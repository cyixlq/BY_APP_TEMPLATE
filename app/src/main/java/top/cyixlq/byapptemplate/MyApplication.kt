package top.cyixlq.byapptemplate

import android.app.Application
import top.cyixlq.core.CoreManager
import top.cyixlq.network.ByNetWorkManager


class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        CoreManager.init(this).configNetWork(baseUrl = "http://api.ebuycambodia.com")
            .configCLog(isEnableLog = true, showThreadInfo = false)
        ByNetWorkManager.init()
    }
}