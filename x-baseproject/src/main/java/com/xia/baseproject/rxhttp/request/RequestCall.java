package com.xia.baseproject.rxhttp.request;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.xia.baseproject.rxhttp.func.RetryExceptionFunc;
import com.xia.baseproject.rxhttp.subscriber.CallbackSubscriber;
import com.xia.baseproject.rxhttp.utils.RxLifecycleUtils;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
@SuppressWarnings("WeakerAccess")
public class RequestCall {

    private Observable<ResponseBody> mObservable;

    public RequestCall(Observable<ResponseBody> observable) {
        mObservable = observable;
    }

    public final void request(@NonNull CallbackSubscriber subscriber) {
        if (mObservable == null) {
            return;
        }
        final LifecycleOwner lifecycleOwner = subscriber.mCallback.mLifecycleOwner;
        mObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retryWhen(new RetryExceptionFunc())
                .as(RxLifecycleUtils.bindLifecycle(lifecycleOwner))
                .subscribe(subscriber);
    }
}
