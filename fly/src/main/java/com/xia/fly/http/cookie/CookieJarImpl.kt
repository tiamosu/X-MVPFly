package com.xia.fly.http.cookie

import com.xia.fly.http.cookie.store.CookieStore
import com.xia.fly.utils.Preconditions
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Created by zhy on 16/3/10.
 */
class CookieJarImpl(private val cookieStore: CookieStore) : CookieJar {

    init {
        Preconditions.checkNotNull<Any>(cookieStore, "cookieStore can not be null")
    }

    @Synchronized
    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        cookieStore.add(url, cookies)
    }

    @Synchronized
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore.get(url)
    }
}
