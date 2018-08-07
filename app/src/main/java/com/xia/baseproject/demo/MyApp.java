package com.xia.baseproject.demo;

import com.blankj.utilcode.util.ThreadUtils;
import com.xia.baseproject.BaseApp;
import com.xia.baseproject.app.Rest;
import com.xia.baseproject.rxhttp.interceptors.BaseInterceptor;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;
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

        //http://www.wanandroid.com
        Rest.init(this)
                .withApiHost("http://fz-gp.d1gu.cn")//http://fz-gp.d1gu.cn
                .withNetWorkCheck(true)
                .withInterceptor(loggingInterceptor)
                .withInterceptor(new BaseInterceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        final Request originalRequest = chain.request();
                        final Request authorised = originalRequest.newBuilder()
                                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X)" +
                                        " AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
                                .addHeader("Accept", "*/*")
                                .addHeader("X-Requested-With", "XMLHttpRequest")
                                .addHeader("x-site-id", "100001")// touch 100003
                                .addHeader("x-term", "1")//touch 0  app 1
                                .addHeader("x-client", "napk-test")
                                .addHeader("x-ver", "1.0.0")
                                .build();
                        return chain.proceed(authorised);
                    }
                })
                .configure();
    }
}
