package com.xia.fly.app;

import com.xia.fly.app.RestConfigKeys.ConfigKey;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * @author xia
 * @date 2018/7/28.
 */
public final class RestConfigurator {
    private static final HashMap<Object, Object> CONFIGS = new HashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private static class SingleTonHolder {
        private static final RestConfigurator INSTANCE = new RestConfigurator();
    }

    public static RestConfigurator getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    private RestConfigurator() {
        CONFIGS.put(RestConfigKeys.CONFIG_READY, false);
        CONFIGS.put(RestConfigKeys.INTERCEPTOR, INTERCEPTORS);
    }

    public final HashMap<Object, Object> getConfigs() {
        return CONFIGS;
    }

    public final RestConfigurator withApiHost(String host) {
        CONFIGS.put(RestConfigKeys.API_HOST, host);
        return this;
    }

    public final RestConfigurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        CONFIGS.put(RestConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final RestConfigurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        CONFIGS.put(RestConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final void configure() {
        CONFIGS.put(RestConfigKeys.CONFIG_READY, true);
    }

    @SuppressWarnings("unchecked")
    public final <T> T getConfiguration(@ConfigKey String configKey) {
        checkConfiguration();
        final Object value = CONFIGS.get(configKey);
        if (value == null) {
            throw new NullPointerException(configKey + " IS NULL");
        }
        return (T) CONFIGS.get(configKey);
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) CONFIGS.get(RestConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }
}
