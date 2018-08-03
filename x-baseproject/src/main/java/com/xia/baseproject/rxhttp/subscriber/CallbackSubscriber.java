package com.xia.baseproject.rxhttp.subscriber;

import android.content.Context;

import com.xia.baseproject.rxhttp.callback.Callback;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;

/**
 * @author xia
 * @date 2018/8/3.
 */
@SuppressWarnings("WeakerAccess")
public abstract class CallbackSubscriber<T> extends ProgressSubscriber<T> {
    private WeakReference<Callback> mCallback;

    protected Callback getCallback() {
        return mCallback == null ? null : mCallback.get();
    }

    public CallbackSubscriber(Context context, Callback callback) {
        super(context);
        if (callback != null) {
            mCallback = new WeakReference<>(callback);
        }
    }

    @Override
    public void doOnSubscribe(Disposable disposable) {
        super.doOnSubscribe(disposable);
        if (getCallback() != null) {
            getCallback().onSubscribe(disposable);
        }
    }

    @Override
    public void doOnError(String error) {
        super.doOnError(error);
        if (getCallback() != null) {
            getCallback().onError(error);
        }
    }

    @Override
    public void doonComplete() {
        super.doonComplete();
        if (getCallback() != null) {
            getCallback().onComplete();
        }
    }
}
