package com.taoweiji.aptpreferences;

/**
 * Created by Wiki on 16/7/15.
 */
@AptPreferences
public class SettingsInfo {
    private String firstUse;
    private String push;
    private int lastUseVersion;
    private long time;
    private boolean login;
    private float price2;
    private double price;
    @AptField(ignore = false)
    private float tmp;
    private static float tmpStatic;

//    @AptField(preferences = true, ignoreGroupId = false)
//    private Run run;
    @AptField(ignoreGroupId = false)
    private LoginUser loginUser;

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    public float getTmp() {
        return tmp;
    }

    public void setTmp(float tmp) {
        this.tmp = tmp;
    }

//    public Run getRun() {
//        return run;
//    }
//
//    public void setRun(Run run) {
//        this.run = run;
//    }

    public String getFirstUse() {
        return firstUse;
    }

    public void setFirstUse(String firstUse) {
        this.firstUse = firstUse;
    }

    public String getPush() {
        return push;
    }

    public void setPush(String push) {
        this.push = push;
    }

    public int getLastUseVersion() {
        return lastUseVersion;
    }

    public void setLastUseVersion(int lastUseVersion) {
        this.lastUseVersion = lastUseVersion;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }


    public float getPrice2() {
        return price2;
    }

    public void setPrice2(float price2) {
        this.price2 = price2;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static float getTmpStatic() {
        return tmpStatic;
    }

    public static void setTmpStatic(float tmpStatic) {
        SettingsInfo.tmpStatic = tmpStatic;
    }

    public static class Run {
        private boolean autoPause;
        private boolean openVoice;
        private String voiceName;

        public boolean isAutoPause() {
            return autoPause;
        }

        public void setAutoPause(boolean autoPause) {
            this.autoPause = autoPause;
        }

        public boolean isOpenVoice() {
            return openVoice;
        }

        public void setOpenVoice(boolean openVoice) {
            this.openVoice = openVoice;
        }

        public String getVoiceName() {
            return voiceName;
        }

        public void setVoiceName(String voiceName) {
            this.voiceName = voiceName;
        }
    }

    public static class LoginUser {
        private String username;
        private String email;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


}
