package com.example.iit2014094.autotaskerapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

/**
 * Created by iit2014094 on 3/24/2017.
 */

public class AlarmManagerBroadcastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");

        wl.acquire();

        Thread aa= new Thread(new Runnable(){

            @Override
            public void run() {
                context.startService(new Intent(context, VolumeChanger.class));
                context.startService(new Intent(context,AutoSmsSender.class));
            }
        });
        aa.start();

        wl.release();
    }
}
