package com.xia.baseproject.rxhttp.subscriber;

import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.CallSuper;

import com.blankj.utilcode.util.NetworkUtils;
import com.xia.baseproject.handler.WeakHandler;
import com.xia.baseproject.ui.dialog.LoadingDialog;
import com.xia.baseproject.ui.loder.Loader;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;

/**
 * @author xia
 * @date 2018/8/3.
 */
@SuppressWarnings("WeakerAccess")
public abstract class ProgressSubscriber<T> extends BaseSubscriber<T> {
    private static final WeakHandler mWeakHandler = new WeakHandler(Looper.getMainLooper());
    private WeakReference<Context> mContext;

    public ProgressSubscriber(Context context) {
        if (context != null) {
            mContext = new WeakReference<>(context);
        }
    }

    protected Context getContext() {
        return mContext == null ? null : mContext.get();
    }

    protected boolean isShowDialog() {
        return true;
    }

    protected Dialog getDialog() {
        return getContext() == null ? null : new LoadingDialog(getContext());
    }

    @CallSuper
    @Override
    public void doOnSubscribe(Disposable disposable) {
        if (!NetworkUtils.isAvailableByPing()) {
            doonComplete();
            return;
        }
        showDialog();
    }

    @CallSuper
    @Override
    public void doOnNext(T t) {
    }

    @CallSuper
    @Override
    public void doOnError(String error) {
        cancelDialog();
    }

    @CallSuper
    @Override
    public void doonComplete() {
        cancelDialog();
    }

    private void showDialog() {
        if (isShowDialog() && getDialog() != null) {
            mWeakHandler.postDelayed(() -> Loader.showLoading(getDialog()), 500);
        }
    }

    private void cancelDialog() {
        if (isShowDialog()) {
            mWeakHandler.removeCallbacksAndMessages(null);
            Loader.stopLoading();
        }
    }
}
