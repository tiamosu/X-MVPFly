package com.xia.fly.module.a.mvp.view

import com.xia.fly.module.a.mvp.presenter.APresenter
import com.xia.fly.mvp.BaseMvpView

/**
 * @author weixia
 * @date 2019/3/15.
 */
interface AView : BaseMvpView<APresenter> {

    fun setData(content: String)

    val boolean: Boolean

    val num: Int
}
