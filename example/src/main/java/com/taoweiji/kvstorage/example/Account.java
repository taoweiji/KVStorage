package com.taoweiji.kvstorage.example;

public class Account {

    private int id;
    private String name;
    private int gender;
    private boolean momentVisible;

    public Account() {
    }

    public Account(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public boolean isMomentVisible() {
        return momentVisible;
    }

    public void setMomentVisible(boolean momentVisible) {
        this.momentVisible = momentVisible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}