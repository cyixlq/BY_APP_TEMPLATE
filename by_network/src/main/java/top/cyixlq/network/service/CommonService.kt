package top.cyixlq.network.service

import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.POST
import retrofit2.http.QueryMap
import retrofit2.http.Url
import top.cyixlq.network.config.INDEX_URL

interface CommonService {

    @POST
    fun post(@Url url: String, @QueryMap params: HashMap<String, Any>) : Observable<ResponseBody>

    @POST
    fun postFlowable(@Url url: String, @QueryMap params: HashMap<String, Any>) : Flowable<ResponseBody>
}