package com.xia.fly.http.subscriber;

import android.app.Activity;
import android.app.Dialog;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.xia.fly.http.callback.Callback;
import com.xia.fly.ui.dialog.LoadingDialog;
import com.xia.fly.ui.dialog.loader.Loader;
import com.xia.fly.ui.fragments.SupportFragment;
import com.xia.fly.utils.FlyUtils;
import com.xia.fly.utils.Platform;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
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
            Platform.getLoadingHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Loader.showLoading(getLoadingDialog());
                }
            }, 300);
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

    private boolean isPageVisible() {
        if (mCallback == null) {
            return false;
        }

        boolean isVisible = false;
        final LifecycleOwner owner = mCallback.mLifecycleOwner;
        if (owner instanceof Activity) {
            final Activity activity = (Activity) owner;
            isVisible = AppUtils.isAppForeground()
                    && ActivityUtils.getTopActivity() == activity;
        } else if (owner instanceof SupportFragment) {
            final SupportFragment fragment = (SupportFragment) owner;
            isVisible = fragment.isSupportVisible();
        } else if (owner instanceof Fragment) {
            final Fragment fragment = (Fragment) owner;
            isVisible = fragment.isVisible();
        }
        return isVisible;
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
