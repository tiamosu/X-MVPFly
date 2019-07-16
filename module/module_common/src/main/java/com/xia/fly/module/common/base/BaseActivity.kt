package com.xia.fly.module.common.base

import android.view.View
import com.blankj.utilcode.util.ClickUtils
import com.xia.fly.mvp.BaseMvpPresenter
import com.xia.fly.ui.activities.FlySupportActivity

/**
 * @author weixia
 * @date 2019/7/16.
 */
abstract class BaseActivity<P : BaseMvpPresenter<*>> : FlySupportActivity<P>() {

    private val mOnClickListener = View.OnClickListener { view -> onWidgetClick(view) }

    protected abstract fun onWidgetClick(view: View)

    protected fun applyWidgetClickListener(vararg views: View?) {
        ClickUtils.applySingleDebouncing(views, mOnClickListener)
    }
}
