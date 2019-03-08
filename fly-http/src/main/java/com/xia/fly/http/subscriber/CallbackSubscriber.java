package com.xia.fly.http.subscriber;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.xia.fly.http.callback.Callback;
import com.xia.fly.ui.dialog.LoadingDialog;
import com.xia.fly.ui.dialog.loader.Loader;
import com.xia.fly.utils.FlyUtils;
import com.xia.fly.utils.Platform;

import androidx.annotation.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
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

    private static final Handler LOADING_HANDLER = new Handler(Looper.getMainLooper());

    public CallbackSubscriber(@NonNull Callback callback) {
        super(FlyUtils.getAppComponent().rxErrorHandler());
        mCallback = callback;
    }

    protected boolean isShowLoadingDialog() {
        return true;
    }

    protected Dialog getLoadingDialog() {
        Context context;
        if (mCallback == null || (context = mCallback.getContext()) == null) {
            return null;
        }
        return new LoadingDialog(context);
    }

    @Override
    public void onSubscribe(final Disposable d) {
        mDisposable = d;
        if (!isDisposed()) {
            Platform.post(new Action() {
                @Override
                public void run() {
                    showDialog();
                    if (mCallback != null) {
                        mCallback.onSubscribe(d);
                    }
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
    public void onError(final Throwable e) {
        super.onError(e);
        Platform.post(new Action() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onError(e);
                    mCallback = null;
                }
                cancelDialog();
            }
        });
    }

    @Override
    public void onComplete() {
        Platform.post(new Action() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onComplete();
                    mCallback = null;
                }
                cancelDialog();
            }
        });
    }

    protected void showDialog() {
        if (isShowLoadingDialog() && isPageVisible()) {
            LOADING_HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Loader.showLoading(getLoadingDialog());
                }
            }, 300);
        }
    }

    protected void cancelDialog() {
        if (isShowLoadingDialog()) {
            LOADING_HANDLER.removeCallbacksAndMessages(null);
            Loader.stopLoading();
        }
        if (!isDisposed()) {
            dispose();
        }
    }

    private boolean isPageVisible() {
        if (mCallback != null) {
            return FlyUtils.isCurrentVisible(mCallback.getLifecycleOwner());
        }
        return false;
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
