package top.cyixlq.byapptemplate.fragment

import top.cyixlq.byapptemplate.bean.VersionData

data class TestViewState(
    val isLoading: Boolean = false,
    val throwable: Throwable? = null,
    val versionData: VersionData? = null
) {
    companion object {
        fun create(
            isLoading: Boolean = false,
            throwable: Throwable? = null,
            versionData: VersionData? = null
        ): TestViewState =
            TestViewState(isLoading, throwable, versionData)
    }
}