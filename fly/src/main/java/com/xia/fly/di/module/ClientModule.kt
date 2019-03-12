package com.xia.fly.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.xia.fly.di.named.RxCacheDirectoryNamed
import com.xia.fly.http.GlobalHttpHandler
import com.xia.fly.http.interceptors.RequestInterceptor
import com.xia.fly.utils.FileUtils
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
import okhttp3.Dispatcher
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 提供一些三方库客户端实例的 [Module]
 *
 * @author xia
 * @date 2018/9/14.
 */
@Suppress("unused")
@Module
abstract class ClientModule {

    @Binds
    abstract fun bindInterceptor(interceptor: RequestInterceptor): Interceptor

    /**
     * [Retrofit] 自定义配置接口
     */
    interface RetrofitConfiguration {
        fun configRetrofit(context: Context, retrofitBuilder: Retrofit.Builder)
    }

    /**
     * [OkHttpClient] 自定义配置接口
     */
    interface OkHttpConfiguration {
        fun configOkHttp(context: Context, okHttpBuilder: OkHttpClient.Builder)
    }

    /**
     * [RxCache] 自定义配置接口
     */
    interface RxCacheConfiguration {
        /**
         * 若想自定义 RxCache 的缓存文件夹或者解析方式, 如改成 FastJson
         * 请 `return rxCacheBuilder.persistence(cacheDirectory, new FastJsonSpeaker());`, 否则请 `return null;`
         *
         * @param context        [Context]
         * @param rxCacheBuilder [RxCache.Builder]
         * @return [RxCache]
         */
        fun configRxCache(context: Context, rxCacheBuilder: RxCache.Builder): RxCache
    }

    @Module
    companion object {
        private const val TIME_OUT = 10

        /**
         * 提供 [Retrofit]
         *
         * @param application   [Application]
         * @param configuration [RetrofitConfiguration]
         * @param builder       [Retrofit.Builder]
         * @param client        [OkHttpClient]
         * @param httpUrl       [HttpUrl]
         * @param gson          [Gson]
         * @return [Retrofit]
         */
        @JvmStatic
        @Singleton
        @Provides
        fun provideRetrofit(application: Application,
                            configuration: RetrofitConfiguration?,
                            builder: Retrofit.Builder, client: OkHttpClient,
                            httpUrl: HttpUrl?, gson: Gson): Retrofit {
            builder
                    .baseUrl(httpUrl!!)//域名
                    .client(client)//设置 OkHttp
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用 RxJava
                    .addConverterFactory(GsonConverterFactory.create(gson))//使用 Gson

            configuration?.configRetrofit(application, builder)
            return builder.build()
        }

        /**
         * 提供 [OkHttpClient]
         *
         * @param application     [Application]
         * @param configuration   [OkHttpConfiguration]
         * @param builder         [OkHttpClient.Builder]
         * @param intercept       [Interceptor]
         * @param interceptors    [<]
         * @param handler         [GlobalHttpHandler]
         * @param executorService [ExecutorService]
         * @return [OkHttpClient]
         */
        @JvmStatic
        @Singleton
        @Provides
        fun provideClient(application: Application,
                          configuration: OkHttpConfiguration?,
                          builder: OkHttpClient.Builder,
                          intercept: Interceptor,
                          interceptors: List<@JvmSuppressWildcards Interceptor>?,
                          handler: GlobalHttpHandler?,
                          executorService: ExecutorService): OkHttpClient {
            builder
                    .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                    .addNetworkInterceptor(intercept)

            if (handler != null) {
                builder.addInterceptor { chain -> chain.proceed(handler.onHttpRequestBefore(chain, chain.request())) }
            }

            //如果外部提供了interceptor的集合则遍历添加
            if (interceptors != null) {
                for (interceptor in interceptors) {
                    builder.addInterceptor(interceptor)
                }
            }

            //为 OkHttp 设置默认的线程池
            builder.dispatcher(Dispatcher(executorService))

            configuration?.configOkHttp(application, builder)
            return builder.build()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideRetrofitBuilder(): Retrofit.Builder {
            return Retrofit.Builder()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideClientBuilder(): OkHttpClient.Builder {
            return OkHttpClient.Builder()
        }

        /**
         * 提供 [RxCache]
         *
         * @param application    [Application]
         * @param configuration  [RxCacheConfiguration]
         * @param cacheDirectory RxCache 缓存路径
         * @param gson           [Gson]
         * @return [RxCache]
         */
        @JvmStatic
        @Singleton
        @Provides
        fun provideRxCache(application: Application,
                           configuration: RxCacheConfiguration?,
                           @RxCacheDirectoryNamed cacheDirectory: File?,
                           gson: Gson): RxCache {
            val builder = RxCache.Builder()
            var rxCache: RxCache? = null
            if (configuration != null) {
                rxCache = configuration.configRxCache(application, builder)
            }
            return rxCache ?: builder.persistence(cacheDirectory, GsonSpeaker(gson))
        }

        /**
         * 需要单独给 [RxCache] 提供子缓存文件
         *
         * @param cacheDir 框架缓存文件
         * @return [File]
         */
        @JvmStatic
        @Singleton
        @Provides
        @RxCacheDirectoryNamed
        fun provideRxCacheDirectory(cacheDir: File): File? {
            val cacheDirectory = File(cacheDir, "RxCache")
            return FileUtils.createOrExistsDir(cacheDirectory)
        }

        /**
         * 提供处理 RxJava 错误的管理器
         *
         * @param application [Application]
         * @param listener    [ResponseErrorListener]
         * @return [RxErrorHandler]
         */
        @JvmStatic
        @Singleton
        @Provides
        fun proRxErrorHandler(application: Application, listener: ResponseErrorListener): RxErrorHandler {
            return RxErrorHandler
                    .builder()
                    .with(application)
                    .responseErrorListener(listener)
                    .build()
        }
    }
}
