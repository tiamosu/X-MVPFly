package com.xia.baseproject.rxhttp.request;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.xia.baseproject.rxhttp.callback.Callback;
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

    public final void request(final Callback callback) {
        if (callback == null || mObservable == null) {
            return;
        }
        LifecycleOwner lifecycleOwner = null;
        Context context = null;
        final AppCompatActivity activity = callback.mActivity;
        final Fragment fragment = callback.mFragment;
        if (activity != null) {
            lifecycleOwner = activity;
            context = activity;
        }
        if (fragment != null) {
            lifecycleOwner = fragment;
            context = fragment.getContext();
        }
        if (lifecycleOwner != null && context != null) {
            mObservable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .retryWhen(new RetryExceptionFunc())
                    .as(RxLifecycleUtils.bindLifecycle(lifecycleOwner))
                    .subscribe(new CallbackSubscriber(context, callback));
        }
    }
}
