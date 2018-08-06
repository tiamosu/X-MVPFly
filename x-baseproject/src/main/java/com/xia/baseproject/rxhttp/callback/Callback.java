package com.xia.baseproject.rxhttp.callback;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
@SuppressWarnings("WeakerAccess")
public abstract class Callback<T> {
    public Object mObject;

    public Callback(@NonNull Object object) {
        mObject = object;
        final boolean a = object instanceof AppCompatActivity;
        final boolean b = object instanceof Fragment;
        if (!(a || b)) {
            throw new IllegalArgumentException("object must be AppCompatActivity or Fragment...！");
        }
    }

    public void onSubscribe(Disposable d) {
    }

    public void onError(String error) {
    }

    public void onComplete() {
    }

    public void inProgress(float progress, long total) {
    }

    public abstract T parseNetworkResponse(ResponseBody responseBody) throws Exception;

    public abstract void onResponse(T response);
}
