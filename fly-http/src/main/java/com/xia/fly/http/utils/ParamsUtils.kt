package com.xia.fly.http.utils

/**
 * @author xia
 * @date 2018/10/8.
 *
 *
 * 用于处理空参数
 */
object ParamsUtils {

    @JvmStatic
    fun escapeParams(map: MutableMap<String, String>?) {
        if (map?.isNotEmpty() == true) {
            for (entry in map.entries) {
                val value: String? = entry.value
                map[entry.key] = value ?: ""
            }
        }
    }
}
