package com.xia.fly.integration

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.xia.fly.base.delegate.AppLifecycles
import com.xia.fly.di.module.GlobalConfigModule

/**
 * [ConfigModule] 可以给框架配置一些参数,需要实现 [ConfigModule] 后,在 AndroidManifest 中声明该实现类
 *
 * @author xia
 * @date 2018/9/14.
 * @see [ConfigModule wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki.2.1)
 */
interface ConfigModule {

    /**
     * 使用 [GlobalConfigModule.Builder] 给框架配置一些配置参数
     *
     * @param context [Context]
     * @param builder [GlobalConfigModule.Builder]
     */
    fun applyOptions(context: Context, builder: GlobalConfigModule.Builder)

    /**
     * 使用 [AppLifecycles] 在 [Application] 的生命周期中注入一些操作
     *
     * @param context    [Context]
     * @param lifecycles [Application] 的生命周期容器, 可向框架中添加多个 [Application] 的生命周期类
     */
    fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycles>)

    /**
     * 使用 [Application.ActivityLifecycleCallbacks] 在 [Activity] 的生命周期中注入一些操作
     *
     * @param context    [Context]
     * @param lifecycles [Activity] 的生命周期容器, 可向框架中添加多个 [Activity] 的生命周期类
     */
    fun injectActivityLifecycle(context: Context, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>)

    /**
     * 使用 [FragmentManager.FragmentLifecycleCallbacks] 在 [Fragment] 的生命周期中注入一些操作
     *
     * @param context    [Context]
     * @param lifecycles [Fragment] 的生命周期容器, 可向框架中添加多个 [Fragment] 的生命周期类
     */
    fun injectFragmentLifecycle(context: Context, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>)
}
