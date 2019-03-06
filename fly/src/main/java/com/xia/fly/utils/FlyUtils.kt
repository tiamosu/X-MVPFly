package com.xia.fly.utils

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.xia.fly.base.App
import com.xia.fly.di.component.AppComponent

/**
 * @author xia
 * @date 2018/9/14.
 */
class FlyUtils private constructor() {

    init {
        throw IllegalStateException("u can't instantiate me!")
    }

    companion object {

        @JvmStatic
        val appComponent: AppComponent
            get() {
                Preconditions.checkNotNull<Application>(Utils.getApp(), "%s == null", Utils.getApp().javaClass.name)
                Preconditions.checkState(Utils.getApp() is App, "%s must be implements %s",
                        Utils.getApp().javaClass.name, App::class.java.name)
                return (Utils.getApp() as App).appComponent
            }
    }
}
