package com.xia.fly.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.Utils
import com.xia.fly.base.App
import com.xia.fly.di.component.AppComponent
import com.xia.fly.ui.fragments.SupportFragment

/**
 * @author xia
 * @date 2018/9/14.
 */
object FlyUtils {

    @JvmStatic
    fun getAppComponent(): AppComponent {
        Preconditions.checkNotNull(Utils.getApp(),
                "%s == null", Utils.getApp().javaClass.name)
        Preconditions.checkState(Utils.getApp() is App,
                "%s must be implements %s",
                Utils.getApp().javaClass.name, App::class.java.name)
        return (Utils.getApp() as App).getAppComponent()
    }

    @JvmStatic
    fun isCurrentVisible(lifecycleOwner: LifecycleOwner): Boolean {
        when (lifecycleOwner) {
            is Activity -> {
                val activity = lifecycleOwner as Activity?
                return AppUtils.isAppForeground()
                        && ActivityUtils.getTopActivity() == activity!!
            }
            is SupportFragment<*> -> {
                val fragment = lifecycleOwner as SupportFragment<*>?
                return fragment!!.isSupportVisible
            }
            is Fragment -> {
                val fragment = lifecycleOwner as Fragment?
                return fragment!!.isVisible
            }
            else -> return false
        }
    }
}
