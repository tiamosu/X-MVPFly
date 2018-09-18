package com.xia.fly.http.request;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.xia.fly.http.callback.Callback;
import com.xia.fly.http.subscriber.CallbackSubscriber;
import com.xia.fly.utils.RxLifecycleUtils;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
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
        if (mObservable != null) {
            final Callback callback = subscriber.mCallback;
            LifecycleOwner lifecycleOwner = null;
            if (callback != null) {
                lifecycleOwner = callback.mLifecycleOwner;
            }
            if (lifecycleOwner != null) {
                mObservable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔时间（单位：秒）
                        .retryWhen(new RetryWithDelay(3, 2))
                        .as(RxLifecycleUtils.bindLifecycle(lifecycleOwner))
                        .subscribe(subscriber);
            }
        }
    }
}
