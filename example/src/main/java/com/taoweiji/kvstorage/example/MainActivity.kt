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
    val TAG = "KVStorage"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 对象操作
        Storage.get().account.loginAccount.setObject(Account(1, "Wiki"))
        val account = Storage.get().account.loginAccount.convert(Account::class.java)
        Storage.get().account.loginAccount.set("gender", 1)
        val name = Storage.get().account.loginAccount.getString("name", "")
        Log.e(TAG, "登录用户信息：" + Storage.get().account.loginAccount.data)

        Log.e(TAG, "最后打开APP时间：" + Date(Storage.get().global.lastOpenAppTime))
        Storage.get().global.lastOpenAppTime = System.currentTimeMillis()

        Storage.get().global.openAppNumOfTimes++
        Log.e(TAG, "打开APP次数：${Storage.get().global.openAppNumOfTimes}")

        if (Storage.get().isTeenagerMode) {
            Log.e(TAG, "是否开启青少年模式：" + Storage.get().isTeenagerMode)
        }
        Storage.get().isTeenagerMode = true

        // 列表操作
        Storage.get().currentUserTags.add("可爱")
        Storage.get().currentUserTags.add("好看")
        Log.e(TAG, "当用户标签：" + JSON.toJSONString(Storage.get().currentUserTags.data))



        Storage.get().tags.add("tag1")
        Storage.get().tags.add("tag2")
        Log.e(TAG, "Set标签：" + JSON.toJSONString(Storage.get().tags.data))

        testObjectList()
    }

    private fun testObjectList() {
//        Storage.get().browsingHistory.addObject(
//            BrowsingHistory(
//                "link2",
//                "http://github.com/taoweiji"
//            )
//        )
        val tmp1 = Storage.get().browsingHistory.findByProperty("name", "link2")
        val all = Storage.get().browsingHistory.convert(BrowsingHistory::class.java)
        Log.e(TAG, "条件查找：$tmp1")
        Log.e(TAG, "转换对象：" + JSON.toJSONString(all))
        uploadException {

        }
    }
}

fun uploadException(runnable: Runnable) {
    try {
        runnable.run()
    } catch (error: Throwable) {
        error.printStackTrace()
        // TODO 上传异常
    }
}