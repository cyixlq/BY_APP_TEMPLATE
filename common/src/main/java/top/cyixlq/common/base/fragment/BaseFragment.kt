package top.cyixlq.common.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : AutoDisposeFragment() {

    private var mRootView: View? = null

    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return mRootView!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRootView = null
    }
}
