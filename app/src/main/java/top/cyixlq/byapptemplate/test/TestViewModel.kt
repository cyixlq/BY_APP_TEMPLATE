package top.cyixlq.byapptemplate.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uber.autodispose.autoDisposable
import top.cyixlq.core.base.viewmodel.BaseViewModel

@SuppressWarnings("checkResult")
class TestViewModel(private val repo: TestDataSourceRepository) : BaseViewModel() {
    val mViewState = MutableLiveData<TestViewState>()

    fun getHotKeys(size: Int) {
        repo.getHotKeys(size)
            .doOnSubscribe { mViewState.postValue(TestViewState(isLoading = true)) }
            .autoDisposable(this)
            .subscribe({
                mViewState.postValue(TestViewState(isLoading = false, hotKeys = it))
            }, {
                mViewState.postValue(TestViewState(isLoading = false, throwable = it))
            })
    }
}

@Suppress("UNCHECKED_CAST")
class TestViewModelFactory(private val repo: TestDataSourceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TestViewModel(repo) as T
    }
}