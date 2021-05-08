package com.taoweiji.aptpreferences.example

import android.app.Activity
import android.os.Bundle
import java.util.*

class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsPreference = SettingsPreferences.get()
        val stringBuilder = StringBuilder()
        stringBuilder.append("last value firstUse: ").append(settingsPreference.useLanguage).append('\n')
        settingsPreference.useLanguage = Date().toString()

//        text_view.text = stringBuilder.toString()
        // 普通类型保存
        settingsPreference.useLanguage = "zh"
        settingsPreference.lastOpenAppTimeMillis = System.currentTimeMillis()
        // 对象类型保存
        val loginInfo = LoginInfo()
        loginInfo.username = "username"
        loginInfo.password = "password"
        settingsPreference.loginInfo = loginInfo
        // 对象类型带 @AptField(preferences = true) 注解的保存，相当于把 push相关的放在一个分类
//        settingsPreference.push.isOpenPush = true


        // 获取
        val useLanguage = settingsPreference.useLanguage
        val loginUser1 = settingsPreference.loginInfo
//        val openPush = settingsPreference.push.isOpenPush
        PushPreferences.get().openPush

    }

}
