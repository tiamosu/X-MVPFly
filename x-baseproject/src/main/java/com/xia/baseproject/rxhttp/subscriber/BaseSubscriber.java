package com.xia.baseproject.rxhttp.subscriber;

import android.support.annotation.CallSuper;

import com.blankj.utilcode.util.NetworkUtils;
import com.xia.baseproject.rxhttp.exception.ApiException;
import com.xia.baseproject.rxhttp.utils.Platform;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author xia
 * @date 2018/7/27.
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseSubscriber<T> implements Observer<T>, ISubscriber<T> {

    @CallSuper
    @Override
    public void onSubscribe(Disposable d) {
        if (!NetworkUtils.isAvailableByPing()) {
            Platform.post(() -> doOnError("无法连接网络"));
            return;
        }
        Platform.post(() -> doOnSubscribe(d));
    }

    @CallSuper
    @Override
    public void onNext(T t) {
        Platform.post(() -> doOnNext(t));
    }

    @CallSuper
    @Override
    public void onError(Throwable e) {
        final ApiException apiException = ApiException.handleException(e);
        final String error = apiException.getMessage();
        Platform.post(() -> doOnError(error));
    }

    @CallSuper
    @Override
    public void onComplete() {
        Platform.post(this::doonComplete);
    }
}
