package com.xia.fly.mvp

import android.content.Context
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.xia.fly.mvp.nullobject.MvpNullObjectBasePresenter
import com.xia.fly.utils.FlyUtils

/**
 * @author xia
 * @date 2018/7/19.
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseMvpPresenter<V : BaseMvpView<*>> : MvpNullObjectBasePresenter<V>() {
    protected lateinit var mLifecycleOwner: LifecycleOwner
    protected var mContext: Context? = null

    @CallSuper
    @MainThread
    override fun onCreate(owner: LifecycleOwner) {
        mLifecycleOwner = owner
        mContext = FlyUtils.getContext(owner)
    }

    @CallSuper
    @MainThread
    override fun onStart(owner: LifecycleOwner) {
    }

    @CallSuper
    @MainThread
    override fun onResume(owner: LifecycleOwner) {
    }

    @CallSuper
    @MainThread
    override fun onPause(owner: LifecycleOwner) {
    }

    @CallSuper
    @MainThread
    override fun onStop(owner: LifecycleOwner) {
    }

    @CallSuper
    @MainThread
    override fun onDestroy(owner: LifecycleOwner) {
    }

    @CallSuper
    @MainThread
    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
    }
}
