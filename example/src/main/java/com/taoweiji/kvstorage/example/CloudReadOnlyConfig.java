package com.taoweiji.kvstorage.example;

import com.taoweiji.kvstorage.ReadOnlyConfigGroup;
import java.lang.String;

public class CloudReadOnlyConfig extends ReadOnlyConfigGroup {
  CloudReadOnlyConfig(String data) {
    super(data);
  }

  public static CloudReadOnlyConfig get() {
    return new CloudReadOnlyConfig("cloud_config");
  }

  public int getHomeDefaultTabIndex() {
    return get("home_default_tab_index").getInt(0);
  }

  public String getLaunchWelcomeMessage() {
    return get("launch_welcome_message").getString("欢迎使用KVStorage");
  }

  public long getSplashScreenDuration() {
    return get("splash_screen_duration").getLong(1000);
  }

  public CloudReadOnlyConfig.Ad getAd() {
    return new CloudReadOnlyConfig.Ad(createGroup("ad"));
  }

  public class Ad extends ReadOnlyConfigGroup {
    Ad(String data) {
      super(data);
    }

    public boolean isLaunchAppAlertAds() {
      return get("launch_app_alert_ads").getBool(false);
    }

    public String getLaunchAppAds() {
      return get("launch_app_ads").getString(null);
    }

    public long getAlertAdsShowDuration() {
      return get("alert_ads_show_duration").getLong(3000);
    }
  }
}
