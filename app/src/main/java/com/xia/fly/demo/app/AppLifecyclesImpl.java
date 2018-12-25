package com.xia.fly.demo.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.squareup.leakcanary.LeakCanary;
import com.xia.fly.BuildConfig;
import com.xia.fly.base.delegate.AppLifecycles;

import androidx.annotation.NonNull;
import me.yokeyword.fragmentation.Fragmentation;

import static android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN;

/**
 * @author xia
 * @date 2018/9/18.
 */
public class AppLifecyclesImpl implements AppLifecycles {

    @Override
    public void attachBaseContext(@NonNull Context base) {
    }

    @Override
    public void onCreate(@NonNull Application application) {
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application);
        }

        Fragmentation.builder()
                //设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .handleException(e -> {
                })
                .install();
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
