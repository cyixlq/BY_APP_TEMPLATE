package top.cyixlq.network

import com.google.gson.reflect.TypeToken
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import top.cyixlq.core.utils.CLog
import top.cyixlq.network.config.API_VERSION
import top.cyixlq.network.config.INDEX_URL
import top.cyixlq.network.config.TAG_PARAMS
import top.cyixlq.network.utils.toJson
import java.lang.reflect.Type

/**
 *  利用这个类就可以适应博也的网络请求
 *  by_network模块是对于此的进一步封装
 */
class RetrofitClient private constructor() {

    companion object {
        @JvmStatic
        fun create(): RetrofitClient {
            return RetrofitClient()
        }
    }

    private var customArgs: HashMap<String, Any>? = null
    private var params: HashMap<String, Any> = HashMap()
    private var type: String? = null
    private var apiVersion: String = API_VERSION
    private var url = INDEX_URL

    fun addParam(key: String, value: Any): RetrofitClient {
        this.params[key] = value
        return this
    }

    fun addParams(params: HashMap<String, Any>): RetrofitClient {
        this.params.putAll(params)
        return this
    }

    fun setType(type: String): RetrofitClient {
        this.type = type
        return this
    }

    fun setApiVersion(apiVersion: String): RetrofitClient {
        this.apiVersion = apiVersion
        return this
    }

    fun setUrl(url: String): RetrofitClient {
        this.url = url
        return this
    }

    fun addCustomArg(key: String, arg: Any): RetrofitClient {
        if (this.customArgs == null) this.customArgs = HashMap()
        val globalCustomArgs = ByNetWorkManager.getGlobalCustomArgs()
        if (globalCustomArgs != null) {
            this.customArgs?.putAll(globalCustomArgs)
        }
        this.customArgs?.put(key, arg)
        return this
    }

    private fun getData(): HashMap<String, Any> {
        type?.let {
            CLog.t(TAG_PARAMS).json(params.toJson())
            val vCustomArgs = this.customArgs
            if (vCustomArgs != null) {
                return ByNetWorkManager.getConvert().encodeData(params, it, apiVersion, vCustomArgs)
            }
            return ByNetWorkManager.getConvert().encodeData(params, it, apiVersion, ByNetWorkManager.getGlobalCustomArgs())
        }
        throw RuntimeException("你必须给请求设置type")
    }

    /**
     *  返回Observable部分方法重载
     */
    @Suppress("UNCHECKED_CAST")
    @JvmOverloads
    fun <T> executeObservable(clazz: Class<T>? = null, typeToken: TypeToken<T>? = null, type: Type? = null): Observable<T> {
        return ByNetWorkManager.getCommonService().post(this.url, getData())
            .subscribeOn(Schedulers.io())
            .map {
                return@map ByNetWorkManager.getConvert().decodeData(it, clazz, typeToken, type) as T
            }
    }

    /**
     *  返回Flowable部分方法重载
     */
    @Suppress("UNCHECKED_CAST")
    @JvmOverloads
    fun <T> executeFlowable(clazz: Class<T>? = null, typeToken: TypeToken<T>? = null, type: Type? = null): Flowable<T> {
        return ByNetWorkManager.getCommonService().postFlowable(this.url, getData())
            .subscribeOn(Schedulers.io())
            .map {
                return@map ByNetWorkManager.getConvert().decodeData(it, clazz, typeToken, type) as T
            }
    }
}