package com.taoweiji.kvstorage.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.taoweiji.kvstorage.KVStorage
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KVStorage.init(this)

//        Storage.get().fit.body.height = 170
//        Storage.get().cloud.isActiveVisible = false
//
//        val account = Storage.get().account.loginAccount.convert(Account::class.java)
//        Log.e("account", "" + account)
//        Storage.get().account.loginAccount.setObject(Account(1, "Wiki"))
//        Storage.get().account.loginAccount.set("msg", "Hello World")
//        Storage.get().account.loginAccount.set("firstLogin", false)
//        Storage.get().account.loginAccount.flush()

        Log.e("lastLoginTime", "" + FitStorage.get().lastLoginTime)

        FitStorage.get().lastLoginTime = System.currentTimeMillis()
//        CrossFitStorage.get().account().loginAccount.data

        // 上一次打开APP的时间
        // 最后登录登录用户信息
        // 登录
        FitStorage.get().bodyInfo.age = 18
        FitStorage.get().bodyInfo.height = 18
        FitStorage.get().bodyInfo.gender = 1

//        Storage.get().floatType = 2f
//        Storage.get().isBoolType = true
//        Storage.get().intType = 1
//        Storage.get().longType = Long.MAX_VALUE
//        Storage.get().string1 = "Hello World"


        Log.e("test1", Storage.get().getEncrypt("test1").getString(""))
        Log.e("test2", "" + Storage.get().getEncrypt("test2").getInt(0))
        Log.e("test3", "" + Storage.get().getEncrypt("test3").getBool(false))
        Log.e("test4", "" + Storage.get().getEncrypt("test4").getFloat(0f))

        Storage.get().getEncrypt("test1").set("Hello World")
        Storage.get().getEncrypt("test2").set(1)
        Storage.get().getEncrypt("test3").set(true)
        Storage.get().getEncrypt("test4").set(3.14)


//        Log.e("loginUserTags", "" + JSON.toJSONString(Storage.get().loginUserTags.data))
//        Storage.get().loginUserTags.data.add("Hello")
//        Storage.get().loginUserTags.flush()
//        Storage.get().loginUserTags.get
//        Storage.get().publicDrafts.rawData.put()

//        Storage.get().account.loginAccount.setObject(Account(1, "Wiki"))
//        val account = Storage.get().account.loginAccount.convert(Account::class.java)
//        val name = Storage.get().account.loginAccount.getString("name", "")
//        Log.e("KVStorage", "d登录信息：" + JSON.toJSONString(Storage.get().account.loginAccount.data))

        Storage.get().global.openAppNumOfTimes++
        Log.e("KVStorage", "打开APP次数：${Storage.get().global.openAppNumOfTimes}")

        if (Storage.get().isTeenagerMode) {
            Log.e("KVStorage", "是否开启青少年模式：" + Storage.get().isTeenagerMode)
        }
        Storage.get().isTeenagerMode = true

        Storage.get().currentUserTags.add("可爱")
        Storage.get().currentUserTags.add("好看")
        Log.e("KVStorage", "当用户标签：" + JSON.toJSONString(Storage.get().currentUserTags.data))

        val json1 = JSONObject()
        json1.put("id", 12)
        json1.put("name", "Wiki")
        Storage.get().publicDrafts.add(json1)

        val accounts = Storage.get().publicDrafts.convert(Account::class.java)
        Log.e("KVStorage", "accounts：" + Gson().toJson(accounts))

        Storage.get().global.lastOpenAppTime
    }
}