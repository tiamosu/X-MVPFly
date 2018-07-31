package com.xia.baseproject.app;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xia
 * @date 2018/7/28.
 */
@SuppressWarnings("WeakerAccess")
public final class RestConfigKeys {
    public static final String CONFIG_READY = "CONFIG_READY";
    public static final String INTERCEPTOR = "INTERCEPTOR";
    public static final String API_HOST = "API_HOST";
    public static final String APPLICATION_CONTEXT = "APPLICATION_CONTEXT";
    public static final String NETWORK_CHECK = "NETWORK_CHECK";

    @StringDef({CONFIG_READY, INTERCEPTOR, API_HOST, APPLICATION_CONTEXT, NETWORK_CHECK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ConfigKey {
    }
}
