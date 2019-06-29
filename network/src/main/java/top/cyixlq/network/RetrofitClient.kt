package top.cyixlq.network

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import top.cyixlq.network.bean.BaseResponse
import top.cyixlq.network.config.API_VERSION
import top.cyixlq.network.config.CLIENT_ID
import top.cyixlq.network.config.SERVICE_ERR
import top.cyixlq.network.exception.CustomNetException
import top.cyixlq.network.service.CommanService
import top.cyixlq.network.utils.FormatUtil

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

    fun execute(): Observable<String> {
        if (type == null) {
            throw RuntimeException("You must set the api type")
        }
        val gson = FormatUtil.getInstance().getGson()
        val data = HashMap<String, Any>()
        data["client_id"] = CLIENT_ID
        data["itboye"] = NetWorkManager.getInstance().getConvert().encodeData(params, type!!, apiVersion)
        val service = RetrofitManager.getInstance().create(CommanService::class.java)
        val observable = service.post(data)
        return observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .map {
                gson.fromJson<BaseResponse>(it, BaseResponse::class.java)
            }.flatMap {
                val obj = it.data as? String ?: throw CustomNetException(SERVICE_ERR, it.msg)
                val json = NetWorkManager.getInstance().getConvert().decodeData(obj)
                Observable.just(json)
            }
    }
}