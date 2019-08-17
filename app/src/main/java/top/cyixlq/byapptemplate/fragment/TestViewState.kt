package top.cyixlq.byapptemplate.fragment

data class TestViewState(
    val isLoading: Boolean = false,
    val throwable: Throwable? = null
) {
    companion object {
        fun create(
            isLoading: Boolean = false,
            throwable: Throwable? = null
        ): TestViewState =
            TestViewState(isLoading, throwable)
    }
}