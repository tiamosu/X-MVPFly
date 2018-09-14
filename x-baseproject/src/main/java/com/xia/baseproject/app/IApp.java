package com.xia.baseproject.app;

import android.support.annotation.NonNull;

import com.xia.baseproject.di.component.AppComponent;

/**
 * @author xia
 * @date 2018/9/14.
 */
public interface IApp {

    @NonNull
    AppComponent getAppComponent();
}
