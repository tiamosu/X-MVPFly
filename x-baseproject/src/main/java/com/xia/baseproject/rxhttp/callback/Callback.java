package com.xia.baseproject.rxhttp.callback;

import android.content.Context;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
@SuppressWarnings("WeakerAccess")
public abstract class Callback<T> {
    private Context mContext;

    public Callback(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public void inProgress(float progress, long total) {
    }

    public abstract T parseNetworkResponse(ResponseBody responseBody) throws Exception;

    public abstract void onResponse(T response);

    public void onSubscribe(Disposable d) {
    }

    public void onNext(ResponseBody responseBody) {
    }

    public void onError(String message) {
    }

    public void onComplete() {
    }
}
