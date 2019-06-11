package com.xia.fly.http.converter

import com.google.gson.Gson
import com.xia.fly.http.callback.IGenericsSerializator

/**
 * @author weixia
 * @date 2019/6/11.
 */
class GsonConverter : IGenericsSerializator {
    private val mGson = Gson()

    override fun <T> transform(response: String, classOfT: Class<T>): T {
        return mGson.fromJson(response, classOfT)
    }
}
