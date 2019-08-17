package top.cyixlq.network.utils

import com.google.gson.reflect.TypeToken

inline fun <reified T> String.jsonToObject(): T =
    FormatUtil.getGson().fromJson<T>(this, object : TypeToken<T>() {}.type)

fun Any.toJson(): String = FormatUtil.getGson().toJson(this)

inline fun <reified T> Any.getTypeToken(): TypeToken<T> = object : TypeToken<T>() {}

/*fun <T> Flowable<String>.toResultObject(clazz: Class<T>): Flowable<Result<T>> =
    this.map { Result.success<T>(FormatUtil.getGson().fromJson(it, clazz)) }
        .onErrorReturn { Result.failure<T>(it) }

fun <T> Flowable<String>.toResultObject(typeToken: TypeToken<T>): Flowable<Result<T>> =
    this.map { Result.success<T>(FormatUtil.getGson().fromJson(it, typeToken.type)) }
        .onErrorReturn { Result.failure<T>(it) }

fun <T> Observable<String>.toResultObject(clazz: Class<T>): Observable<Result<T>> =
    this.map { Result.success<T>(FormatUtil.getGson().fromJson(it, clazz)) }
        .onErrorReturn { Result.failure<T>(it) }

fun <T> Observable<String>.toResultObject(typeToken: TypeToken<T>): Observable<Result<T>> =
    this.map { Result.success<T>(FormatUtil.getGson().fromJson(it, typeToken.type)) }
        .onErrorReturn { Result.failure<T>(it) }*/
