package com.xia.fly.utils;

import com.blankj.utilcode.util.Utils;
import com.xia.fly.base.App;
import com.xia.fly.di.component.AppComponent;

/**
 * @author xia
 * @date 2018/9/14.
 */
public final class FlyUtils {

    private FlyUtils() {
        throw new IllegalStateException("u can't instantiate me!");
    }

    public static AppComponent getAppComponent() {
        Preconditions.checkNotNull(Utils.getApp(), "%s == null", Utils.getApp().getClass().getName());
        Preconditions.checkState(Utils.getApp() instanceof App, "%s must be implements %s",
                Utils.getApp().getClass().getName(), App.class.getName());
        return ((App) Utils.getApp()).getAppComponent();
    }
}
