package com.xia.baseproject.rxhttp.callback;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/27.
 */
@SuppressWarnings("WeakerAccess")
public abstract class Callback<T> {
    public AppCompatActivity mActivity;
    public Fragment mFragment;

    public Callback(AppCompatActivity activity) {
        mActivity = activity;
    }

    public Callback(Fragment fragment) {
        mFragment = fragment;
    }

    public void onSubscribe(Disposable d) {
    }

    public void onError(String error) {
    }

    public void onComplete() {
    }

    public void inProgress(float progress, long total) {
    }

    public abstract T parseNetworkResponse(ResponseBody responseBody) throws Exception;

    public abstract void onResponse(T response);
}
