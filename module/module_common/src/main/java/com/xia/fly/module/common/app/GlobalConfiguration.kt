package com.xia.fly.module.common.app

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.xia.fly.base.delegate.AppLifecycles
import com.xia.fly.di.module.ClientModule
import com.xia.fly.di.module.GlobalConfigModule
import com.xia.fly.http.cookie.CookieJarImpl
import com.xia.fly.http.cookie.store.MemoryCookieStore
import com.xia.fly.http.interceptors.RequestInterceptor
import com.xia.fly.http.utils.HttpsUtils
import com.xia.fly.http.utils.RxJavaUtils
import com.xia.fly.imageloader.GlideImageLoaderStrategy
import com.xia.fly.integration.ConfigModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * @author xia
 * @date 2018/9/18.
 */
@Suppress("unused", "UNUSED_ANONYMOUS_PARAMETER")
class GlobalConfiguration : ConfigModule {

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        //RxJava2 取消订阅后，抛出的异常无法捕获，将导致程序崩溃
        RxJavaUtils.setRxJavaErrorHandler()

        //        if (!BuildConfig.DEBUG) { //Release 时,让框架不再打印 Http 请求和响应的信息
        builder.printHttpLogLevel(RequestInterceptor.Level.NONE)
        //        }
        builder.baseurl("https://www.wanandroid.com")
                .imageLoaderStrategy(GlideImageLoaderStrategy())
                .okhttpConfiguration(object : ClientModule.OkHttpConfiguration {
                    override fun configOkHttp(context: Context, okHttpBuilder: OkHttpClient.Builder) {
                        okHttpBuilder
                                //打印返回信息
                                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                                //阻止第三方使用 Fiddler 或 Charles 进行抓包
                                .proxy(Proxy.NO_PROXY)
                                //设置超时
                                .connectTimeout(10, TimeUnit.SECONDS)
                                .readTimeout(10, TimeUnit.SECONDS)
                                .writeTimeout(10, TimeUnit.SECONDS)
                                //错误重连
                                .retryOnConnectionFailure(true)
                                //cookie认证
                                .cookieJar(CookieJarImpl(MemoryCookieStore()))
                                .hostnameVerifier(HttpsUtils.SafeHostnameVerifier())

//                        if (SSL_PARAMS.sslSocketFactory != null && SSL_PARAMS.trustManager != null) {
//                            okHttpBuilder.sslSocketFactory(SSL_PARAMS.sslSocketFactory!!, SSL_PARAMS.trustManager!!)
//                        }
                    }
                })
                .retrofitConfiguration(object : ClientModule.RetrofitConfiguration {
                    override fun configRetrofit(context: Context, retrofitBuilder: Retrofit.Builder) {
                    }
                })
                .responseErrorListener(ResponseErrorListenerImpl())
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycles>) {
        lifecycles.add(AppLifecyclesImpl())
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>) {}

    override fun injectFragmentLifecycle(context: Context, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {}

    companion object {

        private val SSL_PARAMS = HttpsUtils.getSslSocketFactory()
    }
}
