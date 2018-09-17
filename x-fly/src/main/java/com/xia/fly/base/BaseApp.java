package com.xia.fly.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.xia.fly.base.delegate.AppDelegate;
import com.xia.fly.di.component.AppComponent;
import com.xia.fly.utils.FlyUtils;
import com.xia.fly.utils.Platform;
import com.xia.fly.utils.Preconditions;

/**
 * @author xia
 * @date 2018/7/2.
 */
public class BaseApp extends Application implements IApp {
    private IAppLifecycles mAppDelegate;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        if (mAppDelegate == null) {
            this.mAppDelegate = new AppDelegate(base);
        }
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppDelegate != null) {
            this.mAppDelegate.onCreate(this);
        }
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null) {
            this.mAppDelegate.onTerminate(this);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (Platform.DEPENDENCY_GLIDE) {
            if (level == TRIM_MEMORY_UI_HIDDEN) {
                Glide.get(this).clearMemory();
            }
            Glide.get(this).trimMemory(level);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (Platform.DEPENDENCY_GLIDE) {
            Glide.get(this).clearMemory();
        }
    }

    /**
     * 将 {@link AppComponent} 返回出去, 供其它地方使用,
     * {@link AppComponent} 接口中声明的方法所返回的实例, 在 {@link #getAppComponent()} 拿到对象后都可以直接使用
     *
     * @return AppComponent
     * @see FlyUtils#obtainAppComponentFromContext(Context) 可直接获取 {@link AppComponent}
     */
    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(mAppDelegate instanceof IApp, "%s must be implements %s", mAppDelegate.getClass().getName(), IApp.class.getName());
        return ((IApp) mAppDelegate).getAppComponent();
    }
}
