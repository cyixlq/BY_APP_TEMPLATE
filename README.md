## BY_APP_TEMPLATE

参照[MVVM-Rhine](https://github.com/qingmei2/MVVM-Rhine)打造的，适合博也公司项目的MVVM快速开发模板，精简了di及其他一些小部分（主要是我还没把MVVM-Rhine中的一些东西吃透）。致力于让业务逻辑更清晰有条理。**特别适用于**类似ebuy这种进行层层加密的网络请求方式。推荐Kotlin开发语言。Kotlin已经官宣了，还不用起来？本模板只适用于AndroidX！

## 快速开始

按需引入library，分为common模块和network模块。如果项目没有用到像ebuy这种层层加密网络请求方式，那么只需依赖common模块即可。

**模块初始化：**
1. 如果引入了common模块，在自定义的Application中的onCreate方法加入以下代码：
    ```
    CommonManager.init(this)
    ```
2. 如果引入了network模块，在自定义的Application中的onCreate方法加入以下代码：
    ```
    NetWorkManager.getInstance().setEnableLog(BuildConfig.DEBUG).init(this)
    ```
    其中setEnableLog方法是开启网络日志的开关，true为开，false为关。

    另外，setConvert方法未展示，其作用是自定义参数加密解密的转换器，如果未设置将使用默认的转换器，其相关加密解密方法参照ebuy项目。如果需要自己设计实现，请参考network模块中的SampleConvert。

**防止RxJava内存泄漏：**

common模块引入了AutoDispose框架，在生命周期结束而自动断开与RxJava的连接只需一行代码：
1. Activity中，必须继承自common模块中的BaseActivity，在RxJava流中添加如下Kotlin代码：
    ```
    .autoDisposable(scopeProvider)
    ```
2. Fragment中，必须继承自common模块中的BaseFragment，在RxJava流中添加如下Kotlin代码：
    ```
    .autoDisposable(scopeProvider)
    ```
3. ViewModel中，必须继承自common模块中的BaseViewModel，在RxJava流中添加如下Kotlin代码：
    ```
    .autoDisposable(this)
    ```
**network模块配置**

配置文件位于network模块config包中，NetWorkConfig.kt文件为配置文件，已经添加相关注释，请自行更改。

**其他亮点请自行阅读app模块下源码，一些设计保证了条理清晰，请遵循他们！**

## 辅助工具

快速生成模板代码插件[BY_APP_TEMPLATE-Template](https://github.com/cyixlq/BY_APP_TEMPLATE-Template)

使用此插件可以快速生成**Kotlin**模板代码，增加键盘寿命，减少无意义的时间消耗，争取早点下班。通过此插件生成的模板代码已经实现了下方中的开发规范，以及实现了此项目模板的设计理念，保证条理性。使用方法请参见该项目的README。**暂不支持Java模板代码生成！**

## 开发规范

开发规范已在demo中进行了展示，添加了相关注释，在此还是重复强调几点：

1. 切记千万不要自行创建Intent进行Activity的跳转，强制每个Activity创建时必须提供一个launch方法，在方法参数中添加跳转所需要携带的值。此举是为了规范界面间的传值。launch方法的实现参照TestActivity。
2. 创建Fragment请不要在所需要的地方直接进行实例化，反例：
    ```
    val testFragment = TestFragment()
    ```
    应当使用要创建的Fragment自身提供静态instance方法，正例：
    ```
    class TestFragment: Fragment() {
        companion object {
        fun instance(title: String): TestFragment {
            val bundle = Bundle().apply {
                putString("title", title)
            }
            return TestFragment().apply {
                arguments = bundle
            }
        }
    }

    // 在需要实例化的地方调用instance方法
    val testFragment = TestFragment.instance("test")
    ```
3. 强制所有依赖必须添加到config.gradle，此举是为了统一所有依赖版本。具体做法请参考demo！

4. 请等待后续添加

## 后话

使用此项目模板遇到问题可以先提个issue，然后尝试自己修改源代码，如果问题得到解决还可以在提的issue中追加自己的解决方案。