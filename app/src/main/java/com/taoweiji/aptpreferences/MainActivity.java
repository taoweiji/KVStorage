package com.taoweiji.aptpreferences;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.taoweiji.aptpreferences.preferences.SettingsPreferences;
import com.taoweiji.aptpreferences.preferences.Settings;

import java.util.Date;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SettingsPreferences settingsPreference = SettingsPreferences.get();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("last value firstUse: ").append(settingsPreference.getUseLanguage()).append('\n');
        settingsPreference.setUseLanguage(new Date().toString());
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(stringBuilder.toString());
//        SettingsPreferences2.get()

//        settingsPreference.getRun().getVoiceName();
        // 普通类型保存
        settingsPreference.setUseLanguage("zh");
        settingsPreference.setLastOpenAppTimeMillis(System.currentTimeMillis());
        // 对象类型保存
        Settings.LoginUser loginUser = new Settings.LoginUser();
        loginUser.setUsername("username");
        loginUser.setPassword("password");
        settingsPreference.setLoginUser(loginUser);
        // 对象类型带 @AptField(preferences = true) 注解的保存，相当于把 push相关的放在一个分类
//        settingsPreference.getPush().setOpenPush(true);


        // 获取
        String useLanguage = settingsPreference.getUseLanguage();
        Settings.LoginUser loginUser1 = settingsPreference.getLoginUser();
//        boolean openPush = settingsPreference.getPush().isOpenPush();

    }

}
