package com.xia.fly.http

import com.xia.fly.http.request.DeleteRequest
import com.xia.fly.http.request.GetRequest
import com.xia.fly.http.request.PostRequest
import com.xia.fly.http.request.PutRequest

/**
 * @author xia
 * @date 2018/7/29.
 */
@Suppress("unused")
object RxHttp {

    @JvmStatic
    operator fun get(url: String): GetRequest {
        return GetRequest(url)
    }

    @JvmStatic
    fun post(url: String): PostRequest {
        return PostRequest(url)
    }

    @JvmStatic
    fun delete(url: String): DeleteRequest {
        return DeleteRequest(url)
    }

    @JvmStatic
    fun put(url: String): PutRequest {
        return PutRequest(url)
    }
}
