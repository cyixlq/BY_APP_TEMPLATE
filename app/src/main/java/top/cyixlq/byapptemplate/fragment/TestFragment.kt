package top.cyixlq.byapptemplate.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_test.*
import top.cyixlq.byapptemplate.R
import top.cyixlq.core.common.fragment.CommonFragment

class TestFragment : CommonFragment() {

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
        button.setOnClickListener { mViewModel.getVersionDataNew() }
        binds()
    }

    private fun binds() {
        mViewModel.mViewState.observe(this, Observer {
            progressBar.visibility = if (it.isLoading) View.VISIBLE else View.INVISIBLE
            if (it.versionData != null)
                tvTitle.text = it.versionData.desc
            if (it.throwable != null)
                tvTitle.text = it.throwable.localizedMessage
        })
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