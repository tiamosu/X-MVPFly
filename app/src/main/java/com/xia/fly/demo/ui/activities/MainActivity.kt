package com.xia.fly.demo.ui.activities

import android.graphics.Color
import android.util.Log
import com.xia.fly.demo.R
import com.xia.fly.demo.ui.fragments.MainFragment
import com.xia.fly.mvp.BaseMvpPresenter
import com.xia.fly.mvp.BaseMvpView
import com.xia.fly.ui.activities.ProxyActivity
import com.xia.fly.ui.fragments.FlySupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * @author xia
 */
class MainActivity<P : BaseMvpPresenter<BaseMvpView<P>>> : ProxyActivity<P>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun isRestartRestore(): Boolean {
        return false
    }

    override fun loadProxyRootFragment(proxyContainerId: Int) {
        super.loadProxyRootFragment(R.id.main_container_fl)
    }

    override fun setRootFragment(): Class<out FlySupportFragment<*>> {
        return MainFragment::class.java
    }

    override fun initView() {
        val layout = getContainerLayout()
        layout?.setBackgroundColor(Color.GRAY)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultVerticalAnimator()
    }

    override fun onNetworkState(isAvailable: Boolean) {
        Log.e("xia", "$this    onNetworkState: $isAvailable")
    }

    override fun onNetReConnect() {
        Log.e("xia", "$this    onNetReConnect: ")
    }
}
