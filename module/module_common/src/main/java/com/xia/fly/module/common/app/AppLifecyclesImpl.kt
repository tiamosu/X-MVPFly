package com.xia.fly.module.common.app

import android.app.Application
import android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN
import android.content.Context
import android.content.res.Configuration
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.Glide
import com.squareup.leakcanary.LeakCanary
import com.xia.fly.BuildConfig
import com.xia.fly.base.delegate.AppLifecycles
import me.yokeyword.fragmentation.Fragmentation

/**
 * @author xia
 * @date 2018/9/18.
 */
class AppLifecyclesImpl : AppLifecycles {

    override fun attachBaseContext(context: Context) {}

    override fun onCreate(application: Application) {
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application)
        }

        Fragmentation.builder()
                //设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .handleException { }
                .install()

        if (BuildConfig.DEBUG) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application)
    }

    override fun onTerminate(application: Application) {
        ARouter.getInstance().destroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {}

    override fun onLowMemory() {
        Glide.get(Utils.getApp()).clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(Utils.getApp()).clearMemory()
        }
        Glide.get(Utils.getApp()).trimMemory(level)
    }
}
