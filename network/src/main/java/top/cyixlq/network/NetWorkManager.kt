package top.cyixlq.network

import android.app.Application
import android.content.Context
import top.cyixlq.network.convert.IConvert
import top.cyixlq.network.convert.SampleConvert
import top.cyixlq.network.service.CommonService
import top.cyixlq.network.utils.CLog


class NetWorkManager private constructor(){
    private object SingleHolder {
        val instance = NetWorkManager()
    }

    companion object {
        @JvmStatic
        fun getInstance(): NetWorkManager {
            return SingleHolder.instance
        }
    }

    private var commonService: CommonService = RetrofitManager.getInstance().create(CommonService::class.java)

    private var convert: IConvert? = null
    private var application: Application? = null
    private var version = "un_know"

    fun setConvert(convert: IConvert): NetWorkManager {
        this.convert = convert
        return this
    }

    fun setEnableLog(isShow: Boolean): NetWorkManager {
        CLog.init(isShow)
        return this
    }

    fun init(application: Application) {
        if (this.convert == null) {
            this.convert = SampleConvert()
        }
        this.version = getAppVersionName(application)
        this.application = application
    }

    fun getConvert(): IConvert {
        this.convert?.let {
            return it
        }
        throw RuntimeException("You must init NetWorkManager")
    }

    private fun getAppVersionName(context: Context): String {
        var versionName: String
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versionName = pi.versionName
            if (versionName == null || versionName.isEmpty()) {
                versionName =  "un_know"
            }
        } catch (e: Exception) {
            return "un_know"
        }
        return versionName
    }

    fun getApplication(): Application {
        this.application?.let {
            return it
        }
        throw RuntimeException("You must init NetWorkManager")
    }

    fun getVersionName(): String {
        return this.version
    }

    fun getCommonService(): CommonService {
        return commonService
    }
}