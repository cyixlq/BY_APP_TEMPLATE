package top.cyixlq.byapptemplate.test

data class TestViewState(
    val isLoading: Boolean = false
) {
    companion object {
        fun create(
            isLoading: Boolean = false
        ): TestViewState =
            TestViewState(isLoading)
    }
}