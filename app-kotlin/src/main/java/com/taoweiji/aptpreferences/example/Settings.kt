package com.taoweiji.aptpreferences.example

import com.taoweiji.aptpreferences.AptField
import com.taoweiji.aptpreferences.AptPreferences

import java.io.Serializable

/**
 * Created by Wiki on 16/7/15.
 */
@AptPreferences
open class Settings {
    @AptField(ignoreGroupId = true)
    open var lastOpenAppTimeMillis = System.currentTimeMillis()

    open var useLanguage = "zh"

    @AptField(ignoreGroupId = false)
    open var loginInfo: LoginInfo? = null

    @AptField(ignore = false)
    open var lastActionTimeMillis: Long = 0
}

@AptPreferences
open class Push {
    open var openPush = false

    open var vibrate = false

    open var voice = false
}

class LoginInfo : Serializable {
    var username: String = ""
    var password: String = ""
}