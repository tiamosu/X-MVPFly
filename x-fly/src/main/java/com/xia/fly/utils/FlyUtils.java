package com.xia.fly.utils;

import android.content.Context;

import com.xia.fly.app.IApp;
import com.xia.fly.di.component.AppComponent;

/**
 * @author xia
 * @date 2018/9/14.
 */
public final class FlyUtils {

    public static AppComponent obtainAppComponentFromContext(Context context) {
        Preconditions.checkNotNull(context, "%s cannot be null", Context.class.getName());
        Preconditions.checkState(context.getApplicationContext() instanceof IApp, "Application does not implements App");
        return ((IApp) context.getApplicationContext()).getAppComponent();
    }
}
