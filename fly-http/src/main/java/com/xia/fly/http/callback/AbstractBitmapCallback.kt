package com.xia.fly.http.callback

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.CloseUtils
import com.xia.fly.utils.Platform
import io.reactivex.functions.Action
import okhttp3.ResponseBody

/**
 * @author xia
 * @date 2018/7/28.
 */
@Suppress("unused")
abstract class AbstractBitmapCallback(lifecycleOwner: LifecycleOwner) : Callback<Bitmap>(lifecycleOwner) {

    @Throws(Exception::class)
    override fun parseNetworkResponse(responseBody: ResponseBody) {
        val `is` = responseBody.byteStream()
        val bitmap = BitmapFactory.decodeStream(`is`)
        Platform.post(Action { onResponse(bitmap) })
        CloseUtils.closeIO(responseBody, `is`)
    }
}
