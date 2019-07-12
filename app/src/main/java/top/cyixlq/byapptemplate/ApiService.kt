package top.cyixlq.byapptemplate

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/music/A")
    fun getVKey(@Query("a") a: String, @Query("b") b: String, @Query("c") c: String): Call<String>
}