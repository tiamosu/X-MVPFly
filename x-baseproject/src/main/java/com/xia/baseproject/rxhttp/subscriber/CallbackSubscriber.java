package com.xia.baseproject.rxhttp.subscriber;

import android.app.Dialog;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.CloseUtils;
import com.xia.baseproject.handler.WeakHandler;
import com.xia.baseproject.rxhttp.callback.Callback;
import com.xia.baseproject.rxhttp.utils.Platform;
import com.xia.baseproject.ui.dialog.LoadingDialog;
import com.xia.baseproject.ui.loder.Loader;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/3.
 */
@SuppressWarnings("WeakerAccess")
public class CallbackSubscriber extends BaseSubscriber<ResponseBody> {
    private WeakReference<Callback> mCallback;
    private WeakHandler mWeakHandler = new WeakHandler();

    public CallbackSubscriber(@NonNull Callback callback) {
        mCallback = new WeakReference<>(callback);
    }

    public Callback getCallback() {
        return mCallback == null || mCallback.get() == null ? null : mCallback.get();
    }

    protected boolean isShowDialog() {
        return true;
    }

    protected Dialog getDialog() {
        if (getCallback() != null && getCallback().getContext() != null) {
            return new LoadingDialog(getCallback().getContext());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onNext(ResponseBody responseBody) {
        super.onNext(responseBody);
        Platform.post(() -> doOnNext(responseBody));
        try {
            if (getCallback() != null) {
                final Object result = getCallback().parseNetworkResponse(responseBody);
                if (result != null) {
                    Platform.post(() -> getCallback().onResponse(result));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(responseBody);
        }
    }

    @CallSuper
    @Override
    public void doOnSubscribe(Disposable disposable) {
        showDialog();
        if (getCallback() != null) {
            getCallback().onSubscribe(disposable);
        }
    }

    @CallSuper
    @Override
    public void doOnNext(ResponseBody responseBody) {
    }

    @CallSuper
    @Override
    public void doOnError(String error) {
        cancelDialog();
        if (getCallback() != null) {
            getCallback().onError(error);
        }
    }

    @CallSuper
    @Override
    public void doonComplete() {
        cancelDialog();
        if (getCallback() != null) {
            getCallback().onComplete();
        }
    }

    private void showDialog() {
        if (isShowDialog()) {
            mWeakHandler.postDelayed(() -> Loader.showLoading(getDialog()), 300);
        }
    }

    private void cancelDialog() {
        if (isShowDialog()) {
            mWeakHandler.removeCallbacksAndMessages(null);
            Loader.stopLoading();
        }
    }
}
