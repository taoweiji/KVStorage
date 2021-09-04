package com.taoweiji.kvstorage.example;

import com.taoweiji.kvstorage.ReadOnlyConfigGroup;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("UnnecessaryLocalVariable")
public class CloudConfig extends ReadOnlyConfigGroup {
  CloudConfig(String data) {
    super(data);
  }

  public static CloudConfig get() {
    return new CloudConfig("cloud_config");
  }

  public int getHomeDefaultTabIndex() {
    int home_default_tab_index = get("home_default_tab_index").getInt(0);
    return home_default_tab_index;
  }

  public String getLaunchWelcomeMessage() {
    String launch_welcome_message = get("launch_welcome_message").getString("欢迎使用KVStorage");
    return launch_welcome_message;
  }

  public long getSplashScreenDuration() {
    long splash_screen_duration = get("splash_screen_duration").getLong(1000);
    return splash_screen_duration;
  }

  public CloudConfig.Ad getAd() {
    return new CloudConfig.Ad(createGroup("ad"));
  }

  public static class Ad extends ReadOnlyConfigGroup {
    Ad(String data) {
      super(data);
    }

    public boolean isLaunchAppAlertAds() {
      boolean launch_app_alert_ads = get("launch_app_alert_ads").getBool(false);
      return launch_app_alert_ads;
    }

    public String getLaunchAppAds() {
      String launch_app_ads = get("launch_app_ads").getString(null);
      return launch_app_ads;
    }

    public long getAlertAdsShowDuration() {
      long alert_ads_show_duration = get("alert_ads_show_duration").getLong(3000);
      return alert_ads_show_duration;
    }
  }
}
