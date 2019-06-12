package com.xia.fly.module.b.mvp.view

import com.xia.fly.module.b.mvp.presenter.BPresenter
import com.xia.fly.mvp.BaseMvpView

/**
 * @author weixia
 * @date 2019/3/15.
 */
interface BView : BaseMvpView<BPresenter> {

    fun setContent(content: String?)

    val boolean: Boolean
}
