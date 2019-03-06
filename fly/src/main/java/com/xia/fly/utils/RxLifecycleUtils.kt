package com.xia.fly.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 * @author xia
 * @date 2018/9/3.
 */
class RxLifecycleUtils private constructor() {

    init {
        throw IllegalStateException("u can't instantiate me!")
    }

    companion object {

        @JvmStatic
        fun <T> bindLifecycle(lifecycleOwner: LifecycleOwner): AutoDisposeConverter<T> {
            return AutoDispose.autoDisposable(
                    AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)
            )
        }
    }
}
