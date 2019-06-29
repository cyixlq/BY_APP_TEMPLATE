package top.cyixlq.network.service

import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface CommanService {

    @POST("/index.php")
    fun post(@QueryMap params: HashMap<String, Any>) : Observable<String>
}