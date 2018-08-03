package com.xia.baseproject.rxhttp.subscriber;

import android.content.Context;

import com.xia.baseproject.rxhttp.callback.Callback;
import com.xia.baseproject.rxhttp.utils.Platform;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/3.
 */
@SuppressWarnings("WeakerAccess")
public class CallbackSubscriber extends ProgressSubscriber<ResponseBody> {
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

    @SuppressWarnings("unchecked")
    @Override
    public void onNext(ResponseBody responseBody) {
        super.onNext(responseBody);
        try {
            if (getCallback() != null) {
                final Object result = getCallback().parseNetworkResponse(responseBody);
                Platform.post(() -> getCallback().onResponse(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            responseBody.close();
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
