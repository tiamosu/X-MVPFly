package com.xia.fly.mvp;

import android.content.Context;

import com.xia.fly.mvp.nullobject.MvpNullObjectBasePresenter;
import com.xia.fly.utils.FlyUtils;

import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author weixia
 * @date 2019/3/19.
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseMvpPresenter<V extends BaseMvpView> extends MvpNullObjectBasePresenter<V> {
    protected LifecycleOwner mLifecycleOwner;
    protected Context mContext;

    @CallSuper
    @MainThread
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        mLifecycleOwner = owner;
        mContext = FlyUtils.getContext(owner);
    }

    @CallSuper
    @MainThread
    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
    }

    @CallSuper
    @MainThread
    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
    }

    @CallSuper
    @MainThread
    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
    }

    @CallSuper
    @MainThread
    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
    }

    @CallSuper
    @MainThread
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
    }

    @CallSuper
    @MainThread
    @Override
    public void onLifecycleChanged(@NonNull LifecycleOwner owner, @NonNull Lifecycle.Event event) {
    }
}
