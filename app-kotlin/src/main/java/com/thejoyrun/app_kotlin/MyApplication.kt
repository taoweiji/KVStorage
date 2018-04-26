package com.thejoyrun.app_kotlin

import android.app.Application

import com.alibaba.fastjson.JSON
import com.thejoyrun.aptpreferences.AptParser
import com.thejoyrun.aptpreferences.AptPreferencesManager

/**
 * Created by Wiki on 16/7/15.
 */

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AptPreferencesManager.init(this, object : AptParser {

            override fun deserialize(clazz: Class<*>, text: String): Any? {
                return JSON.parseObject(text, clazz)
            }

            override fun serialize(`object`: Any): String {
                return JSON.toJSONString(`object`)
            }
        })
        AptPreferencesManager.setUserInfo("123456")
    }
}
