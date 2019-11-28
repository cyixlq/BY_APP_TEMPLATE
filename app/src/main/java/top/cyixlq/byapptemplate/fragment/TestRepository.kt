package top.cyixlq.byapptemplate.fragment

import io.reactivex.Flowable
import top.cyixlq.byapptemplate.api.EbuyService
import top.cyixlq.byapptemplate.bean.Result
import top.cyixlq.byapptemplate.bean.VersionData
import top.cyixlq.network.ByNet

class TestDataSourceRepository(
    private val remote: TestRemoteDataSource = TestRemoteDataSource(),
    private val local: TestLocalDataSource = TestLocalDataSource()
) {
    fun getVersionDataNew(): Flowable<Result<VersionData>> {
        return remote.getVersionDataNew()
    }
}

class TestRemoteDataSource {
    fun getVersionDataNew(): Flowable<Result<VersionData>> {
        val map = HashMap<String, String>()
        map["appid"] = "by565fa4facdb191"
        return ByNet.get().create(EbuyService::class.java)
            .getVersionData(map)
            .map { Result.success(it) }
            .onErrorReturn { Result.failure(it) }
    }
}

class TestLocalDataSource