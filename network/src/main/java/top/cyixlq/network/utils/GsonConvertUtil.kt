package top.cyixlq.network.utils

import com.google.gson.reflect.TypeToken

inline fun <reified T> String.jsonToObject(): T =
    FormatUtil.getInstance().getGson().fromJson<T>(this, object : TypeToken<T>() {}.type)