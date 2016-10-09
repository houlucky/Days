#Days
### 简介
Days——是一款可以记录生活点滴、记录生命中重要的日子、看妹子图片的APP，自己呢平时容易忘记一些东西，所以一直想写一个App来帮助自己记录生活中一些比较重要的事情，所以呢就有了Days这款app的诞生。
- 写日记
- 纪念日 
- 每日妹子图片
- 缓存数据，减少网络请求，保证离线查看
- 本地化储存数据
----

- **开源不易，希望能给个Star鼓励**
- 项目地址：https://github.com/houlucky/Days
- 项目主页发布issue: https://github.com/houlucky/Days/issues
- 本项目为开源项目,技术交流可以通过邮箱联系: houxya@gmail.com

----

权限说明

```xml
    <!--允许联网 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <!--获取GSM（2g）、WCDMA（联通3g）等网络状态的信息  -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <!--获取wifi网络状态的信息 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <!--获取sd卡写的权限，用于文件上传和下载-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <!--以下两个都是使用bomb sdk所需要的权限-->
    <!--保持CPU 运转，屏幕和键盘灯有可能是关闭的,用于文件上传和下载 -->
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <!--允许读取手机状态 用于创建BmobInstallation-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
```
### TODO
- [ ] 通知栏提醒
- [ ] 更新缓存方式
- [ ] 重要日志置顶
- [ ] 查看妹子大图
- [ ] 自由定制的Item界面
- [ ] 引导页面
- [ ] 沉浸式状态栏
- [ ] 数据同步到第三方服务器Bmob

----

### 项目
#### 公开 API

每日妹子图片来源于:：[干货集中营][0]
### 第三方服务器

数据的云储存：[Bmob][1]

#### 开源技术
1. [Rxjava][2]
2. [RxAndroid][3]
3. [Retrofit][4]
4. [Glide][5]

### 截图

![][image-0]
### 感谢
感谢开源，学习到了前辈们优秀的代码
- [@张鸿洋][7]
- [@扔物线][8]
- [@drakeet][9]
- [@代码家][10]
- [@泡在网上编代码][14]

### 关于作者

简书：http://www.jianshu.com/users/7999fb63f104/latest_articles

微博：http://weibo.com/houlucky

个人博客： http://www.houlucky.cn/

### LICENSE

```
Copyright 2016 houlucky  Licensed under the Apache License, Version 2.0 (the \"License\")
you may not use this file except in compliance with the License. You may obtain a copy of the License at 
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed 
on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express orimplied. 
See the License for the specific language governing permissions and limitations under the License.
```

图片来源于网络，版权属于原作者。



[0]: http://gank.io/
[1]:  http://www.bmob.cn/
[2]: https://github.com/ReactiveX/RxJava
[3]: https://github.com/ReactiveX/RxAndroid
[4]: https://github.com/square/retrofit
[5]: https://github.com/bumptech/glide
[6]: https://github.com/yangfuhai/ASimpleCache
[7]: https://github.com/hongyangAndroid
[8]: https://github.com/rengwuxian
[9]: https://github.com/drakeet
[10]: https://github.com/daimajia
[13]: https://github.com/Jude95
[14]: http://weibo.com/u/2711441293?topnav=1&wvr=6&topsug=1&is_all=1


[image-0]:  images/app.png
[image-1]:  images/1.png
[image-2]:  images/2.png
[image-3]:  images/3.png
