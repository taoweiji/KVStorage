package com.taoweiji.kvstorage;

public class Group {
    private final GroupData groupData;

    protected Group(GroupData groupData) {
        this.groupData = groupData;
    }

    public Metadata get(String key) {
        return groupData.get(key, false);
    }

    public Metadata get(String key, boolean encrypt) {
        return groupData.get(key, encrypt);
    }

    public <T> ListMetadata<T> getListMetadata(String key, Class<T> type, boolean encrypt) {
        return groupData.getListMetadata(key, type, encrypt);
    }

    public ObjectMetadata getObjectMetadata(String key, boolean encrypt) {
        return groupData.getObjectMetadata(key, encrypt);
    }

    public Metadata getEncrypt(String key) {
        return groupData.get(key, true);
    }

    protected GroupData getGroupData(String key) {
        return KVStorage.getGroupData(groupData.name + "." + key);
    }

    public void clear() {
        groupData.clear();
    }
}