package top.cyixlq.byapptemplate.fragment

class TestDataSourceRepository(
    private val remote: TestRemoteDataSource = TestRemoteDataSource(),
    private val local: TestLocalDataSource = TestLocalDataSource()
)

class TestRemoteDataSource

class TestLocalDataSource