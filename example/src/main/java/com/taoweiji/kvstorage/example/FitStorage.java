package com.taoweiji.kvstorage.example;

import com.taoweiji.kvstorage.Group;
import com.taoweiji.kvstorage.GroupData;
import com.taoweiji.kvstorage.KVStorage;
import com.taoweiji.kvstorage.ObjectListMetadata;
import com.taoweiji.kvstorage.ObjectMetadata;
import com.taoweiji.kvstorage.SetMetadata;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("UnnecessaryLocalVariable")
public class FitStorage extends Group {
  FitStorage(GroupData data) {
    super(data);
  }

  public static FitStorage get() {
    return new FitStorage(KVStorage.getRootGroupData("fit_storage"));
  }

  public long getLastLoginTime() {
    long last_login_time = get("last_login_time").getLong(0);
    return last_login_time;
  }

  public void setLastLoginTime(long value) {
    get("last_login_time").set(value);
  }

  public FitStorage.Account getAccount() {
    return new FitStorage.Account(createGroupData("account"));
  }

  public SetMetadata<String> getLoginUserTags() {
    SetMetadata<String> login_user_tags = createSetMetadata("login_user_tags",String.class,false);
    return login_user_tags;
  }

  public FitStorage.Cloud getCloud() {
    return new FitStorage.Cloud(createGroupData("cloud"));
  }

  public int getIntType() {
    int int_type = get("int_type", true).getInt(10);
    return int_type;
  }

  public void setIntType(int value) {
    get("int_type", true).set(value);
  }

  public boolean isBoolType() {
    boolean bool_type = get("bool_type").getBool(true);
    return bool_type;
  }

  public void setBoolType(boolean value) {
    get("bool_type").set(value);
  }

  public ObjectMetadata getLoginAccount() {
    ObjectMetadata login_account = createObjectMetadata("login_account",true);
    return login_account;
  }

  public FitStorage.Fit getFit() {
    return new FitStorage.Fit(createGroupData("fit"));
  }

  public String getString3() {
    String string3 = get("string3").getString("");
    return string3;
  }

  public void setString3(String value) {
    get("string3").set(value);
  }

  public String getString1() {
    String string1 = get("string1").getString("Hello World");
    return string1;
  }

  public void setString1(String value) {
    get("string1").set(value);
  }

  public String getString2() {
    String string2 = get("string2").getString(null);
    return string2;
  }

  public void setString2(String value) {
    get("string2").set(value);
  }

  public float getFloatType() {
    float float_type = get("float_type").getFloat(1.0f);
    return float_type;
  }

  public void setFloatType(float value) {
    get("float_type").set(value);
  }

  public float getDoubleType() {
    float double_type = get("double_type", true).getFloat(2.0f);
    return double_type;
  }

  public void setDoubleType(float value) {
    get("double_type", true).set(value);
  }

  public ObjectListMetadata getLoginMomentDrafts() {
    ObjectListMetadata login_moment_drafts = createObjectListMetadata("login_moment_drafts",false);
    return login_moment_drafts;
  }

  public long getLongType() {
    long long_type = get("long_type").getLong(100);
    return long_type;
  }

  public void setLongType(long value) {
    get("long_type").set(value);
  }

  public FitStorage.BodyInfo getBodyInfo() {
    return new FitStorage.BodyInfo(createGroupData("body_info"));
  }

  @Override
  public void clear() {
    getAccount().clear();
    getCloud().clear();
    getFit().clear();
    getBodyInfo().clear();
    super.clear();
  }

  public static class Account extends Group {
    Account(GroupData data) {
      super(data);
    }

    public String getLoginType() {
      String login_type = get("login_type").getString(null);
      return login_type;
    }

    public void setLoginType(String value) {
      get("login_type").set(value);
    }

    public long getLoginTime() {
      long login_time = get("login_time").getLong(0);
      return login_time;
    }

    public void setLoginTime(long value) {
      get("login_time").set(value);
    }

    public ObjectMetadata getLoginAccount() {
      ObjectMetadata login_account = createObjectMetadata("login_account",true);
      return login_account;
    }
  }

  public static class Cloud extends Group {
    Cloud(GroupData data) {
      super(data);
    }

    public boolean isEnableBrowsingHistory() {
      boolean enable_browsing_history = get("enable_browsing_history").getBool(true);
      return enable_browsing_history;
    }

    public void setEnableBrowsingHistory(boolean value) {
      get("enable_browsing_history").set(value);
    }

    public boolean isActiveVisible() {
      boolean active_visible = get("active_visible").getBool(true);
      return active_visible;
    }

    public void setActiveVisible(boolean value) {
      get("active_visible").set(value);
    }

    public ObjectListMetadata getLoginDevices() {
      ObjectListMetadata login_devices = createObjectListMetadata("login_devices",false);
      return login_devices;
    }

    public boolean isEnableLocalCity() {
      boolean enable_local_city = get("enable_local_city").getBool(true);
      return enable_local_city;
    }

    public void setEnableLocalCity(boolean value) {
      get("enable_local_city").set(value);
    }
  }

  public static class Fit extends Group {
    Fit(GroupData data) {
      super(data);
    }

    public Fit.Body getBody() {
      return new Fit.Body(createGroupData("body"));
    }

    public ObjectListMetadata getCollect() {
      ObjectListMetadata collect = createObjectListMetadata("collect",false);
      return collect;
    }

    @Override
    public void clear() {
      getBody().clear();
      super.clear();
    }

    public static class Body extends Group {
      Body(GroupData data) {
        super(data);
      }

      public int getWeight() {
        int weight = get("weight").getInt(0);
        return weight;
      }

      public void setWeight(int value) {
        get("weight").set(value);
      }

      public int getHeight() {
        int height = get("height").getInt(0);
        return height;
      }

      public void setHeight(int value) {
        get("height").set(value);
      }
    }
  }

  public static class BodyInfo extends Group {
    BodyInfo(GroupData data) {
      super(data);
    }

    public int getGender() {
      int gender = get("gender").getInt(0);
      return gender;
    }

    public void setGender(int value) {
      get("gender").set(value);
    }

    public int getWeight() {
      int weight = get("weight").getInt(0);
      return weight;
    }

    public void setWeight(int value) {
      get("weight").set(value);
    }

    public int getAge() {
      int age = get("age").getInt(18);
      return age;
    }

    public void setAge(int value) {
      get("age").set(value);
    }

    public int getHeight() {
      int height = get("height").getInt(0);
      return height;
    }

    public void setHeight(int value) {
      get("height").set(value);
    }
  }
}
