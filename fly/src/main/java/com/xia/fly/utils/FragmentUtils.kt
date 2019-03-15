package com.xia.fly.utils

import android.os.Bundle

import androidx.fragment.app.Fragment
import me.yokeyword.fragmentation.ISupportFragment

/**
 * @author xia
 * @date 2018/7/10.
 */
@Suppress("UNCHECKED_CAST")
object FragmentUtils {

    @JvmStatic
    fun <T : ISupportFragment> newInstance(cls: Class<out ISupportFragment>): T? {
        return newInstance(cls, null)
    }

    @JvmStatic
    fun <T : ISupportFragment> newInstance(cls: Class<out ISupportFragment>, bundle: Bundle?): T? {
        try {
            val t = cls.newInstance() as? T
            if (t != null) {
                return newInstance(t, bundle)
            }
        } catch (ignored: IllegalAccessException) {
        } catch (ignored: InstantiationException) {
        }
        return null
    }

    @JvmStatic
    fun <T : ISupportFragment> newInstance(fragment: ISupportFragment, bundle: Bundle?): T {
        if (bundle != null && !bundle.isEmpty) {
            (fragment as Fragment).arguments = bundle
            fragment.putNewBundle(bundle)
        }
        return fragment as T
    }
}
