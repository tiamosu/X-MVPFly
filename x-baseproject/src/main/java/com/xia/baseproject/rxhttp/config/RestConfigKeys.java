package com.xia.baseproject.rxhttp.config;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xia
 * @date 2018/7/28.
 */
@SuppressWarnings("WeakerAccess")
public final class RestConfigKeys {
    public static final String CONFIG_READY = "config_ready";
    public static final String INTERCEPTOR = "interceptor";
    public static final String API_HOST = "api_host";

    @StringDef({CONFIG_READY, INTERCEPTOR, API_HOST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ConfigKey {
    }
}
