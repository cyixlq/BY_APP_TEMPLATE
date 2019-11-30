package top.cyixlq.byapptemplate.test

import top.cyixlq.byapptemplate.bean.HotKey

data class TestViewState(
    val isLoading: Boolean = false,
    val throwable: Throwable? = null,
    val hotKeys: List<HotKey>? = null
)