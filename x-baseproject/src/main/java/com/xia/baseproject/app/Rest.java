package com.xia.baseproject.app;

import android.content.Context;

import com.xia.baseproject.app.RestConfigKeys.ConfigKey;

/**
 * @author xia
 * @date 2018/7/28.
 */
@SuppressWarnings("WeakerAccess")
public final class Rest {

    public static RestConfigurator init(Context context) {
        getConfigurator().getConfigs()
                .put(RestConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return getConfigurator();
    }

    public static RestConfigurator getConfigurator() {
        return RestConfigurator.getInstance();
    }

    public static Context getApplicationContext() {
        return getConfiguration(RestConfigKeys.APPLICATION_CONTEXT);
    }

    public static <T> T getConfiguration(@ConfigKey String configKey) {
        return getConfigurator().getConfiguration(configKey);
    }
}
