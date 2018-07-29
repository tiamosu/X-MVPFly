package com.xia.baseproject.rxhttp.config;

import com.xia.baseproject.rxhttp.config.RestConfigKeys.ConfigKey;

/**
 * @author xia
 * @date 2018/7/28.
 */
@SuppressWarnings("WeakerAccess")
public final class Rest {

    public static RestConfigurator init() {
        return getConfigurator();
    }

    public static RestConfigurator getConfigurator() {
        return RestConfigurator.getInstance();
    }

    public static <T> T getConfiguration(@ConfigKey String configKey) {
        return getConfigurator().getConfiguration(configKey);
    }
}
