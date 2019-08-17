package top.cyixlq.byapptemplate.test

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_test.*
import top.cyixlq.byapptemplate.R
import top.cyixlq.byapptemplate.fragment.TestFragment
import top.cyixlq.common.base.activity.BaseActivity
import top.cyixlq.common.utils.toastShort

class TestActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this, TestViewModelFactory(TestDataSourceRepository())).get(TestViewModel::class.java)
    }

    override val layoutId: Int = R.layout.activity_test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val msg = intent.getStringExtra("msg")
        msg.toastShort()
        val testFragment = TestFragment.instance("test") // 创建Fragment时必须调用要创建的Fragment对象的instance静态方法
        tvAddFragment.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.flContent, testFragment).commit()
        }
        binds()
    }

    private fun binds() { }

    companion object {
        // 所有Activity必须须统一对外暴露launch静态方法，以规范页面间的传值
        fun launch(activity: FragmentActivity, msg: String) {
            val intent = Intent(activity, TestActivity::class.java)
            intent.putExtra("msg", msg)
            activity.startActivity(intent)
        }
    }
}
