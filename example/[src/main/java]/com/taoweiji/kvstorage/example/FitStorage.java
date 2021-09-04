package com.taoweiji.kvstorage.example;

import com.taoweiji.kvstorage.Group;
import com.taoweiji.kvstorage.GroupData;
import com.taoweiji.kvstorage.KVStorage;
import com.taoweiji.kvstorage.ObjectListMetadata;
import com.taoweiji.kvstorage.ObjectMetadata;
import com.taoweiji.kvstorage.SetMetadata;
import java.lang.Override;
import java.lang.String;

public class FitStorage extends Group {
  FitStorage(GroupData data) {
    super(data);
  }

  public static FitStorage get() {
    return new FitStorage(KVStorage.getRootGroupData("fit_storage"));
  }

  public long getLastLoginTime() {
    return get("last_login_time").getLong(0);
  }

  public void setLastLoginTime(long value) {
    get("last_login_time").set(value);
  }

  public FitStorage.Account getAccount() {
    return new FitStorage.Account(createGroupData("account"));
  }

  public SetMetadata<String> getLoginUserTags() {
    return createSetMetadata("login_user_tags",String.class,false);
  }

  public FitStorage.Cloud getCloud() {
    return new FitStorage.Cloud(createGroupData("cloud"));
  }

  public int getIntType() {
    return get("int_type", true).getInt(10);
  }

  public void setIntType(int value) {
    get("int_type", true).set(value);
  }

  public boolean isBoolType() {
    return get("bool_type").getBool(true);
  }

  public void setBoolType(boolean value) {
    get("bool_type").set(value);
  }

  public ObjectMetadata getLoginAccount() {
    return createObjectMetadata("login_account",true);
  }

  public FitStorage.Fit getFit() {
    return new FitStorage.Fit(createGroupData("fit"));
  }

  public String getString3() {
    return get("string3").getString("");
  }

  public void setString3(String value) {
    get("string3").set(value);
  }

  public String getString1() {
    return get("string1").getString("Hello World");
  }

  public void setString1(String value) {
    get("string1").set(value);
  }

  public String getString2() {
    return get("string2").getString(null);
  }

  public void setString2(String value) {
    get("string2").set(value);
  }

  public float getFloatType() {
    return get("float_type").getFloat(1.0f);
  }

  public void setFloatType(float value) {
    get("float_type").set(value);
  }

  public float getDoubleType() {
    return get("double_type", true).getFloat(2.0f);
  }

  public void setDoubleType(float value) {
    get("double_type", true).set(value);
  }

  public ObjectListMetadata getLoginMomentDrafts() {
    return createObjectListMetadata("login_moment_drafts",false);
  }

  public long getLongType() {
    return get("long_type").getLong(100);
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

  public class Account extends Group {
    Account(GroupData data) {
      super(data);
    }

    public String getLoginType() {
      return get("login_type").getString(null);
    }

    public void setLoginType(String value) {
      get("login_type").set(value);
    }

    public long getLoginTime() {
      return get("login_time").getLong(0);
    }

    public void setLoginTime(long value) {
      get("login_time").set(value);
    }

    public ObjectMetadata getLoginAccount() {
      return createObjectMetadata("login_account",true);
    }
  }

  public class Cloud extends Group {
    Cloud(GroupData data) {
      super(data);
    }

    public boolean isEnableBrowsingHistory() {
      return get("enable_browsing_history").getBool(true);
    }

    public void setEnableBrowsingHistory(boolean value) {
      get("enable_browsing_history").set(value);
    }

    public boolean isActiveVisible() {
      return get("active_visible").getBool(true);
    }

    public void setActiveVisible(boolean value) {
      get("active_visible").set(value);
    }

    public ObjectListMetadata getLoginDevices() {
      return createObjectListMetadata("login_devices",false);
    }

    public boolean isEnableLocalCity() {
      return get("enable_local_city").getBool(true);
    }

    public void setEnableLocalCity(boolean value) {
      get("enable_local_city").set(value);
    }
  }

  public class Fit extends Group {
    Fit(GroupData data) {
      super(data);
    }

    public Fit.Body getBody() {
      return new Fit.Body(createGroupData("body"));
    }

    public ObjectListMetadata getCollect() {
      return createObjectListMetadata("collect",false);
    }

    @Override
    public void clear() {
      getBody().clear();
      super.clear();
    }

    public class Body extends Group {
      Body(GroupData data) {
        super(data);
      }

      public int getWeight() {
        return get("weight").getInt(0);
      }

      public void setWeight(int value) {
        get("weight").set(value);
      }

      public int getHeight() {
        return get("height").getInt(0);
      }

      public void setHeight(int value) {
        get("height").set(value);
      }
    }
  }

  public class BodyInfo extends Group {
    BodyInfo(GroupData data) {
      super(data);
    }

    public int getGender() {
      return get("gender").getInt(0);
    }

    public void setGender(int value) {
      get("gender").set(value);
    }

    public int getWeight() {
      return get("weight").getInt(0);
    }

    public void setWeight(int value) {
      get("weight").set(value);
    }

    public int getAge() {
      return get("age").getInt(18);
    }

    public void setAge(int value) {
      get("age").set(value);
    }

    public int getHeight() {
      return get("height").getInt(0);
    }

    public void setHeight(int value) {
      get("height").set(value);
    }
  }
}
