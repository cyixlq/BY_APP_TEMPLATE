package top.cyixlq.common.base.activity

import android.os.Bundle

abstract class BaseActivity : AutoDisposeActivity() {

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }
}