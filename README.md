# Android快速持久化框架 AptPreferences

[![Release](https://jitpack.io/v/joyrun/AptPreferences.svg)](https://jitpack.io/#joyrun/AptPreferences)

AptPreferences是基于面向对象设计的快速持久化框架，目的是为了简化SharePreferences的使用，减少代码的编写。可以非常快速地保存基本类型和对象。AptPreferences是基于APT技术实现，在编译期间实现代码的生成，支持混淆。根据不同的用户区分持久化信息。

### 特点
1. 把通过的Javabean变成SharedPreferences操作类
2. 支持保存基本类型及对象
3. 支持根据不同的用户区分持久化信息。

### 简单例子
```
@AptPreferences
public class Settings {
   private long loginTime;
   private LoginUser loginUser;
    // get、set方法
}
```

```
// 保存信息
SettingsPreference.get().setLoginTime(System.currentTimeMillis());
SettingsPreference.get().set(new LoginUser("Wiki"));
// 获取信息
long loginTime = SettingsPreference.get().getLoginTime();
LoginUser loginUser = SettingsPreference.get().getLoginUser();
```
### 项目地址
https://github.com/joyrun/AptPreferences
### 一、配置项目

##### 配置项目根目录 build.gradle
```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:1.5.0"
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    }
}

allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
##### 配置app build.gradle
```
apply plugin: 'com.android.application'
apply plugin: 'com.neenbedankt.android-apt'

//...
dependencies {
    compile 'com.github.joyrun.AptPreferences:aptpreferences:0.2.3'
    apt 'com.github.joyrun.AptPreferences:aptpreferences-compiler:0.2.3'
}
```

### 二、定义持久化Javabean

使用方法非常简单，先编写一个普通带getter、setter的javabean类，在类头部添加@AptPreferences即可：

```

@AptPreferences
public class Settings {
   private long lastOpenAppTimeMillis;
   // 使用commit提交，默认是使用apply提交，配置默认值
   @AptField(commit = true)
   private String useLanguage = "zh";
   // 使用preferences的方式保存
   @AptField(preferences = true)
   private Push push;
   // 使用对象的方式保存
   private LoginUser loginUser;
   // 不持久化该字段，仅仅保留在内存
   @AptField(save = false)
   private long lastActionTimeMillis;

    // ...

    // get、set方法必须写
}

```



### 三、注解及使用说明

我们提供了两个注解@AptPreferences和@AptField(commit = false,save = true,preferences = false)。

###### @AptPreferences

被注解的javabean必须为字段实现setter和getter方法；

###### @AptField

AptField有三个参数可以配置。

1. commit：可以配置使用commit还是apply持久化，默认是apply，需要在一些需要立刻保存到文件的可以使用commit方式，比如在退出APP时保存退出的时间。

2. save：用来声明是否需要持久化这个字段。

3. preferences：这个属性仅仅适用于对象类型的字段，用来声明这个是以对象的方式保存，还是以preferences的方式保存。如果是true，就可以通过settingsPreference.getPush().isOpenPush()的方式存取。



### 四、初始化

使用之前要进行初始化，建议在Application进行初始化，需要需要保存对象，还需要实现对象的解析器，这里使用Fastjson作为实例：

```

public class MyApplication extends Application{
   @Override
   public void onCreate() {
       super.onCreate();
       AptPreferencesManager.init(this, new AptParser() {
           @Override
           public Object deserialize(Class clazz, String text) {
               return JSON.parseObject(text,clazz);
           }
           @Override
           public String serialize(Object object) {
               return JSON.toJSONString(object);
           }
       });
   }
}

```





### 五、获取持久化对象

```

// 提供一个默认的获取方法

SettingsPreferences settingsPreference = SettingsPreferences.get("name");

// 可以根据不用的用户名称获取

SettingsPreferences settingsPreference = SettingsPreferences.get("name");

```

### 六、代码调用

```

// 普通类型保存
settingsPreference.setUseLanguage("zh");
settingsPreference.setLastOpenAppTimeMillis(System.currentTimeMillis());
// 对象类型保存
Settings.LoginUser loginUser = new Settings.LoginUser();
loginUser.setUsername("username");
loginUser.setPassword("password");
settingsPreference.setLoginUser(loginUser);
// 对象类型带 @AptField(preferences = true) 注解的保存，相当于把 push相关的放在一个分类
settingsPreference.getPush().setOpenPush(true);


// 获取
String useLanguage = settingsPreference.getUseLanguage();
Settings.LoginUser loginUser1 = settingsPreference.getLoginUser();
boolean openPush = settingsPreference.getPush().isOpenPush();
```

### 七、默认值

很多时候我们需要在没有获取到值时使用默认值，SharedPreferences本身也是具备默认值的，所以我们也是支持默认值配置。分析生成的代码可以看到：

```

@Override
public long getLastOpenAppTimeMillis() {
   return mPreferences.getLong("lastOpenAppTimeMillis", super.getLastOpenAppTimeMillis());
}

```

如果没有获取到值，会调用父类的方法，那么就可以通过这个方式配置默认值：

```

@AptPreferences
public class Settings {
   // 使用commit提交，默认是使用apply提交，配置默认值
   @AptField(commit = true)
   private String useLanguage = "zh";

   // ...

}

```



### 八、详细转换代码

```

@AptPreferences
public class Settings {
   private long lastOpenAppTimeMillis;
   // 使用commit提交，默认是使用apply提交，配置默认值
   @AptField(commit = true)
   private String useLanguage = "zh";
   // 使用preferences的方式保存
   @AptField(preferences = true)
   private Push push;
   // 使用对象的方式保存
   private LoginUser loginUser;
   // 不持久化该字段，仅仅保留在内存
   @AptField(save = false)
   private long lastActionTimeMillis;
   public long getLastActionTimeMillis() {
       return lastActionTimeMillis;
   }
   public void setLastActionTimeMillis(long lastActionTimeMillis) {
       this.lastActionTimeMillis = lastActionTimeMillis;
   }
   public LoginUser getLoginUser() {
       return loginUser;
   }
   public void setLoginUser(LoginUser loginUser) {
       this.loginUser = loginUser;
   }
   public long getLastOpenAppTimeMillis() {
       return lastOpenAppTimeMillis;
   }
   public void setLastOpenAppTimeMillis(long lastOpenAppTimeMillis) {
       this.lastOpenAppTimeMillis = lastOpenAppTimeMillis;
   }
   public String getUseLanguage() {
       return useLanguage;
   }
   public void setUseLanguage(String useLanguage) {
       this.useLanguage = useLanguage;
   }
   public Push getPush() {
       return push;
   }
   public void setPush(Push push) {
       this.push = push;
   }
   public static class Push {
       private boolean openPush;
       private boolean vibrate;
       private boolean voice;
       public boolean isOpenPush() {
           return openPush;
       }
       public void setOpenPush(boolean openPush) {
           this.openPush = openPush;
       }
       public boolean isVibrate() {
           return vibrate;
       }
       public void setVibrate(boolean vibrate) {
           this.vibrate = vibrate;
       }
       public boolean isVoice() {
           return voice;
       }
       public void setVoice(boolean voice) {
           this.voice = voice;
       }
   }
   public static class LoginUser implements Serializable{
       public String username;
       public String password;
       public String getUsername() {
           return username;
       }
       public void setUsername(String username) {
           this.username = username;
       }
       public String getPassword() {
           return password;
       }
       public void setPassword(String password) {
           this.password = password;
       }
   }
}

```

实际上就是根据上面的代码自动生成带有持久化的代码，可以在这里可以找到

> app/build/generated/source/apt/debug

```

public final class SettingsPreferences extends Settings {
   public static final Map<String, SettingsPreferences> sMap = new java.util.HashMap<>();
   private final SharedPreferences.Editor mEdit;
   private final SharedPreferences mPreferences;
   private final String mName;
   public SettingsPreferences(String name) {
       mPreferences = AptPreferencesManager.getContext().getSharedPreferences("Settings_" + name, 0);
       mEdit = mPreferences.edit();
       this.mName = name;
       this.setPush(new PushPreferences());
   }
   @Override
   public Settings.LoginUser getLoginUser() {
       String text = mPreferences.getString("loginUser", null);
       Object object = null;
       if (text != null) {
           object = AptPreferencesManager.getAptParser().deserialize(com.thejoyrun.aptpreferences.Settings.LoginUser.class, text);
       }
       if (object != null) {
           return (com.thejoyrun.aptpreferences.Settings.LoginUser) object;
       }
       return super.getLoginUser();
   }
   @Override
   public void setLoginUser(Settings.LoginUser loginUser) {
       mEdit.putString("loginUser", AptPreferencesManager.getAptParser().serialize(loginUser)).apply();
   }
   @Override
   public long getLastOpenAppTimeMillis() {
       return mPreferences.getLong("lastOpenAppTimeMillis", super.getLastOpenAppTimeMillis());
   }
   @Override
   public void setLastOpenAppTimeMillis(long lastOpenAppTimeMillis) {
       mEdit.putLong("lastOpenAppTimeMillis", lastOpenAppTimeMillis).apply();
   }
   @Override
   public String getUseLanguage() {
       return mPreferences.getString("useLanguage", super.getUseLanguage());
   }
   @Override
   public void setUseLanguage(String useLanguage) {
       mEdit.putString("useLanguage", useLanguage).commit();
   }
   public static SettingsPreferences get(String name) {
       if (sMap.containsKey(name)) {
           return sMap.get(name);
       }
       synchronized (sMap) {
           if (!sMap.containsKey(name)) {
               SettingsPreferences preferences = new SettingsPreferences(name);
               sMap.put(name, preferences);
           }
       }
       return sMap.get(name);
   }
   public static SettingsPreferences get() {
       return get("");
   }
   public void clear() {
       mEdit.clear().commit();
       sMap.remove(mName);
   }
   public static void clearAll() {
       java.util.Set<String> keys = sMap.keySet();
       for (String key : keys) {
           sMap.get(key).clear();
       }
   }
   private class PushPreferences extends Settings.Push {
       @Override
       public boolean isOpenPush() {
           return mPreferences.getBoolean("Push.openPush", super.isOpenPush());
       }
       @Override
       public void setOpenPush(boolean openPush) {
           mEdit.putBoolean("Push.openPush", openPush).apply();
       }
       @Override
       public boolean isVibrate() {
           return mPreferences.getBoolean("Push.vibrate", super.isVibrate());
       }
       @Override
       public void setVibrate(boolean vibrate) {
           mEdit.putBoolean("Push.vibrate", vibrate).apply();
       }
       @Override
       public boolean isVoice() {
           return mPreferences.getBoolean("Push.voice", super.isVoice());
       }
       @Override
       public void setVoice(boolean voice) {
           mEdit.putBoolean("Push.voice", voice).apply();
       }
   }
}
```




## License

    Copyright 2016 Joyrun, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
