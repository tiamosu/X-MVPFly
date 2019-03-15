package com.xia.fly.demo

import com.alibaba.android.arouter.launcher.ARouter
import com.xia.fly.base.BaseApplication

/**
 * @author weixia
 * @date 2018/11/29.
 */
@Suppress("unused")
class MyApp : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}
