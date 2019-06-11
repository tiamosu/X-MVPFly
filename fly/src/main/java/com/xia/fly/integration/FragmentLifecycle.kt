package com.xia.fly.integration

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.xia.fly.base.delegate.FragmentDelegate
import com.xia.fly.base.delegate.FragmentDelegateImpl
import com.xia.fly.integration.cache.Cache
import com.xia.fly.integration.cache.IntelligentCache
import com.xia.fly.ui.fragments.IFragment
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [FragmentManager.FragmentLifecycleCallbacks] 默认实现类
 * 通过 [FragmentDelegate] 管理 [Fragment]
 *
 * @author xia
 * @date 2018/9/20.
 */
@Singleton
class FragmentLifecycle @Inject constructor() : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        if (f is IFragment) {
            var fragmentDelegate = fetchFragmentDelegate(f)
            if (fragmentDelegate == null || !fragmentDelegate.isAdded()) {
                fragmentDelegate = FragmentDelegateImpl(fm, f)
                //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
                //否则存储在 LRU 算法的存储空间中, 前提是 Fragment 使用的是 IntelligentCache (框架默认使用)
                val cache = getCacheFromFragment(f)
                cache?.put(IntelligentCache.getKeyOfKeep(FragmentDelegate.FRAGMENT_DELEGATE), fragmentDelegate)
            }
            fragmentDelegate.onAttach(context)
        }
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onCreate(savedInstanceState)
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onCreateView(v, savedInstanceState)
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onActivityCreate(savedInstanceState)
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onStart()
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onResume()
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onPause()
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onStop()
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onSaveInstanceState(outState)
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onDestroyView()
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onDestroy()
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        val fragmentDelegate = fetchFragmentDelegate(f)
        fragmentDelegate?.onDetach()
    }

    private fun fetchFragmentDelegate(fragment: Fragment): FragmentDelegate? {
        val cache = getCacheFromFragment(fragment as? IFragment)
        return cache?.get(IntelligentCache.getKeyOfKeep(FragmentDelegate.FRAGMENT_DELEGATE)) as? FragmentDelegate
    }

    private fun getCacheFromFragment(fragment: IFragment?): Cache<String, Any>? {
        return fragment?.provideCache()
    }
}