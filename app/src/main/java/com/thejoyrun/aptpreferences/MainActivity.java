package com.thejoyrun.aptpreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    Settings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = SettingsPreference.get("name");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("last value firstUse: ").append(settings.getFirstUse()).append('\n');
        settings.setFirstUse(new Date().toString());
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(stringBuilder.toString());
//        SettingsPreference2.get()
    }
}
