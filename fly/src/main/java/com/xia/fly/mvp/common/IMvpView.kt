package com.xia.fly.mvp.common

/**
 * @author weixia
 * @date 2019/3/19.
 */
interface IMvpView<out P> {

    fun newP(): P?
}
