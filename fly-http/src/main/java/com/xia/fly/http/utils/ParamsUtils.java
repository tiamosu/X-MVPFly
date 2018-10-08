package com.xia.fly.http.utils;

import java.util.Map;

/**
 * @author xia
 * @date 2018/10/8.
 * <p>
 * 处理空参数
 */
@SuppressWarnings("UnusedReturnValue")
public final class ParamsUtils {

    private ParamsUtils() {
        throw new IllegalStateException("u can't instantiate me!");
    }

    public static void escapeParams(Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String value = entry.getValue();
                value = value == null ? "" : value;
                map.put(entry.getKey(), value);
            }
        }
    }
}
