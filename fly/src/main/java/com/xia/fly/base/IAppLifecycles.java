package com.xia.fly.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

/**
 * 用于代理 {@link Application} 的生命周期
 *
 * @author xia
 * @date 2018/9/14.
 */
public interface IAppLifecycles {

    void attachBaseContext(@NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);

    void onConfigurationChanged(Configuration newConfig);

    void onLowMemory();

    void onTrimMemory(int level);
}
