package com.thejoyrun.aptpreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    SettingsPreferences settingsPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsPreference = SettingsPreferences.get("name");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("last value firstUse: ").append(settingsPreference.getRun().getVoiceName()).append('\n');
        settingsPreference.getRun().setVoiceName(new Date().toString());
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(stringBuilder.toString());
//        SettingsPreferences2.get()

//        settingsPreference.getRun().getVoiceName();
    }
}
