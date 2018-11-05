package com.xia.fly.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.xia.fly.di.named.RxCacheDirectoryNamed;
import com.xia.fly.http.GlobalHttpHandler;
import com.xia.fly.http.interceptors.RequestInterceptor;
import com.xia.fly.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 提供一些三方库客户端实例的 {@link Module}
 *
 * @author xia
 * @date 2018/9/14.
 */
@Module
public abstract class ClientModule {
    private static final int TIME_OUT = 10;

    /**
     * 提供 {@link Retrofit}
     *
     * @return {@link Retrofit}
     */
    @Singleton
    @Provides
    static Retrofit provideRetrofit(final Application application,
                                    @Nullable final RetrofitConfiguration configuration,
                                    final Retrofit.Builder builder, final OkHttpClient client,
                                    final HttpUrl httpUrl, final Gson gson) {
        builder
                .baseUrl(httpUrl)//域名
                .client(client);//设置okhttp

        builder
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用 Rxjava
                .addConverterFactory(GsonConverterFactory.create(gson));//使用 Gson

        if (configuration != null) {
            configuration.configRetrofit(application, builder);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    static OkHttpClient provideClient(final Application application,
                                      @Nullable final OkHttpConfiguration configuration,
                                      final OkHttpClient.Builder builder,
                                      final Interceptor intercept,
                                      @Nullable final List<Interceptor> interceptors,
                                      @Nullable final GlobalHttpHandler handler,
                                      final ExecutorService executorService) {
        builder
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(intercept);

        if (handler != null) {
            builder.addInterceptor(chain -> chain.proceed(handler.onHttpRequestBefore(chain, chain.request())));
        }

        if (interceptors != null) {//如果外部提供了interceptor的集合则遍历添加
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        // 为 OkHttp 设置默认的线程池。
        builder.dispatcher(new Dispatcher(executorService));

        if (configuration != null) {
            configuration.configOkHttp(application, builder);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    static Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    static OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Binds
    abstract Interceptor bindInterceptor(RequestInterceptor interceptor);

    /**
     * 提供 {@link RxCache}
     *
     * @param cacheDirectory cacheDirectory RxCache缓存路径
     * @return {@link RxCache}
     */
    @Singleton
    @Provides
    static RxCache provideRxCache(final Application application,
                                  @Nullable final RxCacheConfiguration configuration,
                                  @RxCacheDirectoryNamed final File cacheDirectory,
                                  final Gson gson) {
        final RxCache.Builder builder = new RxCache.Builder();
        RxCache rxCache = null;
        if (configuration != null) {
            rxCache = configuration.configRxCache(application, builder);
        }
        if (rxCache != null) {
            return rxCache;
        }
        return builder.persistence(cacheDirectory, new GsonSpeaker(gson));
    }

    /**
     * 需要单独给 {@link RxCache} 提供缓存路径
     *
     * @return {@link File}
     */
    @Singleton
    @Provides
    @RxCacheDirectoryNamed
    static File provideRxCacheDirectory(File cacheDir) {
        final File cacheDirectory = new File(cacheDir, "RxCache");
        return FileUtils.createOrExistsDir(cacheDirectory);
    }

    /**
     * 提供处理 RxJava 错误的管理器
     *
     * @return {@link RxErrorHandler}
     */
    @Singleton
    @Provides
    static RxErrorHandler proRxErrorHandler(Application application, ResponseErrorListener listener) {
        return RxErrorHandler
                .builder()
                .with(application)
                .responseErrorListener(listener)
                .build();
    }

    public interface RetrofitConfiguration {
        void configRetrofit(Context context, Retrofit.Builder retrofitBuilder);
    }

    public interface OkHttpConfiguration {
        void configOkHttp(Context context, OkHttpClient.Builder okHttpBuilder);
    }

    public interface RxCacheConfiguration {
        /**
         * 若想自定义 RxCache 的缓存文件夹或者解析方式, 如改成 fastjson
         * 请 {@code return rxCacheBuilder.persistence(cacheDirectory, new FastJsonSpeaker());}, 否则请 {@code return null;}
         *
         * @return {@link RxCache}
         */
        RxCache configRxCache(Context context, RxCache.Builder rxCacheBuilder);
    }
}
