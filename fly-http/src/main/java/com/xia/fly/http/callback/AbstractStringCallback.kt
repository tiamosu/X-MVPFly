package com.xia.fly.http.callback

import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.CloseUtils
import com.xia.fly.utils.Platform
import io.reactivex.functions.Action
import okhttp3.ResponseBody

/**
 * @author xia
 * @date 2018/7/28.
 */
abstract class AbstractStringCallback(lifecycleOwner: LifecycleOwner) : Callback<String>(lifecycleOwner) {

    @Throws(Exception::class)
    override fun parseNetworkResponse(responseBody: ResponseBody) {
        val result = responseBody.string()
        Platform.post(Action { onResponse(result) })
        CloseUtils.closeIO(responseBody)
    }
}