package top.cyixlq.byapptemplate.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uber.autodispose.autoDisposable
import top.cyixlq.byapptemplate.bean.AddressItem
import top.cyixlq.byapptemplate.bean.VersionData
import top.cyixlq.byapptemplate.bean.Result
import top.cyixlq.core.common.viewmodel.CommonViewModel

class MainViewModel(private val repo: MainRepository): CommonViewModel() {

    val mViewStateSubject = MutableLiveData<MainViewState>()

    // 获取MainActivity页面所需要的全部数据
    @Suppress("UNCHECKED_CAST")
    fun getMainData() {
        // 若sid失效，请前往http://test.ebuycambodia.com/user/login重新获取
        repo.getMainData("377079aa6e2739bd7ceb6422f81cb2831")
            .startWith(Result.loading())
            .autoDisposable(this)
            .subscribe(
                {
                    when(it) { // 通过统一封装的Result类判断是否成功或是失败
                        is Result.Success -> { // 如果是成功，数据将在data中
                            when(it.data) { // 根据data的类型将数据post到UI界面
                                is VersionData -> mViewStateSubject.postValue(
                                    MainViewState(isLoading = false, versionData = it.data)
                                )
                                is List<*> -> {
                                        mViewStateSubject.postValue(
                                            MainViewState(
                                                isLoading = false,
                                                addressList = it.data as List<AddressItem>
                                            )
                                        )
                                }
                            }
                        }
                        // 失败的话错误就在error中
                        is Result.Failure -> mViewStateSubject.postValue(MainViewState(isLoading = false, throwable = it.error))
                        // 如果是Loading类型就设置isLoading为true
                        is Result.Loading -> mViewStateSubject.postValue(MainViewState(isLoading = true))
                    }
                },
                {
                    // 理论上不会进入到这个方法，因为我们处理了在错误时固定返回Result.Failure
                },
                {
                    // 所有请求全部成功完成
                    mViewStateSubject.postValue(MainViewState(isLoading = false))
                }
            )
    }

    fun getMusicData() {
        repo.getMusicData()
            .startWith(Result.loading())
            .autoDisposable(this)
            .subscribe {
                when(it) {
                    is Result.Success -> {
                        mViewStateSubject.postValue(MainViewState(isLoading = false, musicUrl = it.data.string()))
                    }
                    is Result.Failure -> {
                        mViewStateSubject.postValue(MainViewState(isLoading = false, throwable = it.error))
                    }
                    is Result.Loading -> mViewStateSubject.postValue(MainViewState(isLoading = true))
                }
            }
    }

    fun getHospitalBanner() {
        repo.getHospitalBanner("00P007001")
            .startWith(Result.loading())
            .autoDisposable(this)
            .subscribe {
                when(it) {
                    is Result.Success -> {
                        mViewStateSubject.postValue(MainViewState(isLoading = false, banners = it.data))
                    }
                    is Result.Failure -> {
                        mViewStateSubject.postValue(MainViewState(isLoading = false, throwable = it.error))
                    }
                    is Result.Loading -> {
                        mViewStateSubject.postValue(MainViewState(isLoading = true))
                    }
                }
            }
    }
}

class MainViewModelFactory(private val repo: MainRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}