package top.cyixlq.byapptemplate.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uber.autodispose.autoDisposable
import top.cyixlq.byapptemplate.bean.Result
import top.cyixlq.core.common.viewmodel.CommonViewModel

@SuppressWarnings("checkResult")
class TestViewModel(private val repo: TestDataSourceRepository) : CommonViewModel() {
    val mViewState = MutableLiveData<TestViewState>()

    fun getVersionDataNew() {
        repo.getVersionDataNew()
            .startWith(Result.loading())
            .autoDisposable(this)
            .subscribe {
                when(it) {
                    is Result.Success -> {
                        mViewState.postValue(TestViewState(isLoading = false, versionData = it.data))
                    }
                    is Result.Failure -> {
                        mViewState.postValue(TestViewState(isLoading = false, throwable = it.error))
                    }
                    is Result.Loading -> {
                        mViewState.postValue(TestViewState(isLoading = true))
                    }
                }
            }
    }
}

@Suppress("UNCHECKED_CAST")
class TestViewModelFactory(private val repo: TestDataSourceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TestViewModel(repo) as T
    }
}