package top.cyixlq.byapptemplate.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import top.cyixlq.byapptemplate.R
import top.cyixlq.byapptemplate.test.TestActivity
import top.cyixlq.common.base.activity.BaseActivity
import top.cyixlq.common.utils.toastShort

class MainActivity : BaseActivity() {

    override val layoutId: Int = R.layout.activity_main

    private val mViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, MainViewModelFactory(MainRepository())).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnGetData.setOnClickListener { mViewModel.getMainData() }
        btnGoToTest.setOnClickListener {
            // 启动每个Activity必须调用其launch静态方法，以此保证页面间传值不会混乱
            // 坚决不能自己创建一个Intent进行跳转
            TestActivity.launch(this, "来自MainActivity的消息")
        }
        binds()
    }

    private fun binds() {
        // 在此处处理数据绑定
        mViewModel.mViewStateSubject.observe(this, Observer {
            progressBar.visibility = if (it.isLoading) View.VISIBLE else View.INVISIBLE
            if (it.addressList != null) {
                val stringBuilder = StringBuilder()
                for (item in it.addressList) {
                    stringBuilder.append("${item.contact}\n")
                }
                tvAddressList.text = stringBuilder.toString()
            }
            if (it.versionData != null) {
                tvVersionData.text = it.versionData.desc
            }
            if (it.throwable != null) {
                it.throwable.localizedMessage.toastShort()
            }
        })
    }
}
