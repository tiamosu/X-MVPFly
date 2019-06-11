package com.xia.fly.http.callback

/**
 * @author weixia
 * @date 2019/6/11.
 */
interface IGenericsSerializator {

    fun <T> transform(response: String, classOfT: Class<T>): T
}
