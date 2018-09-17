package com.xia.fly.http.subscriber;

import android.app.Dialog;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.NetworkUtils;
import com.xia.fly.http.callback.Callback;
import com.xia.fly.http.exception.ApiException;
import com.xia.fly.ui.dialog.LoadingDialog;
import com.xia.fly.ui.dialog.loader.Loader;
import com.xia.fly.utils.Platform;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/3.
 */
@SuppressWarnings("WeakerAccess")
public class CallbackSubscriber extends DisposableObserver<ResponseBody> {
    public Callback mCallback;

    public CallbackSubscriber(@NonNull Callback callback) {
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
    protected void onStart() {
        if (!NetworkUtils.isConnected()) {
            onError("无法连接网络");
            dispose();
            return;
        }
        Platform.post(() -> {
            showDialog();
            if (mCallback != null) {
                mCallback.onSubscribe(this);
            }
        });
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
        if (!isDisposed()) {
            final ApiException apiException = ApiException.handleException(e);
            final String error = apiException.getMessage();
            onError(error);
        }
    }

    @Override
    public void onComplete() {
        if (!isDisposed()) {
            Platform.post(() -> {
                cancelDialog();
                if (mCallback != null) {
                    mCallback.onComplete();
                    mCallback = null;
                }
            });
        }
    }

    private void onError(String error) {
        Platform.post(() -> {
            cancelDialog();
            if (mCallback != null) {
                mCallback.onError(error);
                mCallback = null;
            }
        });
    }

    private void showDialog() {
        if (isShowLoadingDialog()) {
            Platform.getHandler().postDelayed(() -> Loader.showLoading(getLoadingDialog()), 400);
        }
    }

    private void cancelDialog() {
        if (isShowLoadingDialog()) {
            Platform.getHandler().removeCallbacksAndMessages(null);
            Loader.stopLoading();
        }
    }
}
