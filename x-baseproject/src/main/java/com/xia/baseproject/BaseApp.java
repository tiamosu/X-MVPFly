package com.xia.baseproject;

import android.app.Application;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.xia.baseproject.rxhttp.utils.Platform;

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
}
