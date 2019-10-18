package top.cyixlq.byapptemplate.api

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.*

interface MusicService {

    @FormUrlEncoded
    @Headers(
        "Host: music.ghpym.com",
        "referer: https://music.ghpym.com/api/ajax.php",
        "User-Agent: Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1)"
    )
    @POST("https://music.ghpym.com/api/ajax.php")
    fun getMusicData(
        @Field("id") id: String,
        @Field("site") site: String,
        @Field("fun") func: String,
        @Field("type") type: String,
        @Field("token") token: String,
        @Field("w") appName: String
    ): Flowable<ResponseBody>
}