package com.taoweiji.aptpreferences.preferences;

import com.taoweiji.aptpreferences.AptField;
import com.taoweiji.aptpreferences.AptPreferences;

import java.io.Serializable;

/**
 * Created by Wiki on 16/7/15.
 */
@AptPreferences
public class Settings {
    @AptField(commit = true, ignoreGroupId = true)
    private long lastOpenAppTimeMillis;
    // 使用commit提交，默认是使用apply提交
    @AptField(commit = true, ignoreGroupId = false)
    private String useLanguage;
    // 使用对象的方式保存
    @AptField(preferences = true, ignoreGroupId = false)
    private LoginUser loginUser;
    // 不持久化该字段，仅仅保留在内存
    @AptField(save = false)
    private long lastActionTimeMillis;
    public long getLastActionTimeMillis() {
        return lastActionTimeMillis;
    }
    public void setLastActionTimeMillis(long lastActionTimeMillis) {
        this.lastActionTimeMillis = lastActionTimeMillis;
    }
    public LoginUser getLoginUser() {
        return loginUser;
    }
    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }
    public long getLastOpenAppTimeMillis() {
        return lastOpenAppTimeMillis;
    }
    public void setLastOpenAppTimeMillis(long lastOpenAppTimeMillis) {
        this.lastOpenAppTimeMillis = lastOpenAppTimeMillis;
    }
    public String getUseLanguage() {
        return useLanguage;
    }
    public void setUseLanguage(String useLanguage) {
        this.useLanguage = useLanguage;
    }
    public static class Push {
        private boolean openPush;
        private boolean vibrate;
        private boolean voice;
        public boolean isOpenPush() {
            return openPush;
        }
        public void setOpenPush(boolean openPush) {
            this.openPush = openPush;
        }
        public boolean isVibrate() {
            return vibrate;
        }
        public void setVibrate(boolean vibrate) {
            this.vibrate = vibrate;
        }
        public boolean isVoice() {
            return voice;
        }
        public void setVoice(boolean voice) {
            this.voice = voice;
        }
    }
    public static class LoginUser implements Serializable{
        public String username;
        public String password;
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }
}
