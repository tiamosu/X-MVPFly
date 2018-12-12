package com.xia.fly.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
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
     * @param application   {@link Application}
     * @param configuration {@link RetrofitConfiguration}
     * @param builder       {@link Retrofit.Builder}
     * @param client        {@link OkHttpClient}
     * @param httpUrl       {@link HttpUrl}
     * @param gson          {@link Gson}
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
                .client(client)//设置 OkHttp
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用 RxJava
                .addConverterFactory(GsonConverterFactory.create(gson));//使用 Gson

        if (configuration != null) {
            configuration.configRetrofit(application, builder);
        }
        return builder.build();
    }

    /**
     * 提供 {@link OkHttpClient}
     *
     * @param application     {@link Application}
     * @param configuration   {@link OkHttpConfiguration}
     * @param builder         {@link OkHttpClient.Builder}
     * @param intercept       {@link Interceptor}
     * @param interceptors    {@link List<Interceptor>}
     * @param handler         {@link GlobalHttpHandler}
     * @param executorService {@link ExecutorService}
     * @return {@link OkHttpClient}
     */
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

        //如果外部提供了interceptor的集合则遍历添加
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        //为 OkHttp 设置默认的线程池
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
     * @param application    {@link Application}
     * @param configuration  {@link RxCacheConfiguration}
     * @param cacheDirectory RxCache 缓存路径
     * @param gson           {@link Gson}
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
     * 需要单独给 {@link RxCache} 提供子缓存文件
     *
     * @param cacheDir 框架缓存文件
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
     * @param application {@link Application}
     * @param listener    {@link ResponseErrorListener}
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

    /**
     * {@link Retrofit} 自定义配置接口
     */
    public interface RetrofitConfiguration {
        void configRetrofit(@NonNull Context context, @NonNull Retrofit.Builder retrofitBuilder);
    }

    /**
     * {@link OkHttpClient} 自定义配置接口
     */
    public interface OkHttpConfiguration {
        void configOkHttp(@NonNull Context context, @NonNull OkHttpClient.Builder okHttpBuilder);
    }

    /**
     * {@link RxCache} 自定义配置接口
     */
    public interface RxCacheConfiguration {
        /**
         * 若想自定义 RxCache 的缓存文件夹或者解析方式, 如改成 FastJson
         * 请 {@code return rxCacheBuilder.persistence(cacheDirectory, new FastJsonSpeaker());}, 否则请 {@code return null;}
         *
         * @param context        {@link Context}
         * @param rxCacheBuilder {@link RxCache.Builder}
         * @return {@link RxCache}
         */
        RxCache configRxCache(@NonNull Context context, @NonNull RxCache.Builder rxCacheBuilder);
    }
}
