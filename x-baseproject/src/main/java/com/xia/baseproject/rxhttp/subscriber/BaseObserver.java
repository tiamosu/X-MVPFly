package com.xia.baseproject.rxhttp.subscriber;

import android.app.Dialog;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.xia.baseproject.rxhttp.exception.ExceptionHandle;
import com.xia.baseproject.rxhttp.utils.Platform;
import com.xia.baseproject.utils.DialogUtils;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author xia
 * @date 2018/7/27.
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseObserver<T> implements Observer<T>, ISubscriber<T> {

    protected boolean isShowDialog() {
        return true;
    }

    private WeakReference<Dialog> mDialog;

    protected void setDialog(Dialog dialog) {
        this.mDialog = new WeakReference<>(dialog);
    }

    private Dialog getDialog() {
        final boolean isNull = mDialog == null || mDialog.get() == null;
        return isNull ? null : mDialog.get();
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (!NetworkUtils.isConnected()) {
            d.dispose();
            doOnError("无网络连接~！");
            return;
        }
        Platform.post(() -> {
            showDialog();
            doOnSubscribe(d);
        });
    }

    @Override
    public void onNext(T t) {
        Platform.post(() -> doOnNext(t));
    }

    @Override
    public void onError(Throwable e) {
        Platform.post(() -> {
            cancelDialog();

            final ExceptionHandle.ResponseThrowable responseThrowable
                    = ExceptionHandle.handleException(e);
            final String error = responseThrowable.message;
            doOnError(error);
        });
    }

    @Override
    public void onComplete() {
        Platform.post(() -> {
            cancelDialog();
            doOnCompleted();
        });
    }

    @SuppressWarnings("unchecked")
    private void showDialog() {
        if (isShowDialog()) {
            ThreadUtils.executeBySingleWithDelay(mSimpleTask, 500, TimeUnit.MILLISECONDS);
        }
    }

    private void cancelDialog() {
        if (isShowDialog()) {
            ThreadUtils.cancel(mSimpleTask);
            DialogUtils.safeCloseDialog(getDialog());
        }
    }

    private final ThreadUtils.SimpleTask mSimpleTask = new ThreadUtils.SimpleTask() {
        @Nullable
        @Override
        public Object doInBackground() {
            return null;
        }

        @Override
        public void onSuccess(@Nullable Object result) {
            DialogUtils.safeShowDialog(getDialog());
        }
    };
}
