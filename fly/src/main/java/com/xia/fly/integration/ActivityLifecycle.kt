package com.xia.fly.integration

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.xia.fly.base.delegate.ActivityDelegate
import com.xia.fly.base.delegate.ActivityDelegateImpl
import com.xia.fly.integration.cache.Cache
import com.xia.fly.integration.cache.IntelligentCache
import com.xia.fly.ui.activities.IActivity
import com.xia.fly.utils.Preconditions
import dagger.Lazy
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [Application.ActivityLifecycleCallbacks] 默认实现类
 * 通过 [ActivityDelegate] 管理 [Activity]
 *
 * @author xia
 * @date 2018/9/20.
 */
@Suppress("UNCHECKED_CAST")
@Singleton
class ActivityLifecycle @Inject
constructor() : Application.ActivityLifecycleCallbacks {

    @JvmField
    @Inject
    internal var mApplication: Application? = null
    @JvmField
    @Inject
    internal var mExtras: Cache<String, Any>? = null
    @JvmField
    @Inject
    internal var mFragmentLifecycle: Lazy<FragmentManager.FragmentLifecycleCallbacks>? = null
    @JvmField
    @Inject
    internal var mFragmentLifecycles: Lazy<MutableList<FragmentManager.FragmentLifecycleCallbacks>>? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        //配置ActivityDelegate
        if (activity is IActivity) {
            var activityDelegate = fetchActivityDelegate(activity)
            if (activityDelegate == null) {
                val cache = getCacheFromActivity(activity as IActivity)
                activityDelegate = ActivityDelegateImpl(activity)
                //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
                //否则存储在 LRU 算法的存储空间中, 前提是 Activity 使用的是 IntelligentCache (框架默认使用)
                cache.put(IntelligentCache.getKeyOfKeep(ActivityDelegate.ACTIVITY_DELEGATE), activityDelegate)
            }
            activityDelegate.onCreate(savedInstanceState)
        }

        registerFragmentCallbacks(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        val activityDelegate = fetchActivityDelegate(activity)
        activityDelegate?.onStart()
    }

    override fun onActivityResumed(activity: Activity) {
        val activityDelegate = fetchActivityDelegate(activity)
        activityDelegate?.onResume()
    }

    override fun onActivityPaused(activity: Activity) {
        val activityDelegate = fetchActivityDelegate(activity)
        activityDelegate?.onPause()
    }

    override fun onActivityStopped(activity: Activity) {
        val activityDelegate = fetchActivityDelegate(activity)
        activityDelegate?.onStop()
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        val activityDelegate = fetchActivityDelegate(activity)
        activityDelegate?.onSaveInstanceState(outState)
    }

    override fun onActivityDestroyed(activity: Activity) {
        val activityDelegate = fetchActivityDelegate(activity)
        if (activityDelegate != null) {
            activityDelegate.onDestroy()
            getCacheFromActivity(activity as IActivity).clear()
        }
    }

    /**
     * 给每个 Activity 的所有 Fragment 设置监听其生命周期,
     *
     * @param activity
     */
    private fun registerFragmentCallbacks(activity: Activity) {
        if (activity is FragmentActivity) {

            //mFragmentLifecycle 为 Fragment 生命周期实现类, 用于框架内部对每个 Fragment 的必要操作, 如给每个 Fragment 配置 FragmentDelegate
            //注册框架内部已实现的 Fragment 生命周期逻辑
            if (mFragmentLifecycle != null) {
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(mFragmentLifecycle!!.get(), true)
            }

            if (mExtras?.containsKey(IntelligentCache.getKeyOfKeep(ConfigModule::class.java.name)) == true) {
                val modules = mExtras!![IntelligentCache.getKeyOfKeep(ConfigModule::class.java.name)] as List<ConfigModule>?
                if (modules?.isNotEmpty() == true && mApplication != null && mFragmentLifecycles != null) {
                    for (module in modules) {
                        module.injectFragmentLifecycle(mApplication!!, mFragmentLifecycles!!.get())
                    }
                }
                mExtras!!.remove(IntelligentCache.getKeyOfKeep(ConfigModule::class.java.name))
            }

            //注册框架外部, 开发者扩展的 Fragment 生命周期逻辑
            if (mFragmentLifecycles != null) {
                for (fragmentLifecycle in mFragmentLifecycles!!.get()) {
                    activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycle, true)
                }
            }
        }
    }

    private fun fetchActivityDelegate(activity: Activity): ActivityDelegate? {
        var activityDelegate: ActivityDelegate? = null
        if (activity is IActivity) {
            val cache = getCacheFromActivity(activity as IActivity)
            activityDelegate = cache[IntelligentCache.getKeyOfKeep(ActivityDelegate.ACTIVITY_DELEGATE)] as ActivityDelegate?
        }
        return activityDelegate
    }

    private fun getCacheFromActivity(activity: IActivity): Cache<String, Any> {
        val cache = activity.provideCache()
        Preconditions.checkNotNull<Any>(cache, "%s cannot be null on Activity", Cache::class.java.name)
        return cache
    }
}
