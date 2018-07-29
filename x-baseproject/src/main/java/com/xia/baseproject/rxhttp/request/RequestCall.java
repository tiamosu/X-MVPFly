package com.xia.baseproject.rxhttp.request;

import com.xia.baseproject.rxhttp.subscriber.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author xia
 * @date 2018/7/27.
 */
@SuppressWarnings("WeakerAccess")
public class RequestCall {

    private BaseRequest mBaseRequest;

    public RequestCall(BaseRequest request) {
        this.mBaseRequest = request;
    }

    @SuppressWarnings("unchecked")
    public final <T> void request(BaseObserver<T> baseObserver) {
        Observable observable = null;
        if (mBaseRequest != null) {
            observable = mBaseRequest.getObservable();
        }
        if (observable != null) {
            observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(baseObserver);
        }
    }
}
