package com.xia.baseproject.demo;

import com.blankj.utilcode.util.ThreadUtils;
import com.squareup.leakcanary.LeakCanary;
import com.xia.baseproject.app.BaseApp;
import com.xia.baseproject.app.Rest;

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

        Rest.init(this)
                .withApiHost("http://www.wanandroid.com")
                .withNetWorkCheck(true)
                .withInterceptor(loggingInterceptor)
                .configure();
    }
}
