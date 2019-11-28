package top.cyixlq.byapptemplate.test

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_test.*
import top.cyixlq.byapptemplate.R
import top.cyixlq.byapptemplate.fragment.TestFragment
import top.cyixlq.core.base.activity.BaseActivity
import top.cyixlq.core.utils.toastLong
import top.cyixlq.core.utils.toastShort

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
        btnGetHotKey.setOnClickListener {
            mViewModel.getHotKeys(5)
        }
        binds()
    }

    private fun binds() {
        mViewModel.mViewState.observe(this, Observer {
            progressBar.visibility = if (it.isLoading) View.VISIBLE else View.INVISIBLE
            if (it.throwable != null) {
                it.throwable.localizedMessage.toastLong()
            }
            if (it.hotKeys != null) {
                val stringBuilder = StringBuilder()
                for (hotKey in it.hotKeys) {
                    stringBuilder.append(hotKey.word).append(",")
                }
                tvHotKeys.text = stringBuilder.toString()
            }
        })
    }

    companion object {
        // 所有Activity必须须统一对外暴露launch静态方法，以规范页面间的传值
        fun launch(activity: FragmentActivity, msg: String) {
            val intent = Intent(activity, TestActivity::class.java)
            intent.putExtra("msg", msg)
            activity.startActivity(intent)
        }
    }
}
