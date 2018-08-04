package com.xia.baseproject.rxhttp.method;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xia
 * @date 2018/8/4.
 */
@SuppressWarnings("WeakerAccess")
public class HttpMethod {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String HEAD = "HEAD";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String PATCH = "PATCH";

    @StringDef({GET, POST, HEAD, PUT, DELETE, PATCH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface method {
    }
}
