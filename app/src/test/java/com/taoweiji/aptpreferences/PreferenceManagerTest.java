package com.taoweiji.aptpreferences;

import org.junit.Test;

class User{
    int age;
    String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
public class PreferenceManagerTest {
    @Test
    public void test(){
        User user = PreferenceManager.get(User.class);
        System.out.println(user.getAge());
    }
}