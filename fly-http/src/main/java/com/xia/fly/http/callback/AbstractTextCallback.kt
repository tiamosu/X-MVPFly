package com.xia.fly.http.callback

import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.CloseUtils
import com.xia.fly.utils.Platform
import io.reactivex.functions.Action
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @author xia
 * @date 2018/7/28.
 */
@Suppress("unused")
abstract class AbstractTextCallback(lifecycleOwner: LifecycleOwner) : Callback<String>(lifecycleOwner) {

    @Throws(Exception::class)
    override fun parseNetworkResponse(responseBody: ResponseBody) {
        val `is` = responseBody.byteStream()
        val reader = InputStreamReader(`is`, "utf-8")
        val bufferedReader = BufferedReader(reader)
        var line: String
        val builder = StringBuilder()

        do {
            line = bufferedReader.readLine()
            if (line == null) {
                break
            }
            builder.append(line)
        } while (true)

        val result = builder.toString()
        Platform.post(Action { onResponse(result) })
        CloseUtils.closeIO(responseBody, `is`, reader, bufferedReader)
    }
}
