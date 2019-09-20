package top.cyixlq.common.utils

import android.os.Build
import androidx.annotation.RequiresApi
import top.cyixlq.common.CommonManager

object VersionUtil {

    fun getVersionName(): String {
        val packageManager = CommonManager.getApplication().packageManager
        val packageInfo = packageManager.getPackageInfo(CommonManager.getApplication().packageName, 0)
        return packageInfo.versionName
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getLongVersionCode(): Long {
        val packageManager = CommonManager.getApplication().packageManager
        val packageInfo = packageManager.getPackageInfo(CommonManager.getApplication().packageName, 0)
        return packageInfo.longVersionCode
    }

    fun getVersionCode(): Int {
        val packageManager = CommonManager.getApplication().packageManager
        val packageInfo = packageManager.getPackageInfo(CommonManager.getApplication().packageName, 0)
        return packageInfo.versionCode
    }

}