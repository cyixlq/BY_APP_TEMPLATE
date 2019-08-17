package top.cyixlq.network.utils

import com.google.gson.Gson

object FormatUtil {

    private val gson = Gson()

    fun getGson(): Gson {
        return gson
    }
}