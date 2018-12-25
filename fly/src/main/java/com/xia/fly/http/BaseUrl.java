package com.xia.fly.http;

import androidx.annotation.NonNull;
import okhttp3.HttpUrl;

/**
 * @author xia
 * @date 2018/9/14.
 */
public interface BaseUrl {

    /**
     * @return 在调用 Retrofit API 接口之前,使用 Okhttp 或其他方式,请求到正确的 BaseUrl 并通过此方法返回
     */
    @NonNull
    HttpUrl url();
}
