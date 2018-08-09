package com.xia.baseproject.rxhttp.subscriber;

import android.app.Dialog;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.CloseUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.xia.baseproject.app.Rest;
import com.xia.baseproject.rxhttp.callback.AbstractFileCallback;
import com.xia.baseproject.rxhttp.callback.Callback;
import com.xia.baseproject.rxhttp.exception.ApiException;
import com.xia.baseproject.ui.dialog.LoadingDialog;
import com.xia.baseproject.ui.loder.Loader;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/8/3.
 */
@SuppressWarnings("WeakerAccess")
public class CallbackSubscriber implements Observer<ResponseBody> {
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
    public void onSubscribe(Disposable d) {
        if (!NetworkUtils.isAvailableByPing()) {
            onError("无法连接网络");
            return;
        }
        showDialog();
        if (mCallback != null) {
            mCallback.onSubscribe(d);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onNext(ResponseBody responseBody) {
        if (mCallback != null) {
            final boolean isFileCallback = mCallback instanceof AbstractFileCallback;
            try {
                final Object result = mCallback.parseNetworkResponse(responseBody);
                if (!isFileCallback) {
                    mCallback.onResponse(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (!isFileCallback) {
                    CloseUtils.closeIO(responseBody);
                }
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        cancelDialog();
        final ApiException apiException = ApiException.handleException(e);
        final String error = apiException.getMessage();
        onError(error);
    }

    @Override
    public void onComplete() {
        cancelDialog();
        if (mCallback != null) {
            mCallback.onComplete();
            mCallback = null;
        }
    }

    private void onError(String error) {
        if (mCallback != null) {
            mCallback.onError(error);
            mCallback = null;
        }
    }

    private void showDialog() {
        if (isShowLoadingDialog()) {
            Rest.getHandler().postDelayed(() -> Loader.showLoading(getLoadingDialog()), 300);
        }
    }

    private void cancelDialog() {
        if (isShowLoadingDialog()) {
            Rest.getHandler().removeCallbacksAndMessages(null);
            Loader.stopLoading();
        }
    }
}
