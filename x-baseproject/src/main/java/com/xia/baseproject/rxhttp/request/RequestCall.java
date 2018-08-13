package com.xia.baseproject.rxhttp.request;

import android.support.annotation.NonNull;

import com.xia.baseproject.rxhttp.func.RetryExceptionFunc;
import com.xia.baseproject.rxhttp.subscriber.CallbackSubscriber;

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
        if (mObservable != null) {
            mObservable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .retryWhen(new RetryExceptionFunc())
                    .subscribe(subscriber);
        }
    }
}
