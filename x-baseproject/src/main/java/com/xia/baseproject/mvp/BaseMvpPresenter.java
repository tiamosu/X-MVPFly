package com.xia.baseproject.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.xia.baseproject.mvp.nullobject.MvpNullObjectBasePresenter;
import com.xia.baseproject.ui.fragments.SupportFragment;

/**
 * @author xia
 * @date 2018/7/19.
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseMvpPresenter<V extends BaseMvpView> extends MvpNullObjectBasePresenter<V> {
    protected SupportFragment mSupportFragment;
    protected AppCompatActivity mAppCompatActivity;

    @CallSuper
    @MainThread
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        if (owner instanceof SupportFragment) {
            mSupportFragment = (SupportFragment) owner;
        }
        if (owner instanceof AppCompatActivity) {
            mAppCompatActivity = (AppCompatActivity) owner;
        }
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
