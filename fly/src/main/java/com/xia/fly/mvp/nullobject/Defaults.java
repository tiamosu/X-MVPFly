package com.xia.fly.mvp.nullobject;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

/**
 * @author xia
 * @date 2018/7/19.
 */
public final class Defaults {

    private static final Map<Class<?>, Object> DEFAULTS =
            unmodifiableMap(new HashMap<Class<?>, Object>() {
                {
                    put(Boolean.TYPE, false);
                    put(Byte.TYPE, (byte) 0);
                    put(Character.TYPE, '\000');
                    put(Double.TYPE, 0.0d);
                    put(Float.TYPE, 0.0f);
                    put(Integer.TYPE, 0);
                    put(Long.TYPE, 0L);
                    put(Short.TYPE, (short) 0);
                }
            });

    @SuppressWarnings("unchecked")
    public static <T> T defaultValue(Class<T> type) {
        return (T) DEFAULTS.get(type);
    }
}
