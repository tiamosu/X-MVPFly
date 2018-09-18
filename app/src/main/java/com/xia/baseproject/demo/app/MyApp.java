package com.xia.baseproject.demo.app;

import com.blankj.utilcode.util.ThreadUtils;
import com.squareup.leakcanary.LeakCanary;
import com.xia.fly.base.BaseApp;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author xia
 * @date 2018/7/2.
 */
public class MyApp extends BaseApp {

    @Override
    public void onCreate() {
        if (!ThreadUtils.isMainThread()) {
            return;
        }
        super.onCreate();
        initLeakCanary();
        initHttp();
    }

    private void initLeakCanary() {
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
    }

    private void initHttp() {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
