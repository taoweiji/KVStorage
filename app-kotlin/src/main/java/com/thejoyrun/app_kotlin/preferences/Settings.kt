package com.thejoyrun.app_kotlin.preferences

import com.thejoyrun.aptpreferences.AptField
import com.thejoyrun.aptpreferences.AptPreferences

import java.io.Serializable

/**
 * Created by Wiki on 16/7/15.
 */
@AptPreferences
open class Settings {
    @AptField(commit = true, global = true)
    open var lastOpenAppTimeMillis: Long = 0
    // 使用commit提交，默认是使用apply提交
    @AptField(commit = true, global = false)
    open var useLanguage: String? = null
    // 使用preferences的方式保存
    @AptField(preferences = true, global = false)
    open var push: Push? = null
    // 使用对象的方式保存
    @AptField(preferences = false, global = false)
    open var loginUser: LoginUser? = null
    // 不持久化该字段，仅仅保留在内存
    @AptField(save = false)
    open var lastActionTimeMillis: Long = 0

    open class Push {
        var isOpenPush: Boolean = false
        var isVibrate: Boolean = false
        var isVoice: Boolean = false
    }

    open class LoginUser : Serializable {
        var username: String = ""
        var password: String = ""
    }
}
