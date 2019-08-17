package top.cyixlq.network.service

import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface CommonService {

    @POST("/index.php")
    fun post(@QueryMap params: HashMap<String, Any>) : Observable<String>

    @POST("/index.php")
    fun postFlowable(@QueryMap params: HashMap<String, Any>) : Flowable<String>
}