package com.xia.fly.http.callback

import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.CloseUtils
import com.xia.fly.utils.Platform
import io.reactivex.functions.Action
import okhttp3.ResponseBody
import java.lang.reflect.ParameterizedType

/**
 * @author weixia
 * @date 2019/6/11.
 */
abstract class GenericsCallback<T>(lifecycleOwner: LifecycleOwner,
                                   private val mGenericsSerializator: IGenericsSerializator) : Callback<T>(lifecycleOwner) {

    @Suppress("UNCHECKED_CAST")
    @Throws(Exception::class)
    override fun parseNetworkResponse(responseBody: ResponseBody) {
        var type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            type = type.actualTypeArguments[0]
        }
        if (type is Class<*>) {
            val string = responseBody.string()
            val result: T? = if (type == String::class.java) {
                string as? T
            } else {
                mGenericsSerializator.transform(string, type) as? T
            }
            Platform.post(Action { onResponse(result) })
            CloseUtils.closeIO(responseBody)
        }
    }
}
