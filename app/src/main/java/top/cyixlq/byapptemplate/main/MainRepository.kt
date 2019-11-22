package top.cyixlq.byapptemplate.main

import io.reactivex.Flowable
import okhttp3.ResponseBody
import top.cyixlq.byapptemplate.api.MusicService
import top.cyixlq.byapptemplate.bean.AddressItem
import top.cyixlq.byapptemplate.bean.Result
import top.cyixlq.byapptemplate.bean.VersionData
import top.cyixlq.core.net.RetrofitManager
import top.cyixlq.core.utils.RxSchedulers
import top.cyixlq.network.RetrofitClient
import top.cyixlq.network.utils.getTypeToken

class MainRepository(
    private val remote: MainRemoteDataSource = MainRemoteDataSource(),
    private val local: MainLocalDataSource = MainLocalDataSource()
) {
    fun getMainData(sid: String): Flowable<Result<Any>> {
        // 通过RxJava串行发送网络请求任务
        return Flowable.concat(
            remote.getVersionData(),
            remote.getAddressList(sid)
        )
    }

    fun getMusicData(): Flowable<Result<ResponseBody>> {
        return remote.getMusicData()
    }
}

// 远程数据源class（服务端）
class MainRemoteDataSource {
    fun getAddressList(sid: String): Flowable<Result<List<AddressItem>>> {
        return RetrofitClient.create().setType("BY_Address_all")
            .addParam("sid", sid)
            .executeFlowable(typeToken = getTypeToken<List<AddressItem>>())
            .map { Result.success(it) }
            .onErrorReturn { Result.failure(it) }
    }

    fun getVersionData(): Flowable<Result<VersionData>> {
        return RetrofitClient.create()
            .setType("BY_Config_version")
            .addParam("appid", "by565fa4facdb191")
            .executeFlowable(VersionData::class.java)
            .map { Result.success(it) }
            .onErrorReturn { Result.failure(it) }
    }

    fun getMusicData(): Flowable<Result<ResponseBody>> {
        return RetrofitManager.getInstance().create(MusicService::class.java)
            .getMusicData("001qvvgF38HVc4", "qq",
                "fun_get_music_url", "flac", "mkfsldlf", "fmosd")
            .observeOn(RxSchedulers.io)
            .subscribeOn(RxSchedulers.io)
            .map { Result.success(it) }
            .onErrorReturn { Result.failure(it) }
    }
}

// 本地数据源class（SQLite，SP等）
class MainLocalDataSource