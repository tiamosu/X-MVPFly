package com.xia.fly.http.request;

import com.xia.fly.http.callback.Callback;
import com.xia.fly.http.subscriber.CallbackSubscriber;
import com.xia.fly.utils.RxLifecycleUtils;

import androidx.annotation.NonNull;
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

    private final Observable<ResponseBody> mObservable;

    public RequestCall(Observable<ResponseBody> observable) {
        mObservable = observable;
    }

    public final void request(@NonNull CallbackSubscriber subscriber) {
        Callback callback;
        if (mObservable != null && (callback = subscriber.mCallback) != null) {
            mObservable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔时间（单位：秒）
                    .retryWhen(new RetryWithDelay(3, 2))
                    .as(RxLifecycleUtils.bindLifecycle(callback.mLifecycleOwner))
                    .subscribe(subscriber);
        }
    }
}
