package com.xia.baseproject.demo.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.squareup.leakcanary.LeakCanary;
import com.xia.fly.base.delegate.IAppLifecycles;

import static android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN;

/**
 * @author xia
 * @date 2018/9/18.
 */
public class AppLifecyclesImpl implements IAppLifecycles {

    @Override
    public void attachBaseContext(@NonNull Context base) {
    }

    @Override
    public void onCreate(@NonNull Application application) {
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    }

    @Override
    public void onLowMemory() {
        Glide.get(Utils.getApp()).clearMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(Utils.getApp()).clearMemory();
        }
        Glide.get(Utils.getApp()).trimMemory(level);
    }
}
