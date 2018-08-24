package com.xia.baseproject.rxhttp.subscriber;

import android.app.Dialog;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.NetworkUtils;
import com.xia.baseproject.app.Rest;
import com.xia.baseproject.rxhttp.AutoDisposable;
import com.xia.baseproject.rxhttp.callback.Callback;
import com.xia.baseproject.rxhttp.exception.ApiException;
import com.xia.baseproject.rxhttp.utils.Platform;
import com.xia.baseproject.ui.dialog.LoadingDialog;
import com.xia.baseproject.ui.dialog.loader.Loader;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/3.
 */
@SuppressWarnings("WeakerAccess")
public class CallbackSubscriber implements Observer<ResponseBody> {
    private Callback mCallback;
    private String mHttpTag;

    public CallbackSubscriber(@NonNull Callback callback) {
        mCallback = callback;
        mHttpTag = callback.mHttpTag;
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
        if (!NetworkUtils.isConnected()) {
            onError("无法连接网络");
            d.dispose();
            return;
        }
        AutoDisposable.getInstance().add(mHttpTag, d);
        Platform.post(() -> {
            showDialog();
            if (mCallback != null) {
                mCallback.onSubscribe(d);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            if (mCallback != null) {
                mCallback.parseNetworkResponse(responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        final ApiException apiException = ApiException.handleException(e);
        final String error = apiException.getMessage();
        onError(error);
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
            Rest.getHandler().postDelayed(() -> Loader.showLoading(getLoadingDialog()), 400);
        }
    }

    private void cancelDialog() {
        if (isShowLoadingDialog()) {
            Rest.getHandler().removeCallbacksAndMessages(null);
            Loader.stopLoading();
        }
    }
}
