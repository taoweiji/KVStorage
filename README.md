# 强大的 Android 结构化KV存储框架，基于 yaml 生成 java 结构化存储类

 [![Maven Central](https://img.shields.io/maven-central/v/io.github.taoweiji.kvstorage/kvstorage)](https://search.maven.org/search?q=io.github.taoweiji.kvstorage)


使用 yaml 配置文件声明键值对的字段名、类型、默认值、是否加密、分组等，通过 Gradle 插件自动生成对象类，让键值对存储的使用变成get/set形式，让代码调用更加安全、优雅。

- 支持对字段单独加密，避免敏感信息明文存储；
- 通过 yaml 配置存储字段信息，配置简单易懂，一目了然；
- 支持基本类型、对象和列表数据，代替SharePreference及部分SQLite场景；
- 支持自定义文件名，实现多用户之间的数据隔离；
- 支持自定义存储数据源，开发者可以自行实现把部分数据同步到云端；
- 支持自定义存储数据源，提供 [MMKV拓展](#使用-mmkv-作为存储底层) 支持。
- 提供只读参数类生成插件，开发者可以自行实现在线KV参数服务。


#### 相对传统KV，结构化KV带来的改进
- 解决传统KV需要定义大量常量字符串KEY_NAME带来的凌乱问题；
- 解决传统KV多个地方使用可能会导致默认值、类型不一致导致的异常问题；
- 解决传统KV部分数据需要对不同用户隔离的实现过于繁琐问题；
- 解决传统KV部分数据需要加密存储的实现过于繁琐问题；
- 解决传统KV存储list/set类型数据时，增删改列表数据需要编写复杂代码问题；

#### 推荐规范

- 推荐只使用一个yaml配置文件，放在公共 module 内，方便组件化开发调用；
- 推荐不同的业务划分不同的分组，避免所有的配置都写在一个组内；
- 编写yaml时，所有的字段都应该编写注释，说明其用法；
- bool类型数据不用增加is_前缀，bool类型会默认生成 setXX / isXX 方法；

### 编写 yaml 配置文件

在公共 module（或 app）目录下创建 yaml 配置文件，比如 storage.yaml 会生成 Storage.java，不同文件名存储数据是隔离的，一旦定好后不建议修改。一个项目无论有多少个 module，都推荐使用一个 yaml 配置文件，放在公共的module里面。

```yaml
#storage.yaml
account:
  login_account(object,encrypt): #用户登录信息
  login_type(string): null #登录方式：qq、weixin、weibo、phone
browsing_history(list<object>): #浏览记录
teenager_mode(bool): false #是否开启青少年模式
current_user_tags(list<string>): #当前用户标签
global:
  open_app_num_of_times(int): 0 #打开APP的次数
  last_open_app_time(long): 0 #上次打开app时间，13位
tags(set<string>): #标签
```

- 配置文件仅允许使用小写字母、数字、下划线，字段必须使用字母开头。
- 带有括号 `()`是字段，否则就是分组。如上，account 是分组，而login_account、login_time是字段。
- 字段必须在括号`()`内填写类型，仅支持`int`、`long`、`float`、`bool`、`string`、`object`、`list<int>`、`list<long>`、`list<float>`、`list<bool>`、`list<string>`、`list<object>`、`set<int>`、`set<long>`、`set<float>`、`set<bool>`、`set<string>`、`set<object>`。
- 如果字段需要加密，就需要在括号内填写 `encrypt`，如(object,encrypt)、(int,encrypt)。

### 配置 Gradle

修改项目根目录的 build.gradle

```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'io.github.taoweiji.kvstorage:kvstorage-gradle-plugin:+'
    }
}
```

修改公共 module（或 app）的  build.gradle

```groovy
apply plugin: 'kvstorage'
dependencies {
    implementation 'io.github.taoweiji.kvstorage:kvstorage:+'
}
kvstorage {
    yamlFiles = ["storage.yaml"] // 配置文件，多个配置文件用","分割
    packageName = "com.taoweiji.kvstorage.example" // 生成代码的包名
    outputDir = "src/main/java" // 代码生成的目录
}
```

点击 Gradle sync 即可生成对应的类。

### 使用

```kotlin
// 在Application进行初始化
KVStorage.init(this)

// 对象操作
Storage.get().account.loginAccount.setObject(Account(1, "Wiki"))
val account = Storage.get().account.loginAccount.convert(Account::class.java)
Storage.get().account.loginAccount.set("gender", 1)
val name = Storage.get().account.loginAccount.getString("name", "")
Log.e(TAG, "登录用户信息：" + Storage.get().account.loginAccount.data)

Log.e(TAG, "最后打开APP时间：" + Date(Storage.get().global.lastOpenAppTime))
Storage.get().global.lastOpenAppTime = System.currentTimeMillis()

Storage.get().global.openAppNumOfTimes++
Log.e(TAG, "打开APP次数：${Storage.get().global.openAppNumOfTimes}")

if (Storage.get().isTeenagerMode) {
    Log.e(TAG, "是否开启青少年模式：" + Storage.get().isTeenagerMode)
}
Storage.get().isTeenagerMode = true

// 列表操作
Storage.get().currentUserTags.add("可爱")
Storage.get().currentUserTags.add("好看")
Log.e(TAG, "当用户标签：" + JSON.toJSONString(Storage.get().currentUserTags.data))

Storage.get().browsingHistory.addObject(
    BrowsingHistory(
        "link",
        "http://github.com/taoweiji"
    )
)
Log.e(
    TAG,
    "浏览记录：" + JSON.toJSONString(Storage.get().browsingHistory.convert(BrowsingHistory::class.java))
)
```



## 高级用法

### 自定义文件名，实现多用户存储隔离

如果账号支持多用户登录，或者切换账号的时候需要保留上一个账号的信息，可以通过自定义存储的文件名称实现区分。

```java
KVStorage.init(this, new KVStorage.Interceptor() {
    @Override
    public String customFileName(@NonNull String name) {
        // 设置 storage.global 全局生效，不对用户隔离
        if (name.equals("storage.global")) {
            return name;
        }
        // 默认所有配置都进行用户隔离
        String userId = "1234";
        return userId + "_" + name;
    }
});
```



### 自定义对象序列化、反序列化

默认使用 FastJson 作为序列化库，开发者可以自定义序列化库，下面是gson的示例。
```groovy
dependencies {
    implementation('io.github.taoweiji.kvstorage:kvstorage:+') {
        // 移除 fastjson 依赖
        exclude group: 'com.alibaba', module: 'fastjson'
    }
    implementation 'com.google.code.gson:gson:2.8.6'
}
```
修改初始化
```java
KVStorage.init(this, new KVStorage.Interceptor() {
    @Override
    public String toJSONString(@NonNull Object object) {
        return new Gson().toJson(object);
    }

    @Override
    public <T> List<T> parseArray(@NonNull String text, Class<T> clazz) {
        Type userListType = new TypeToken<ArrayList<T>>() {}.getType();
        return new Gson().fromJson(text, userListType);
    }

    @Override
    public <T> T parseObject(@NonNull String text, Class<T> clazz) {
        return new Gson().fromJson(text, clazz);
    }
});
```



### 自定义加密/解密算法

```yaml
login_account(object,encrypt): #登录信息，加密
```

通过 `encrypt` 可以对字段进行单独的加密，避免敏感信息明文存储在文件当中，比如密码、手机号码等。框架默认使用 base64作为简单处理，避免明文存储。但是base64并不是一个加密算法，如果需要对字段加密需要自己实现相关接口，推荐官方的 [KeyStore](https://developer.android.google.cn/training/articles/keystore.html)。

```java
KVStorage.init(this, new KVStorage.Interceptor() {
    @Override
    public String decryption(@NonNull String data) {
      	// TODO 解密
        // return new String(Base64.decode(data, Base64.DEFAULT));
    }

    @Override
    public String encryption(@NonNull String data) {
      	// TODO 加密
        // return Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
    }
});
```
### 自定义存储数据源：云同步键值对存储服务
开发者可以自定义存储数据源，比如用来实现本地键值对数据同步到云端，跟随着用户登录的账号，不会随着卸载而丢失，但是与服务端联动的代码需要开发者自行实现。
#### 定义需要同步的分组
```yaml
#storage.yaml
cloud:
  open_comment_push(bool): true #是否开启评论推送
  login_divices(list<object>): #登录的设备信息
```
#### 自定义存储数据源
```java
KVStorage.init(this, new KVStorage.Interceptor() {
    @Override
    public PreferencesAdapter createPreferencesProvider(String fileName) {
        if(fileName.equals("storage.cloud")){
            // TODO 针对 storage.cloud 分组自行实现云同步
        }
        return super.createPreferencesProvider(fileName);
    }
});
```


### 自定义存储数据源：使用MMKV作为存储数据源

默认是使用 SharedPreferences 作为存储底层，开发者可以自定义存储底层，比如使用 [MMKV](https://github.com/Tencent/MMKV) 作为底层存储，从而获取更高的性能。

#### 引入 mmkv 拓展

```groovy
implementation 'io.github.taoweiji.kvstorage:kvstorage-mmkv:+'
```

#### 初始化

```java
MMKV.initialize(this);

KVStorage.init(this, new KVStorage.Interceptor() {
    @Override
    public PreferencesAdapter createPreferencesProvider(String fileName) {
      	// 返回 mmkv 适配器
        return new MMKVPreferencesProvider(fileName);
    }
});
```

这样存储数据源就会改成mmkv，其它用法没有任何差异。


### 只读KV类生成插件：实现在线参数服务
通常的项目都会有在线参数服务，用于下发参数到客户端，控制客户端的显示逻辑，部分在线参数服务还具备ab测试能力，不同的用户下发不同的参数，比如只让部分用户体验新功能。客户端使用参数服务和键值对存储使用方式类似，不同的是参数服务只允许读取数据。对于一个成熟的APP，必然会大规模使用在线参数服务，就会存在大量的字段名，和键值对一样，如果没有合理使用，也会让代码变得凌乱。KVStorage也提供了解决方案，可以根据yaml配置文件生成相关的工具类，但是后端服务和客户端服务提供还是需要开发者自行实现。

#### 创建cloud_config.yaml

在公共的Module目录下创建cloud_config.yaml，填写对应的自定义，编写规则和 storage 是一样的，也支持分组，config仅支持 int、long、float、bool、string类型。

```yaml
# cloud_config.yaml
ad:
  launch_app_ads(string): #启动APP广告
  launch_app_alert_ads(bool): #启动APP是否显示弹出广告
  alert_ads_show_duration(long): 3000 #弹窗广告显示时长，单位：秒

launch_welcome_message(string): 欢迎使用KVStorage #启动欢迎语
splash_screen_duration(long): 1000 #闪屏时长
home_default_tab_index(int): 0 #首页默认显示第几个tab
```

##### 修改build.gradle

```groovy
apply plugin: 'kvstorage'
dependencies {
    implementation 'io.github.taoweiji.kvstorage:kvstorage:+'
}
kvstorage {
    packageName = "com.taoweiji.kvstorage.example" // 生成代码的包名
    outputDir = "src/main/java" // 代码生成的目录
  	configYamlFiles = ["cloud_config.yaml"]
}
```

#### 实现数据提供

配置服务端的数据提供需要开发者自行实现。

```java
KVStorage.setReadOnlyConfigProvider(new KVStorage.ReadOnlyConfigProvider() {
    @Override
    public int getInt(String groupName, String key, int def) {
      	// TODO 
    }
    @Override
    public float getFloat(String groupName, String key, float def) {
      	// TODO 
    }
    @Override
    public long getLong(String groupName, String key, long def) {
      	// TODO 
    }
    @Override
    public boolean getBool(String groupName, String key, boolean def) {
      	// TODO 
    }
    @Override
    public String getString(String groupName, String key, String def) {
      	// TODO 
    }
});
```

#### 使用

```kotlin
if (CloudConfig.get().ad.isLaunchAppAlertAds) {
	println("显示弹出广告：" + CloudConfig.get().ad.launchAppAds)
}
```



## License

    Copyright 2021 taoweiji.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

