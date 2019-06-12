package com.xia.fly.mvp.nullobject

import java.util.*
import java.util.Collections.unmodifiableMap

/**
 * @author xia
 * @date 2018/7/19.
 */
@Suppress("UNCHECKED_CAST")
object Defaults {

    private val DEFAULTS = unmodifiableMap(object : HashMap<Class<*>, Any>() {
        init {
            put(java.lang.Boolean.TYPE, false)
            put(java.lang.Byte.TYPE, 0.toByte())
            put(Character.TYPE, '\u0000')
            put(java.lang.Double.TYPE, 0.0)
            put(java.lang.Float.TYPE, 0.0f)
            put(Integer.TYPE, 0)
            put(java.lang.Long.TYPE, 0L)
            put(java.lang.Short.TYPE, 0.toShort())
        }
    })

    @JvmStatic
    fun <T> defaultValue(type: Class<T>?): T? {
        type ?: return null
        return DEFAULTS[type] as? T
    }
}
