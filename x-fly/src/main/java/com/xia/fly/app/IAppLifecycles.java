package com.xia.fly.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author xia
 * @date 2018/9/14.
 * <p>
 * 用于代理 {@link Application} 的生命周期
 */
public interface IAppLifecycles {

    void attachBaseContext(@NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);
}
