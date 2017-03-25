package com.example.iit2014094.autotaskerapp;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.lang.reflect.Method;

/**
 * Created by VOJJALA TEJA on 24-03-2017.
 */

public class AutoSmsSender extends Service {

    public BroadcastReceiver CallBlocker;
    public TelephonyManager telephonyManager;
    public Object telephonyService;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

        registerReceiver(new BroadcastReceiver(){

            @SuppressLint("UseValueOf") @Override
            public void onReceive(Context context, Intent intent) {


                final WifiInfo connectionInfo = wifiManager.getConnectionInfo();

                String bssid = connectionInfo.getBSSID();
                String ssid = connectionInfo.getSSID();

                DatabaseHandler db=new DatabaseHandler(context);
                if(bssid!=null && db.CheckIsDataAlreadyInDBorNot(bssid)){
                    sendSmsIfRinging();
                }
                unregisterReceiver(this);

            }
        },filter);
        wifiManager.startScan();
        return Service.START_STICKY;

    }

    private void sendSmsIfRinging(){

        CallBlocker =new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent) {
                telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                try {
                    Class<?> c = Class.forName(telephonyManager.getClass().getName());
                    Method m = c.getDeclaredMethod("getITelephony");
                    m.setAccessible(true);
                    telephonyService = m.invoke(telephonyManager);
                    telephonyManager.listen(callBlockListener, PhoneStateListener.LISTEN_CALL_STATE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            PhoneStateListener callBlockListener = new PhoneStateListener()
            {
                public void onCallStateChanged(int state, String incomingNumber)
                {
                    if(state==TelephonyManager.CALL_STATE_RINGING)
                    {
                        try {
                            Toast.makeText(getApplicationContext(),"Hii",Toast.LENGTH_LONG).show();
                            Class<?> classTelephony = Class.forName(telephonyManager.getClass().getName());
                            Method method = classTelephony.getDeclaredMethod("getITelephony");
                            method.setAccessible(true);
                            Object telephonyInterface = method.invoke(telephonyManager);
                            Class<?> telephonyInterfaceClass =Class.forName(telephonyInterface.getClass().getName());
                            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");
                            methodEndCall.invoke(telephonyInterface);
                            String strSMSBody = "Busy. Will call you back!";
/*                            SmsManager sms = SmsManager.getDefault();
                            List<String> messages = sms.divideMessage(strSMSBody);
                            for (String message : messages) {
                                sms.sendTextMessage(incomingNumber, null, message, PendingIntent.getBroadcast(
                                        getApplicationContext(), 0, null, 0), null);
                            }*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        };
        IntentFilter filter= new IntentFilter("android.intent.action.PHONE_STATE");
        registerReceiver(CallBlocker, filter);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (CallBlocker != null)
        {
            unregisterReceiver(CallBlocker);
            CallBlocker = null;
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (CallBlocker != null)
        {
            unregisterReceiver(CallBlocker);
            CallBlocker = null;
        }
    }
}
