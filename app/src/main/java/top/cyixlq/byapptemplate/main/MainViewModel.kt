package top.cyixlq.byapptemplate.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uber.autodispose.autoDisposable
import top.cyixlq.byapptemplate.bean.AddressItem
import top.cyixlq.byapptemplate.bean.VersionData
import top.cyixlq.core.base.viewmodel.BaseViewModel
import top.cyixlq.byapptemplate.bean.Result

class MainViewModel(private val repo: MainRepository): BaseViewModel() {

    val mViewStateSubject = MutableLiveData<MainViewState>()

    // 获取MainActivity页面所需要的全部数据
    @Suppress("UNCHECKED_CAST")
    fun getMainData() {
        // 若sid失效，请前往http://test.ebuycambodia.com/user/login重新获取
        repo.getMainData("751652ace5577283f99edfcc6f42c1631")
            .startWith(Result.loading())
            .autoDisposable(this)
            .subscribe(
                {
                    when(it) { // 通过统一封装的Result类判断是否成功或是失败
                        is Result.Success -> { // 如果是成功，数据将在data中
                            when(it.data) { // 根据data的类型将数据post到UI界面
                                is VersionData -> mViewStateSubject.postValue(
                                    MainViewState.create(isLoading = true, versionData = it.data)
                                )
                                is List<*> -> {
                                    if (it.data.isNotEmpty() && it.data[0] is AddressItem) { // 为了安全性和稳定性，严格校验
                                        mViewStateSubject.postValue(
                                            MainViewState.create(
                                                isLoading = true,
                                                addressList = it.data as List<AddressItem>
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        // 失败的话错误就在error中
                        is Result.Failure -> mViewStateSubject.postValue(MainViewState.create(throwable = it.error))
                        // 如果是Loading类型就设置isLoading为true
                        is Result.Loading -> mViewStateSubject.postValue(MainViewState.create(isLoading = true))
                    }
                },
                {
                    // 理论上不会进入到这个方法，因为我们处理了在错误时固定返回Result.Failure
                },
                {
                    // 所有请求全部成功完成
                    mViewStateSubject.postValue(MainViewState.create(isLoading = false))
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
                        mViewStateSubject.postValue(MainViewState.create(isLoading = false, musicUrl = it.data.string()))
                    }
                    is Result.Failure -> {
                        mViewStateSubject.postValue(MainViewState.create(isLoading = false, throwable = it.error))
                    }
                    is Result.Loading -> mViewStateSubject.postValue(MainViewState.create(isLoading = true))
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