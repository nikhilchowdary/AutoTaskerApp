package com.example.iit2014094.autotaskerapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.iit2014094.autotaskerapp.adapters.WifiRvAdapter;
import com.example.iit2014094.autotaskerapp.models.WifiLocations;

import java.util.ArrayList;

/**
 * Created by VOJJALA TEJA on 24-03-2017.
 */

public class CustomiseWifi extends AppCompatActivity {

    private TextView wifiName;
    private TextView wifiMacAddress;
    private SwitchCompat scSilence;
    private SwitchCompat scAutoSms;
    private LinearLayout llAutoSmsText;

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customise_wifi);


        scSilence = (SwitchCompat) findViewById(R.id.sc_silence);
        scAutoSms = (SwitchCompat) findViewById(R.id.sc_AutoSms);
        llAutoSmsText = (LinearLayout) findViewById(R.id.ll_autosms_text);

        databaseHandler = new DatabaseHandler(this);

        String id = getIntent().getExtras().getString("id");
        final WifiLocations wifiLocation = databaseHandler.getWifi("1");
        String name = getIntent().getExtras().getString("name");
        String mac = getIntent().getExtras().getString("mac");

        TextView wifiname=(TextView)findViewById(R.id.wifiName);
        TextView wifimac=(TextView)findViewById(R.id.wifiMacAddress);

        wifiname.setText(name);
        wifimac.setText(mac);



        scSilence.setChecked(Boolean.parseBoolean(wifiLocation.getIsSilent()));
        scAutoSms.setChecked(Boolean.parseBoolean(wifiLocation.getAutoSms()));

        scSilence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                databaseHandler.deleteWifi(wifiLocation);
                wifiLocation.setIsSilent(String.valueOf(isChecked));
                databaseHandler.addWifi(wifiLocation);
            }
        });

        scAutoSms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                databaseHandler.deleteWifi(wifiLocation);
                wifiLocation.setIsAutoSms(String.valueOf(isChecked));
                databaseHandler.addWifi(wifiLocation);
                if(scAutoSms.isChecked()) {
                    llAutoSmsText.setVisibility(View.VISIBLE);
                }
                else {
                    llAutoSmsText.setVisibility(View.GONE);
                }
            }
        });
    }


}
