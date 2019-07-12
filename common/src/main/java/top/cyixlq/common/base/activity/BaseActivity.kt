package top.cyixlq.common.base.activity

import android.os.Bundle

abstract class BaseActivity : AutoDisposeActivity() {

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }

    /**
     * 进行VM与View状态绑定
     */
    abstract fun binds()
}