# SimpleEyes

[![CircleCI](https://circleci.com/gh/AndyJennifer/SimpleEyes.svg?style=shield)](https://circleci.com/gh/AndyJennifer/SimpleEyes)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

SimpleEyes-Jetpack 分支基于 [Android Jetpack](https://developer.android.google.cn/jetpack)，并使用了 Google 推荐的 [应用架构指南](https://developer.android.google.cn/jetpack/docs/guide)。如果你正打算学习[Android Jetpack](https://developer.android.google.cn/jetpack)，相信该项目正好能帮助到你。

## 项目中使用到的 Jetpack 组件✨

- [x] DataBinding
- [ ] LiveData
- [x] Navigation
- [ ] Paging
- [x] VideModel
- [x] Lifecycles
- [ ] WorkManager

在项目中，因为个人喜好原因，并没有使用 [LiveData](https://developer.android.google.cn/topic/libraries/architecture/livedata#work_livedata) ，而是使用更为灵活的 RxJava。没有使用 Paging 与 WorkManager 的原因，是因为项目暂时还在开发中，故没有机会使用这些组件。

## 项目整体架构 🚌

![项目整体.jpg](https://upload-images.jianshu.io/upload_images/2824145-c766f88b8a3b028c.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

<table>

<tr>
	<td>组件</td>
	<td>角色</td>
	<td>依赖</td>
	<td>输入</td>
	<td>输出</td>
</tr>

<tr>
	<td>RemoteDataSource</td>
	<td>构建请求数据以及从 API 获取数据</td>
	<td>API service</td>
	<td>请求信息</td>
	<td>请求响应</td>
</tr>

<tr>
	<td>LocalDataSource</td>
	<td>本地数据存储</td>
	<td>SharedPreferences or Database</td>
	<td>用于存储的数据</td>
	<td>将数据进行存储</td>
</tr>

<tr>
	<td>Repository(数据仓库）</td>
	<td>用于存储或获取数据，同时也可以作为内存及的缓存(可选）</td>
	<td>RemoteDataSoruce and/or LocalDataSource</td>
	<td>检索数据或存储数据</td>
	<td></td>
</tr>

<tr>
	<td>UserCase</td>
	<td>根据自己的业务逻辑来处理数据</td>
	<td>Repository and/or UserCase</td>
	<td>ids</td>
	<td></td>
</tr>

<tr>
	<td>ViewModel</td>
	<td>用于提供UI所需要的数据，同时根据用户不操作触发不同的响应</td>
	<td>UserCase</td>
	<td>ids 或者用户行为</td>
	<td>Livedata< T ></td>
</tr>

<tr>
	<td>UI:Activity/xml</td>
	<td>用于展示数据，同时把用户操作传递给 ViewModel</td>
	<td>ViewModel</td>
	<td>ids 或者用户操作</td>
	<td>无</td>
</tr>
</table>

如果你对该该架构不是很熟悉，可以观看官方视频--> [Kotlin 语言帮助开发者更好的构建应用](https://v.qq.com/x/page/q3006tgkwbk.html) 了解更多内容。

## 额外使用的库 💪

- [koin](https://github.com/InsertKoinIO/koin)
- [AutoDispose](https://github.com/uber/AutoDispose)

## 参考

感谢以下开源项目提供的灵感与思路：

- [sunflower](https://github.com/android/sunflower)
- [rx-mvvm-android](https://github.com/ffgiraldez/rx-mvvm-android)

## 声明 📢

感谢 [开眼App](http://www.kaiyanapp.com) 提供参考，本人是豆瓣粉丝，使用了其中的 Api ，并非攻击。如构成侵权，请及时通知我删除或者修改。数据来源来自[开眼](https://www.kaiyanapp.com/) ，一切解释权归开眼所有。

## 最后

注意：此开源项目仅做学习交流使用。如用到实际项目，还需多考虑其他因素，请多多斟酌。如果你觉得该项目不错，欢迎点击 star ❤️，follow，也可以帮忙分享给你更多的朋友。你的支持与鼓励是给我继续做好该项目的最大动力。

## 联系我

- QQ:443696320
- 简书：[AndyandJennifer](https://www.jianshu.com/users/921c778fb5e1/timeline)
- 掘金：[AndyandJennifer](https://juejin.im/user/5acc1ea06fb9a028bc2e0fc1)
- Email: [andyjennifer@126.com](andyjennifer@126.com)

## License

```text
   Copyright [2019] [AndyJennifer]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
