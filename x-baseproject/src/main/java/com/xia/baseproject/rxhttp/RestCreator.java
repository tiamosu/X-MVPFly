package com.xia.baseproject.rxhttp;

import com.xia.baseproject.rxhttp.api.RestService;
import com.xia.baseproject.app.Rest;
import com.xia.baseproject.app.RestConfigKeys;
import com.xia.baseproject.rxhttp.cookie.CookieJarImpl;
import com.xia.baseproject.rxhttp.cookie.store.MemoryCookieStore;
import com.xia.baseproject.rxhttp.utils.HttpsUtils;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author xia
 * @date 2018/7/27.
 */
public final class RestCreator {

    private static final HttpsUtils.SSLParams SSL_PARAMS
            = HttpsUtils.getSslSocketFactory(null, null, null);

    public static final class OKHttpHolder {
        private static final long TIME_OUT = 10;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = Rest.getConfiguration(RestConfigKeys.INTERCEPTOR);

        @SuppressWarnings("StatementWithEmptyBody")
        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        public static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .hostnameVerifier(new HttpsUtils.HOSTNAME_VERIFIER())
                .sslSocketFactory(SSL_PARAMS.sSLSocketFactory, SSL_PARAMS.trustManager)
                .proxy(Proxy.NO_PROXY)
                .build();
    }

    public static final class RetrofitHolder {
        private static final String BASE_URL = Rest.getConfiguration(RestConfigKeys.API_HOST);
        public static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }
}