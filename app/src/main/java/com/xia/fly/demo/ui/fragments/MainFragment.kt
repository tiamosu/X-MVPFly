package com.xia.fly.demo.ui.fragments

import android.view.View
import com.xia.fly.demo.R
import com.xia.fly.module.common.base.BaseFragment
import com.xia.fly.module.common.router.Router
import com.xia.fly.mvp.BaseMvpPresenter
import com.xia.fly.utils.FragmentUtils
import me.yokeyword.fragmentation.ISupportFragment

/**
 * @author xia
 * @date 2018/7/3.
 */
class MainFragment : BaseFragment<BaseMvpPresenter<*>>() {

    private val mFragments = arrayOfNulls<ISupportFragment>(1)

    override fun isCheckNetWork(): Boolean {
        return false
    }

    override fun isLoadTitleBar(): Boolean {
        return true
    }

    override fun newP(): BaseMvpPresenter<*>? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initData() {
        val fragmentA = Router.obtainFragmentACls()
        val firstFragment = findChildFragment(fragmentA)
        if (firstFragment == null) {
            mFragments[0] = FragmentUtils.newInstance(fragmentA)
            loadMultipleRootFragment(R.id.main_fl, 0, mFragments)
        } else {
            mFragments[0] = firstFragment
        }
    }

    override fun initView() {}

    override fun initEvent() {
        applyClickListener(
                rootView?.findViewById(R.id.main_login_btn),
                rootView?.findViewById(R.id.main_open_btn)
        )
    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.main_login_btn -> {
            }
            R.id.main_open_btn -> {
            }
        }
    }
}
