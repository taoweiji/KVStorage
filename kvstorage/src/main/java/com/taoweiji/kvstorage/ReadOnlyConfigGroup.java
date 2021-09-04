package com.taoweiji.kvstorage;

public class ReadOnlyConfigGroup {
    private final String groupName;

    public ReadOnlyConfigGroup(String groupName) {
        this.groupName = groupName;
    }

    protected String createGroup(String name) {
        return groupName + "." + name;
    }

    protected ReadOnlyMetadata get(String name) {
        return new ReadOnlyMetadata(this.groupName, name);
    }
}
