package com.xia.baseproject.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

/**
 * @author xia
 * @date 2018/7/19.
 */
public interface IMvpPresenter<V extends IMvpView> {

    @UiThread
    void attachView(@NonNull V view);

    @UiThread
    void detachView();

    @UiThread
    void onCreate();

    @UiThread
    void onStart();

    @UiThread
    void onResume();

    @UiThread
    void onPause();

    @UiThread
    void onStop();

    @UiThread
    void onDestroy();
}
