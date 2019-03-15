package com.xia.fly.module.common.base

import android.view.View
import android.widget.FrameLayout
import com.xia.fly.module.common.R

import com.xia.fly.mvp.BaseMvpPresenter
import com.xia.fly.ui.fragments.SupportFragment

/**
 * @author xia
 * @date 2018/7/3.
 */
abstract class BaseFragment<P : BaseMvpPresenter<*>> : SupportFragment<P>() {

    protected open fun isLoadTitleBar(): Boolean {
        return false
    }

    override fun onCreateTitleBar(titleBarContainer: FrameLayout) {
        if (isLoadTitleBar()) {
            val view = View.inflate(context, R.layout.layout_head_view, null)
            titleBarContainer.addView(view)
        }
    }
}
