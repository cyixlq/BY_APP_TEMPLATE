package top.cyixlq.byapptemplate

import android.app.Application
import top.cyixlq.common.CommonManager
import top.cyixlq.network.NetWorkManager

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        CommonManager.init(this)
        NetWorkManager.getInstance().setEnableLog(BuildConfig.DEBUG).init(this)
    }
}