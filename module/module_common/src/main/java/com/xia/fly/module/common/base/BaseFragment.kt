package com.xia.fly.module.common.base

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IdRes
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ClickUtils
import com.xia.fly.module.common.R

import com.xia.fly.mvp.BaseMvpPresenter
import com.xia.fly.ui.fragments.FlySupportFragment

/**
 * @author xia
 * @date 2018/7/3.
 */
abstract class BaseFragment<P : BaseMvpPresenter<*>> : FlySupportFragment<P>() {

    protected abstract fun onWidgetClick(view: View)

    override fun onCreateTitleBar(titleBarContainer: FrameLayout) {
        if (isLoadTitleBar()) {
            val view = View.inflate(context, R.layout.layout_head_view, null)
            titleBarContainer.addView(view)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
    }

    private val mOnClickListener = View.OnClickListener { view -> onWidgetClick(view) }

    protected fun applyWidgetClickListener(vararg views: View?) {
        ClickUtils.applySingleDebouncing(views, mOnClickListener)
    }

    fun <T : View> getView(layoutView: View? = rootView, @IdRes viewId: Int): T? {
        return layoutView?.findViewById(viewId) as? T
    }
}
