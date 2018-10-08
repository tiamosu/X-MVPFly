package com.xia.fly.http.interceptor;

import android.text.TextUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author xia
 * @date 2018/10/8.
 * <p>
 * 用于处理读取单独 api 的超时时间的配置
 */
public class TimeoutInterceptor implements Interceptor {
    private static final String READ_TIMEOUT_NAME = "readTimeout";
    private static final String WRITE_TIMEOUT_NAME = "writeTimeout";
    private static final String CONNECTION_TIMEOUT_NAME = "connectionTimeout";
    private static final int LONG_TIMEOUT = 30000;

    private static final String LONG_READ_TIMEOUT = READ_TIMEOUT_NAME + ":" + LONG_TIMEOUT;
    private static final String LONG_WRITE_TIMEOUT = WRITE_TIMEOUT_NAME + ":" + LONG_TIMEOUT;
    private static final String LONG_CONNECTION_TIMEOUT = CONNECTION_TIMEOUT_NAME + ":" + LONG_TIMEOUT;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        final Headers.Builder builder = request.headers().newBuilder();

        final String readTimeout = request.header(READ_TIMEOUT_NAME);
        if (!TextUtils.isEmpty(readTimeout)) {
            try {
                chain.withReadTimeout(Integer.valueOf(readTimeout.trim()), TimeUnit.MILLISECONDS);
            } catch (Exception ignored) {
            }
            builder.removeAll(LONG_READ_TIMEOUT);
        }

        final String writeTimeout = request.header(WRITE_TIMEOUT_NAME);
        if (!TextUtils.isEmpty(writeTimeout)) {
            try {
                chain.withWriteTimeout(Integer.valueOf(writeTimeout.trim()), TimeUnit.MILLISECONDS);
            } catch (Exception ignored) {
            }
            builder.removeAll(LONG_WRITE_TIMEOUT);
        }

        final String connectionTimeout = request.header(CONNECTION_TIMEOUT_NAME);
        if (!TextUtils.isEmpty(connectionTimeout)) {
            try {
                chain.withConnectTimeout(Integer.valueOf(connectionTimeout.trim()), TimeUnit.MILLISECONDS);
            } catch (Exception ignored) {
            }
            builder.removeAll(LONG_CONNECTION_TIMEOUT);
        }

        request = request.newBuilder().headers(builder.build()).build();
        return chain.proceed(request);
    }
}
