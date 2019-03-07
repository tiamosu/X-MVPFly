package com.xia.fly.mvp.common

import android.content.Context

/**
 * @author xia
 * @date 2018/7/19.
 */
interface IMvpView<P> {

    fun getContext(): Context

    fun newP(): P?
}
