[![Release](https://jitpack.io/v/taoweiji/AptPreferences.svg)](https://jitpack.io/#taoweiji/AptPreferences)

AptPreferences是基于面向对象设计的快速持久化框架，目的是为了简化SharePreferences的使用，减少代码的编写。可以非常快速地保存基本类型和对象。AptPreferences是基于APT技术实现，在编译期间实现代码的生成，支持混淆。根据不同的用户区分持久化信息。

### 特点
1. 把通过的Javabean变成SharedPreferences操作类
2. 支持保存基本类型及对象
3. 支持根据不同的用户区分持久化信息。

### 简单例子
##### 定义javabean类
```
@AptPreferences
public class Settings {
   private long loginTime;
   private LoginUser loginUser;
    // get、set方法
}
```
##### 使用方式
```
//初始化
AptPreferencesManager.init(this, null);
// 保存信息
SettingsPreference.get().setLoginTime(System.currentTimeMillis());
SettingsPreference.get().set(new LoginUser("Wiki"));
// 获取信息
long loginTime = SettingsPreference.get().getLoginTime();
LoginUser loginUser = SettingsPreference.get().getLoginUser();
```
从上面的简单例子可以看到，我们需要做SharePreferences持久化，仅仅定义一个简单的javabean类（Settings）并添加注解即可，这个框架会根据javabean生成带有持久化功能的SettingsPreference类，通过这个类就可以非常简单去保持或者获取数据，大大简化了SharePreferences的使用，也可以保持对象。
### 项目地址
https://github.com/taoweiji/AptPreferences
### 一、配置项目

##### 配置项目根目录 build.gradle
```
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}
```
##### 配置app build.gradle
```
apply plugin: 'com.android.application'


//...
dependencies {
    implementation 'io.github.taoweiji:aptpreferences:1.2.0'
    annotationProcessor 'io.github.taoweiji:aptpreferences-compiler:1.2.0'
}
```

### 二、定义持久化Javabean

使用方法非常简单，先编写一个普通带getter、setter的javabean类，在类头部添加@AptPreferences即可：

```
@AptPreferences
open class Settings {
    @AptField(ignoreGroupId = true)
    open var lastOpenAppTimeMillis = System.currentTimeMillis()

    @AptField
    open var useLanguage = "zh"

    @AptField(ignoreGroupId = false)
    open var loginUser: LoginUser? = null

    @AptField(ignore = true)
    open var lastActionTimeMillis: Long = 0
}

class LoginUser : Serializable {
    var username: String = ""
    var password: String = ""
}
```



### 三、注解及使用说明

我们提供了两个注解@AptPreferences和@AptField(commit = false,ignore = false,ignoreGroupId = false)。

###### @AptPreferences

被注解的javabean必须为字段实现setter和getter方法；

###### @AptField

AptField有三个参数可以配置。

1. commit：可以配置使用commit还是apply持久化，默认是apply，需要在一些需要立刻保存到文件的可以使用commit方式，比如在退出APP时保存退出的时间。

2. ignore：用来声明是否需要持久化这个字段，如果是false就不保存。

3. ignoreGroupId：如果是true，那么该字段就会忽略GroupId，可以用于实现多用户登录的场景，部分字段需要区分用户发、设置为false；部分字段无需区分用户，是全局参数，那么就是true


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





### 五、根据不同的用户设置
如果app支持多用户登录，需要根据不用的用户持久化，可以通过下面方法配置。再通过@AptField(global = false)，就可以针对某个字段跟随用户不同进行持久化。
```
AptPreferencesManager.setGroupId("uid");
```

### 六、代码调用

```

// 普通类型保存
SettingsPreferences.get().setUseLanguage("zh");
SettingsPreferences.get().setLastOpenAppTimeMillis(System.currentTimeMillis());
// 对象类型保存
Settings.LoginUser loginUser = new Settings.LoginUser();
loginUser.setUsername("username");
loginUser.setPassword("password");
SettingsPreferences.get().setLoginUser(loginUser);

// 获取
String useLanguage = settingsPreference.getUseLanguage();
Settings.LoginUser loginUser1 = settingsPreference.getLoginUser();
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






## License

    Copyright 2016 Tao, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
