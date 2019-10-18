package top.cyixlq.network

import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import top.cyixlq.core.net.exception.CustomNetException
import top.cyixlq.core.utils.FormatUtil
import top.cyixlq.network.bean.BaseResponse
import top.cyixlq.network.config.API_VERSION
import top.cyixlq.network.config.RESULT_OK
import top.cyixlq.network.config.SERVICE_ERR

class RetrofitClient private constructor() {
    companion object {
        @JvmStatic
        fun create(): RetrofitClient {
            return RetrofitClient()
        }
    }

    private var params: HashMap<String, Any> = HashMap()
    private var type: String? = null
    private var apiVersion: String = API_VERSION

    fun addParam(key: String, value: Any): RetrofitClient {
        this.params[key] = value
        return this
    }

    fun setParams(params: HashMap<String, Any>): RetrofitClient {
        this.params = params
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

    private fun getData(): HashMap<String, Any> {
        if (type == null)
            throw RuntimeException("You must set the api type")
        return ByNetWorkManager.getInstance().getConvert().encodeData(params, type!!, apiVersion)
    }

    /**
     *  返回Observable部分方法重载
     */
    fun <T> executeObservable(clazz: Class<T>): Observable<T> {
        return executeObservable(clazz = clazz, typeToken = null)
    }

    fun <T> executeObservable(typeToken: TypeToken<T>): Observable<T> {
        return executeObservable(clazz = null, typeToken = typeToken)
    }

    private fun <T> executeObservable(clazz: Class<T>? = null, typeToken: TypeToken<T>? = null): Observable<T> {
        return ByNetWorkManager.getInstance().getCommonService().post(getData())
            .subscribeOn(Schedulers.io())
            .map {
                val baseResponse = FormatUtil.getGson().fromJson(it.string(), BaseResponse::class.java)
                if (baseResponse.code != RESULT_OK) {
                    throw CustomNetException(baseResponse.code, baseResponse.msg)
                }
                baseResponse
            }
            .map {
                val result = it.data as? String ?: throw CustomNetException(SERVICE_ERR, it.msg)
                val decodeString = ByNetWorkManager.getInstance().getConvert().decodeData(result)
                Logger.json(decodeString)
                if (clazz != null) {
                    return@map FormatUtil.getGson().fromJson<T>(decodeString, clazz)
                }
                else {
                    if (typeToken == null) {
                        throw RuntimeException("At least one of clazz and typeToken is not null")
                    }
                    return@map FormatUtil.getGson().fromJson<T>(decodeString, typeToken.type)
                }
            }
    }

    /**
     *  返回Flowable部分方法重载
     */
    fun <T> executeFlowable(clazz: Class<T>): Flowable<T> {
        return executeFlowable(clazz = clazz, typeToken = null)
    }

    fun <T> executeFlowable(typeToken: TypeToken<T>): Flowable<T> {
        return executeFlowable(clazz = null, typeToken = typeToken)
    }

    private fun <T> executeFlowable(clazz: Class<T>? = null, typeToken: TypeToken<T>? = null): Flowable<T> {
        return ByNetWorkManager.getInstance().getCommonService().postFlowable(getData())
            .subscribeOn(Schedulers.io())
            .map {
                val baseResponse = FormatUtil.getGson().fromJson(it.string(), BaseResponse::class.java)
                if (baseResponse.code != RESULT_OK) {
                    throw CustomNetException(baseResponse.code, baseResponse.msg)
                }
                baseResponse
            }
            .map {
                val result = it.data as? String ?: throw CustomNetException(SERVICE_ERR, it.msg)
                val decodeString = ByNetWorkManager.getInstance().getConvert().decodeData(result)
                Logger.json(decodeString)
                if (clazz != null) {
                    return@map FormatUtil.getGson().fromJson<T>(decodeString, clazz)
                } else {
                    if (typeToken == null) {
                        throw RuntimeException("At least one of clazz and typeToken is not null")
                    }
                    return@map FormatUtil.getGson().fromJson<T>(decodeString, typeToken.type)
                }
            }
    }
}