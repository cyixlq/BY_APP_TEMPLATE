package top.cyixlq.byapptemplate.main

import io.reactivex.Flowable
import top.cyixlq.byapptemplate.bean.AddressItem
import top.cyixlq.byapptemplate.bean.VersionData
import top.cyixlq.network.RetrofitClient
import top.cyixlq.network.utils.getTypeToken
import top.cyixlq.byapptemplate.bean.Result

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
}

// 远程数据源class（服务端）
class MainRemoteDataSource {
    fun getAddressList(sid: String): Flowable<Result<List<AddressItem>>> {
        return RetrofitClient.create().setType("BY_Address_all")
            .addParam("sid", sid)
            .executeFlowable(getTypeToken<List<AddressItem>>())
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
}

// 本地数据源class（SQLite，SP等）
class MainLocalDataSource