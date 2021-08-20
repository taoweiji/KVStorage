# 强大的 Android 键值对存储框架，基于 yaml 生成 java 存储对象类

 [![Maven Central](https://img.shields.io/maven-central/v/io.github.taoweiji.kvstorage/kvstorage)](https://search.maven.org/search?q=io.github.taoweiji.kvstorage)


使用 yaml 配置文件声明键值对的字段名、类型、默认值、是否加密、分组等，通过 Gradle 插件自动生成对象类，让键值对存储的使用变成get/set形式，让代码调用更加安全、优雅。

- 支持对字段单独加密，避免敏感信息明文存储。
- 通过 yaml 配置存储字段信息，配置简单易懂，一目了然；
- 支持基本类型、对象和列表数据，代替SharePreference及部分SQLite场景。
- 支持自定义文件名，实现多用户之间的数据隔离。

#### 推荐规范

- 推荐只使用一个yaml配置文件，放在公共 module 内，方便组件化开发调用；
- 推荐不同的业务划分不同的分组，避免所有的配置都写在一个组内；
- 编写yaml时，所有的字段都应该编写注释，说明其用法；
- bool类型数据不用增加is_前缀，bool类型会默认生成 setXX / isXX 方法；

### 编写 yaml 配置文件

在公共 module（或 app）目录下创建 yaml 配置文件，比如 storage.yaml 会生成 Storage.java，不同文件名存储数据是隔离的，一旦定好后不建议修改。一个项目无论有多少个 module，都推荐使用一个 yaml 配置文件，放在公共的module里面。

```yaml
account:
    login_account(object,encrypt): #用户登录信息
    login_time(long): 0 #上次登录时间
    login_type(string): null #登录方式：qq、weixin、weibo、phone
browsing_history(list<object>): #浏览记录
teenager_mode(bool): false #是否开启青少年模式
current_user_tags(list<string>): #当前用户标签
global:
  open_app_num_of_times(int): 0 #打开APP的次数
  last_open_app_time(long): 0 #上次打开app时间，13位
```

- 配置文件仅允许使用小写字母、数字、下划线，字段必须使用字母开头。
- 带有括号 `()`是字段，否则就是分组。如上，account 是分组，而login_account、login_time是字段。
- 字段必须在括号`()`内填写类型，仅支持`int`、`long`、`float`、`bool`、`string`、`object`、`list<int>`、`list<long>`、`list<float>`、`list<bool>`、`list<string>`、`list<object>`。
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
    yamlFiles = "storage.yaml" // 配置文件，多个配置文件用","分割
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
Log.e("KVStorage", "登录用户信息：" + Storage.get().account.loginAccount.data)

val lastLoginTime = Storage.get().account.loginTime
Storage.get().account.loginTime = System.currentTimeMillis()

Storage.get().global.openAppNumOfTimes++
Log.e("KVStorage", "打开APP次数：${Storage.get().global.openAppNumOfTimes}")

if (Storage.get().isTeenagerMode) {
    Log.e("KVStorage", "是否开启青少年模式：" + Storage.get().isTeenagerMode)
}
Storage.get().isTeenagerMode = true

// 列表操作
Storage.get().currentUserTags.add("可爱")
Storage.get().currentUserTags.add("好看")
Log.e("KVStorage", "当用户标签：" + JSON.toJSONString(Storage.get().currentUserTags.data))
```



## 高级用法

### 自定义文件名，实现多用户隔离

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

