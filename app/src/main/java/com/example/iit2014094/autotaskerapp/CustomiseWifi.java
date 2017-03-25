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

        wifiName = (TextView)findViewById(R.id.wifiName);
        wifiMacAddress = (TextView) findViewById(R.id.wifiMacAddress);

        scSilence = (SwitchCompat) findViewById(R.id.sc_silence);
        scAutoSms = (SwitchCompat) findViewById(R.id.sc_AutoSms);
        llAutoSmsText = (LinearLayout) findViewById(R.id.ll_autosms_text);

        databaseHandler = new DatabaseHandler(this);

        String id = getIntent().getExtras().getString("id");
        String name = getIntent().getExtras().getString("name");
        String mac = getIntent().getExtras().getString("mac");

        TextView wifiname=(TextView)findViewById(R.id.wifiName);
        TextView wifimac=(TextView)findViewById(R.id.wifiMacAddress);

        wifiname.setText(name);
        wifimac.setText(mac);

        WifiLocations wifiLocation = databaseHandler.getWifi("1");


        scSilence.setChecked(Boolean.parseBoolean(wifiLocation.getIsSilent()));
        scAutoSms.setChecked(Boolean.parseBoolean(wifiLocation.getAutoSms()));


        scAutoSms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(scAutoSms.isChecked()) {
                    llAutoSmsText.setVisibility(View.VISIBLE);
                }
                else {
                    llAutoSmsText.setVisibility(View.GONE);
                }
            }
        });

        TextView msg=(TextView)findViewById(R.id.msg);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View dialogView = View.inflate(getApplicationContext(),R.layout.dialog_add_wifi, null);

                final AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext())
                        .setTitle(R.string.add_wifi_dialog_title)
                        .setView(dialogView)
                        .setPositiveButton(R.string.add, null)
                        .setNegativeButton(R.string.cancel, null)
                        .create();

                alertDialog.show();
                final EditText etAddWifiName = (EditText)dialogView.findViewById(R.id.add_wifi_name);

                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        alertDialog.cancel();
                    }
                });



            }
        });
    }


}
