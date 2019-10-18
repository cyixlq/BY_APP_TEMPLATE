package top.cyixlq.byapptemplate.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_test.*
import top.cyixlq.core.base.fragment.BaseFragment
import top.cyixlq.byapptemplate.R

class TestFragment : BaseFragment() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this, TestViewModelFactory(TestDataSourceRepository())).get(TestViewModel::class.java)
    }

    override val layoutId: Int = R.layout.fragment_test

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString("title")
        if (title != null) {
            tvTitle.text = title
        }
        binds()
    }

    private fun binds() {
    }

    companion object {
        // 必须对外提供实例化方法
        fun instance(title: String): TestFragment {
            val bundle = Bundle().apply {
                putString("title", title)
            }
            return TestFragment().apply {
                arguments = bundle
            }
        }
    }
}