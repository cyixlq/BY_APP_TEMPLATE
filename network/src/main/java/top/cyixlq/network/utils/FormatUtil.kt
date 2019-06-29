package top.cyixlq.network.utils

import com.google.gson.Gson

class FormatUtil private constructor(){

    private val gson = Gson()

    private object SingleHolder {
        val instance = FormatUtil()
    }

    companion object {
        @JvmStatic
        fun getInstance(): FormatUtil {
            return SingleHolder.instance
        }
    }

    fun getGson(): Gson {
        return gson
    }
}