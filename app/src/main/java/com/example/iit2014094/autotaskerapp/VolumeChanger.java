package com.example.iit2014094.autotaskerapp;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;

import java.util.List;

/**
 * Created by iit2014094 on 3/24/2017.
 */

public class VolumeChanger extends Service {

    private List<ScanResult> scanList;
    private DatabaseHandler db;
    private String bssid,ssid;
    private int notification_sound,ring_sound,media_sound;
    private static int vis=0;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        getCurrentWifiNetwork();
        return Service.START_STICKY;

    }
    private void getCurrentWifiNetwork() {

        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

        registerReceiver(new BroadcastReceiver(){

            @SuppressLint("UseValueOf") @Override
            public void onReceive(Context context, Intent intent) {


                final WifiInfo connectionInfo = wifiManager.getConnectionInfo();

                bssid = connectionInfo.getBSSID();
                ssid = connectionInfo.getSSID();

                db=new DatabaseHandler(context);
                int temp=0;
                if(bssid!=null && db.CheckIsDataAlreadyInDBorNot(bssid)){
                    vis++;
                    AudioManager amanager=(AudioManager)getSystemService(context.AUDIO_SERVICE);

                    //saving volume levels of notifications alarm ring media
                    if(vis==1){

                        notification_sound=amanager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
                        ring_sound=amanager.getStreamVolume(AudioManager.STREAM_RING);
                        media_sound=amanager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    }

                    //volume level adjusted to zero
                    amanager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,0,0);
                    amanager.setStreamVolume(AudioManager.STREAM_RING,0,0);
                    amanager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
                }
                else{

                    if(vis!=0) {
                        AudioManager amanager = (AudioManager) getSystemService(context.AUDIO_SERVICE);

                        //restoring initial configuration of volume levels
                        amanager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, notification_sound, 0);
                        amanager.setStreamVolume(AudioManager.STREAM_RING, ring_sound, 0);
                        amanager.setStreamVolume(AudioManager.STREAM_MUSIC, media_sound, 0);

                        vis=0;
                    }
                }
                
                unregisterReceiver(this);

            }
        },filter);

        wifiManager.startScan();
    }
}