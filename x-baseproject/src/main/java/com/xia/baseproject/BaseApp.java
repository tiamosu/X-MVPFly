package com.xia.baseproject;

import android.app.Application;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.multidex.MultiDex;

/**
 * @author xia
 * @date 2018/7/2.
 */
public class BaseApp extends Application {

    @CallSuper
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
