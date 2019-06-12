package com.xia.fly.mvp.common

import android.view.View

/**
 * @author weixia
 * @date 2019/3/19.
 */
interface IMvpView<P> : View.OnClickListener {

    fun newP(): P?

    fun onWidgetClick(view: View)
}
