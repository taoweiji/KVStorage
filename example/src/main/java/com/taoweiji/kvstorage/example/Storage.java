package com.taoweiji.kvstorage.example;

import com.taoweiji.kvstorage.Group;
import com.taoweiji.kvstorage.GroupData;
import com.taoweiji.kvstorage.KVStorage;
import com.taoweiji.kvstorage.ListMetadata;
import java.lang.Override;
import java.lang.String;
import org.json.JSONObject;

public class Storage extends Group {
  Storage(GroupData data) {
    super(data);
  }

  public static Storage get() {
    return new Storage(KVStorage.getRootGroupData("storage"));
  }

  public ListMetadata<JSONObject> getPublicDrafts() {
    return getListMetadata("public_drafts",JSONObject.class,false);
  }

  public boolean isTeenagerMode() {
    return get("teenager_mode").getBool(false);
  }

  public void setTeenagerMode(boolean value) {
    get("teenager_mode").set(value);
  }

  public ListMetadata<String> getShippingAddress() {
    return getListMetadata("shipping_address",String.class,false);
  }

  public ListMetadata<JSONObject> getBrowsingHistory() {
    return getListMetadata("browsing_history",JSONObject.class,false);
  }

  public ListMetadata<JSONObject> getLocalCollect() {
    return getListMetadata("local_collect",JSONObject.class,false);
  }

  public ListMetadata<String> getCurrentUserTags() {
    return getListMetadata("current_user_tags",String.class,false);
  }

  public Storage.Global getGlobal() {
    return new Storage.Global(getGroupData("global"));
  }

  @Override
  public void clear() {
    getGlobal().clear();
    super.clear();
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
