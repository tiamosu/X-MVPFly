package com.xia.fly.standalone.a

import com.xia.fly.module.a.ui.AFragment
import com.xia.fly.mvp.BaseMvpPresenter
import com.xia.fly.mvp.BaseMvpView
import com.xia.fly.ui.activities.ProxyActivity
import com.xia.fly.ui.fragments.FlySupportFragment

/**
 * @author weixia
 */
class MainActivity<P : BaseMvpPresenter<BaseMvpView<P>>> : ProxyActivity<P>() {

    override fun setRootFragment(): Class<out FlySupportFragment<*>> {
        return AFragment::class.java
    }
}
