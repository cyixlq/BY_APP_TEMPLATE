package top.cyixlq.core.utils

import android.annotation.SuppressLint
import android.content.Context
import top.cyixlq.core.CoreManager

object DisplayUtil {

    @SuppressLint("PrivateApi")
    fun getStatusbarHeight(): Int {
        var statusBarHeight = 0
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val o = c.newInstance()
            val field = c.getField("status_bar_height")
            val x = field.get(o) as Int
            statusBarHeight = CoreManager.getApplication().resources.getDimensionPixelSize(x)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusBarHeight
    }

    fun getScreenHeightExcludeStatusbar(context: Context): Int {
        return context.resources.displayMetrics.heightPixels - getStatusbarHeight()
    }

    fun px2dp(pxValue: Float): Int {
        val density = CoreManager.getApplication().resources.displayMetrics.density
        return (pxValue / density + 0.5f).toInt()
    }

    fun dp2px(dpValue: Float): Int {
        val density = CoreManager.getApplication().resources.displayMetrics.density
        return (dpValue * density + 0.5f).toInt()
    }

    fun px2sp(pxValue: Float): Int {
        val scaleDensity = CoreManager.getApplication().resources.displayMetrics.scaledDensity
        return (pxValue / scaleDensity + 0.5f).toInt()
    }

    fun sp2px(context: Context, spValue: Float): Int {
        val scaleDensity = context.resources.displayMetrics.scaledDensity
        return (spValue * scaleDensity + 0.5f).toInt()
    }
}
