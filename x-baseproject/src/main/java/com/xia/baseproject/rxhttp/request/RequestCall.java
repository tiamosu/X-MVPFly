package com.xia.baseproject.rxhttp.request;

import android.arch.lifecycle.LifecycleOwner;

import com.xia.baseproject.rxhttp.subscriber.BaseSubscriber;
import com.xia.baseproject.rxhttp.utils.RxLifecycleUtils;

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

    public RequestCall() {
    }

    @SuppressWarnings("unchecked")
    public final <T> void request(BaseSubscriber<T> baseSubscriber) {
        if (baseSubscriber != null) {
//            final LifecycleOwner lifecycleOwner = baseSubscriber.getLifecycleOwner();
//            if (lifecycleOwner == null || mBaseRequest == null) {
//                return;
//            }
//            final Observable<T> observable = mBaseRequest.getObservable();
//            if (observable != null) {
//                observable.subscribeOn(Schedulers.io())
//                        .unsubscribeOn(Schedulers.io())
//                        .as(RxLifecycleUtils.bindLifecycle(lifecycleOwner))
//                        .subscribe(baseSubscriber);
//            }
        }
    }
}
