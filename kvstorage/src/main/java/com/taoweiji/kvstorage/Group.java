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

    protected <T> ListMetadata<T> createListMetadata(String key, Class<T> type, boolean encrypt) {
        return groupData.getListMetadata(key, type, encrypt);
    }

    protected <T> SetMetadata<T> createSetMetadata(String key, Class<T> type, boolean encrypt) {
        return groupData.getSetMetadata(key, type, encrypt);
    }

    protected ObjectListMetadata createObjectListMetadata(String key, boolean encrypt) {
        return groupData.getObjectListMetadata(key, encrypt);
    }

    protected ObjectMetadata createObjectMetadata(String key, boolean encrypt) {
        return groupData.getObjectMetadata(key, encrypt);
    }

    protected GroupData createGroupData(String key) {
        return KVStorage.getGroupData(groupData.name + "." + key);
    }

    public void clear() {
        groupData.clear();
    }
}