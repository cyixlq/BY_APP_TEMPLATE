package top.cyixlq.byapptemplate.fragment

import top.cyixlq.byapptemplate.bean.VersionData

data class TestViewState(
    val isLoading: Boolean = false,
    val throwable: Throwable? = null,
    val versionData: VersionData? = null
)