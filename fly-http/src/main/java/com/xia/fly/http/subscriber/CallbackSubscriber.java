package com.xia.fly.http.subscriber;

import android.app.Dialog;
import android.support.annotation.NonNull;

import com.xia.fly.http.callback.Callback;
import com.xia.fly.ui.dialog.LoadingDialog;
import com.xia.fly.ui.dialog.loader.Loader;
import com.xia.fly.utils.FlyUtils;
import com.xia.fly.utils.Platform;

import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/3.
 */
@SuppressWarnings("WeakerAccess")
public class CallbackSubscriber extends ErrorHandleSubscriber<ResponseBody> {
    public Callback mCallback;
    private Disposable mDisposable;

    public CallbackSubscriber(@NonNull Callback callback) {
        super(FlyUtils.getAppComponent().rxErrorHandler());
        mCallback = callback;
    }

    protected boolean isShowLoadingDialog() {
        return true;
    }

    protected Dialog getLoadingDialog() {
        if (mCallback == null || mCallback.getContext() == null) {
            return null;
        }
        return new LoadingDialog(mCallback.getContext());
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        if (!isDisposed()) {
            Platform.post(() -> {
                showDialog();
                if (mCallback != null) {
                    mCallback.onSubscribe(d);
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            if (mCallback != null && !isDisposed()) {
                mCallback.parseNetworkResponse(responseBody);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        Platform.post(() -> {
            cancelDialog();
            if (mCallback != null) {
                mCallback.onError(e);
                mCallback = null;
            }
        });
    }

    @Override
    public void onComplete() {
        Platform.post(() -> {
            cancelDialog();
            if (mCallback != null) {
                mCallback.onComplete();
                mCallback = null;
            }
        });
    }

    protected void showDialog() {
        if (isShowLoadingDialog()) {
            Platform.getLoadingHandler().postDelayed(() ->
                    Loader.showLoading(getLoadingDialog()), 300);
        }
    }

    protected void cancelDialog() {
        if (isShowLoadingDialog()) {
            Platform.getLoadingHandler().removeCallbacksAndMessages(null);
            Loader.stopLoading();
        }
        if (!isDisposed()) {
            dispose();
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean isDisposed() {
        return mDisposable != null && mDisposable.isDisposed();
    }

    protected void dispose() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
