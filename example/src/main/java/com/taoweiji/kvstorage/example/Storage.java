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
import java.lang.SuppressWarnings;

@SuppressWarnings("UnnecessaryLocalVariable")
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
    boolean teenager_mode = get("teenager_mode").getBool(false);
    return teenager_mode;
  }

  public void setTeenagerMode(boolean value) {
    get("teenager_mode").set(value);
  }

  public ObjectListMetadata getShippingAddress() {
    ObjectListMetadata shipping_address = createObjectListMetadata("shipping_address",false);
    return shipping_address;
  }

  public ObjectListMetadata getBrowsingHistory() {
    ObjectListMetadata browsing_history = createObjectListMetadata("browsing_history",false);
    return browsing_history;
  }

  public ListMetadata<String> getCurrentUserTags() {
    ListMetadata<String> current_user_tags = createListMetadata("current_user_tags",String.class,false);
    return current_user_tags;
  }

  public Storage.Global getGlobal() {
    return new Storage.Global(createGroupData("global"));
  }

  public SetMetadata<String> getTags() {
    SetMetadata<String> tags = createSetMetadata("tags",String.class,false);
    return tags;
  }

  @Override
  public void clear() {
    getAccount().clear();
    getGlobal().clear();
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

    public ObjectMetadata getLoginAccount() {
      ObjectMetadata login_account = createObjectMetadata("login_account",true);
      return login_account;
    }
  }

  public static class Global extends Group {
    Global(GroupData data) {
      super(data);
    }

    public int getOpenAppNumOfTimes() {
      int open_app_num_of_times = get("open_app_num_of_times").getInt(0);
      return open_app_num_of_times;
    }

    public void setOpenAppNumOfTimes(int value) {
      get("open_app_num_of_times").set(value);
    }

    public long getLastOpenAppTime() {
      long last_open_app_time = get("last_open_app_time").getLong(0);
      return last_open_app_time;
    }

    public void setLastOpenAppTime(long value) {
      get("last_open_app_time").set(value);
    }
  }
}
