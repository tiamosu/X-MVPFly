package com.xia.fly.http.callback;

import android.content.Context;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
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
        Context context = null;
        if (lifecycleOwner instanceof AppCompatActivity) {
            context = ((AppCompatActivity) lifecycleOwner);
        } else if (lifecycleOwner instanceof Fragment) {
            context = ((Fragment) lifecycleOwner).getContext();
        }
        mContext = context != null ? new WeakReference<>(context) : null;
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
