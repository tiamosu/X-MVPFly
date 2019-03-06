package com.xia.fly.demo.mvp.view

import com.xia.fly.demo.mvp.presenter.HomePresenter
import com.xia.fly.mvp.BaseMvpView

/**
 * @author xia
 * @date 2018/7/20.
 */
interface HomeView : BaseMvpView<HomePresenter> {

    val boolean: Boolean

    val num: Int

    fun setData(content: String)
}
