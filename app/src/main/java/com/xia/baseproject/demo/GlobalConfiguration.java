package com.xia.baseproject.demo;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.xia.fly.base.IAppLifecycles;
import com.xia.fly.di.module.GlobalConfigModule;
import com.xia.fly.http.cookie.CookieManager;
import com.xia.fly.http.log.RequestInterceptor;
import com.xia.fly.imageloader.GlideImageLoaderStrategy;
import com.xia.fly.integration.ConfigModule;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xia
 * @date 2018/9/18.
 */
public final class GlobalConfiguration implements ConfigModule {

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
//        if (!BuildConfig.DEBUG) { //Release 时,让框架不再打印 Http 请求和响应的信息
        builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
//        }
        builder.baseurl("http://www.wanandroid.com")
                .imageLoaderStrategy(new GlideImageLoaderStrategy())
                .okhttpConfiguration((context1, okHttpBuilder) -> okHttpBuilder
                        //设置超时
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        //错误重连
                        .retryOnConnectionFailure(true)
                        //cookie认证
                        .cookieJar(CookieManager.getInstance()))
                .retrofitConfiguration((context12, retrofitBuilder) -> {
                });
    }

    @Override
    public void injectAppLifecycle(Context context, List<IAppLifecycles> lifecycles) {
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
    }
}
