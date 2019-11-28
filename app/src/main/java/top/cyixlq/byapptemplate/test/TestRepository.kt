package top.cyixlq.byapptemplate.test

import io.reactivex.Observable
import top.cyixlq.byapptemplate.api.EbuyService
import top.cyixlq.byapptemplate.bean.HotKey
import top.cyixlq.network.ByNet

class TestDataSourceRepository(val remote: TestRemoteDataSource = TestRemoteDataSource(),
                               val local: TestLocalDataSource = TestLocalDataSource()) {
    fun getHotKeys(size: Int): Observable<List<HotKey>> {
        return remote.getHotKeys(size)
    }
}

class TestRemoteDataSource {
    fun getHotKeys(size: Int): Observable<List<HotKey>> {
        return ByNet.get().create(EbuyService::class.java)
            .getKotKeys(size)
    }
}

class TestLocalDataSource