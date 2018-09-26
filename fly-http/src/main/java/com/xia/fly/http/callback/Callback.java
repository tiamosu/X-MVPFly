package com.xia.fly.http.callback;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
@SuppressWarnings("WeakerAccess")
public abstract class Callback<T> {
    private WeakReference<Context> mContext;
    public final LifecycleOwner mLifecycleOwner;

    public Context getContext() {
        return mContext != null ? mContext.get() : null;
    }

    public Callback(@NonNull LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
        if (lifecycleOwner instanceof AppCompatActivity) {
            mContext = new WeakReference<>((AppCompatActivity) lifecycleOwner);
        } else if (lifecycleOwner instanceof Fragment) {
            mContext = new WeakReference<>(((Fragment) lifecycleOwner).getContext());
        }
    }

    public void onSubscribe(Disposable d) {
    }

    public void onError(Throwable e) {
    }

    public void onComplete() {
    }

    public void inProgress(float progress, long total) {
    }

    public abstract void parseNetworkResponse(ResponseBody responseBody) throws Exception;

    public abstract void onResponse(T response);
}
