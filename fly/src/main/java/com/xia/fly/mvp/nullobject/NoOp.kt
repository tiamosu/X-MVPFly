package com.xia.fly.mvp.nullobject

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy.newProxyInstance

/**
 * @author weixia
 * @date 2019/6/12.
 */
object NoOp {

    private val DEFAULT_VALUE = DefaultValueInvocationHandler()

    @Suppress("UNCHECKED_CAST")
    fun <T> of(interfaceClass: Class<T>): T {
        return newProxyInstance(interfaceClass.classLoader,
                arrayOf<Class<*>>(interfaceClass),
                DEFAULT_VALUE) as T
    }

    private class DefaultValueInvocationHandler : InvocationHandler {
        override fun invoke(proxy: Any?, method: Method?, args: Array<Any>?): Any? {
            return Defaults.defaultValue(method?.returnType)
        }
    }
}
