package com.xia.baseproject.app;

import android.os.Handler;

import com.xia.baseproject.app.RestConfigKeys.ConfigKey;

/**
 * @author xia
 * @date 2018/7/28.
 */
@SuppressWarnings("WeakerAccess")
public final class Rest {

    public static RestConfigurator init() {
        return RestConfigurator.getInstance();
    }

    public static <T> T getConfiguration(@ConfigKey String configKey) {
        return init().getConfiguration(configKey);
    }

    public static Handler getHandler() {
        return getConfiguration(RestConfigKeys.HANDLER);
    }
}
