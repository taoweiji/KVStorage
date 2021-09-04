package com.taoweiji.kvstorage.example;

import com.taoweiji.kvstorage.Group;
import com.taoweiji.kvstorage.GroupData;
import com.taoweiji.kvstorage.KVStorage;
import com.taoweiji.kvstorage.ListMetadata;
import com.taoweiji.kvstorage.ObjectListMetadata;
import com.taoweiji.kvstorage.ObjectMetadata;
import com.taoweiji.kvstorage.SetMetadata;
import java.lang.Override;
import java.lang.String;

public class Storage extends Group {
  Storage(GroupData data) {
    super(data);
  }

  public static Storage get() {
    return new Storage(KVStorage.getRootGroupData("storage"));
  }

  public Storage.Account getAccount() {
    return new Storage.Account(createGroupData("account"));
  }

  public boolean isTeenagerMode() {
    return get("teenager_mode").getBool(false);
  }

  public void setTeenagerMode(boolean value) {
    get("teenager_mode").set(value);
  }

  public ObjectListMetadata getShippingAddress() {
    return createObjectListMetadata("shipping_address",false);
  }

  public ObjectListMetadata getBrowsingHistory() {
    return createObjectListMetadata("browsing_history",false);
  }

  public ListMetadata<String> getCurrentUserTags() {
    return createListMetadata("current_user_tags",String.class,false);
  }

  public Storage.Global getGlobal() {
    return new Storage.Global(createGroupData("global"));
  }

  public SetMetadata<String> getTags() {
    return createSetMetadata("tags",String.class,false);
  }

  @Override
  public void clear() {
    getAccount().clear();
    getGlobal().clear();
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

    public ObjectMetadata getLoginAccount() {
      return createObjectMetadata("login_account",true);
    }
  }

  public class Global extends Group {
    Global(GroupData data) {
      super(data);
    }

    public int getOpenAppNumOfTimes() {
      return get("open_app_num_of_times").getInt(0);
    }

    public void setOpenAppNumOfTimes(int value) {
      get("open_app_num_of_times").set(value);
    }

    public long getLastOpenAppTime() {
      return get("last_open_app_time").getLong(0);
    }

    public void setLastOpenAppTime(long value) {
      get("last_open_app_time").set(value);
    }
  }
}
