package com.thejoyrun.aptpreferences;

/**
 * Created by Wiki on 16/7/15.
 */
@AptPreferences
public class Settings {
    private String firstUse;
    private String push;
    private int lastUseVersion;
    private long time;
    private boolean login;
    private float price2;
    @AptField(serialize = false)
    private float tmp;


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
}
