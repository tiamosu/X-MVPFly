package com.xia.baseproject.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.xia.baseproject.mvp.nullobject.MvpNullObjectBasePresenter;

/**
 * @author xia
 * @date 2018/7/19.
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseMvpPresenter<V extends BaseMvpView> extends MvpNullObjectBasePresenter<V> {
    protected Fragment mFragment;
    protected AppCompatActivity mActivity;

    @CallSuper
    @MainThread
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        if (owner instanceof Fragment) {
            mFragment = (Fragment) owner;
        }
        if (owner instanceof AppCompatActivity) {
            mActivity = (AppCompatActivity) owner;
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
