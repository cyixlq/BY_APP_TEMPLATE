package top.cyixlq.byapptemplate.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import top.cyixlq.common.base.viewmodel.BaseViewModel

@SuppressWarnings("checkResult")
class TestViewModel(private val repo: TestDataSourceRepository) : BaseViewModel()

@Suppress("UNCHECKED_CAST")
class TestViewModelFactory(private val repo: TestDataSourceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TestViewModel(repo) as T
    }
}