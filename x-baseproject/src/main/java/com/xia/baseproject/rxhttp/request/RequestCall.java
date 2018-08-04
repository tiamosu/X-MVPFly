package com.xia.baseproject.rxhttp.request;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.xia.baseproject.rxhttp.callback.Callback;
import com.xia.baseproject.rxhttp.subscriber.CallbackSubscriber;
import com.xia.baseproject.rxhttp.utils.RxLifecycleUtils;
import com.xia.baseproject.ui.fragments.SupportFragment;

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

    public final void request(final AppCompatActivity activity, final Callback callback) {
        commonRequest(activity, callback);
    }

    public final void request(final SupportFragment fragment, final Callback callback) {
        commonRequest(fragment, callback);
    }

    private void commonRequest(final Object o, Callback callback) {
        if (o == null || callback == null || mObservable == null) {
            return;
        }
        LifecycleOwner lifecycleOwner = null;
        Context context = null;
        if (o instanceof AppCompatActivity) {
            final AppCompatActivity activity = (AppCompatActivity) o;
            lifecycleOwner = activity;
            context = activity;
        }
        if (o instanceof SupportFragment) {
            final SupportFragment fragment = (SupportFragment) o;
            lifecycleOwner = fragment;
            context = fragment.getContext();
        }
        if (lifecycleOwner != null && context != null) {
            mObservable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .as(RxLifecycleUtils.bindLifecycle(lifecycleOwner))
                    .subscribe(new CallbackSubscriber(context, callback));
        }
    }
}
