package com.xia.fly.utils;

import com.blankj.utilcode.util.Utils;
import com.xia.fly.base.App;
import com.xia.fly.di.component.AppComponent;

/**
 * @author xia
 * @date 2018/9/14.
 */
public final class FlyUtils {

    public static AppComponent getAppComponent() {
        Preconditions.checkNotNull(Utils.getApp(), "%s cannot be null", Utils.getApp().getClass().getName());
        Preconditions.checkState(Utils.getApp() instanceof App, "Application does not implements App");
        return ((App) Utils.getApp()).getAppComponent();
    }
}
