package com.xia.fly.demo.base

import android.view.View
import android.widget.FrameLayout

import com.xia.fly.demo.R
import com.xia.fly.mvp.BaseMvpPresenter
import com.xia.fly.ui.fragments.SupportFragment

/**
 * @author xia
 * @date 2018/7/3.
 */
abstract class HeadViewFragment<P : BaseMvpPresenter<*>> : SupportFragment<P>() {

    protected open fun isLoadTitleBar(): Boolean {
        return false
    }

    override fun onCreateTitleBar(container: FrameLayout) {
        if (isLoadTitleBar()) {
            val headView = View.inflate(context, R.layout.layout_head_view, null)
            container.addView(headView)
        }
    }
}
