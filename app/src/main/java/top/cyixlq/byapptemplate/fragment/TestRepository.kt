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
        return ByNet.create(EbuyService::class.java)
            .getVersionData("by565fa4facdb191")
            .map { Result.success(it) }
            .onErrorReturn { Result.failure(it) }
    }
}

class TestLocalDataSource