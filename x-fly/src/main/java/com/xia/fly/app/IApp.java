package com.xia.fly.app;

import android.support.annotation.NonNull;

import com.xia.fly.di.component.AppComponent;

/**
 * @author xia
 * @date 2018/9/14.
 */
public interface IApp {

    @NonNull
    AppComponent getAppComponent();
}
