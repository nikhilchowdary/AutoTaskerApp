package com.example.iit2014094.autotaskerapp;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by VOJJALA TEJA on 25-03-2017.
 */

public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}
