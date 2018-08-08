package com.xia.baseproject.demo;

import com.blankj.utilcode.util.ThreadUtils;
import com.xia.baseproject.BaseApp;
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

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Rest.init(this)
                .withApiHost("http://www.wanandroid.com")
                .withNetWorkCheck(true)
                .withInterceptor(loggingInterceptor)
                .configure();
    }
}
