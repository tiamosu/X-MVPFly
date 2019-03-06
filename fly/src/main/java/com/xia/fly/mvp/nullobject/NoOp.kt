package com.xia.fly.mvp.nullobject

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

import java.lang.reflect.Proxy.newProxyInstance

/**
 * @author xia
 * @date 2018/7/19.
 */
@Suppress("UNCHECKED_CAST")
object NoOp {

    private val DEFAULT_VALUE = DefaultValueInvocationHandler()

    fun <T> of(interfaceClass: Class<T>): T {
        return newProxyInstance(interfaceClass.classLoader,
                arrayOf<Class<*>>(interfaceClass),
                DEFAULT_VALUE) as T
    }

    private class DefaultValueInvocationHandler : InvocationHandler {
        override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any {
            return Defaults.defaultValue(method.returnType)
        }
    }
}
