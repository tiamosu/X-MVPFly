package com.xia.fly.http.request

import com.xia.fly.http.api.RestService
import com.xia.fly.http.utils.ParamsUtils
import com.xia.fly.utils.FlyUtils
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.io.File

/**
 * @author xia
 * @date 2018/8/3.
 */
@Suppress("UNCHECKED_CAST", "unused")
abstract class BaseRequest<R : BaseRequest<R>>(@JvmField protected val mUrl: String) {
    @JvmField
    protected val mParams = LinkedHashMap<String, String>()
    @JvmField
    protected val mFileParams = LinkedHashMap<String, File>()
    @JvmField
    protected val mHeaders = LinkedHashMap<String, String>()

    fun addParam(key: String, value: String?): R {
        mParams[key] = value ?: ""
        return this as R
    }

    fun addParams(params: MutableMap<String, String?>?): R {
        mParams.putAll(ParamsUtils.escapeParams(params))
        return this as R
    }

    fun addFileParam(key: String, file: File?): R {
        if (file != null) {
            mFileParams[key] = file
        }
        return this as R
    }

    fun addFileParams(fileParams: MutableMap<String, File?>?): R {
        mFileParams.putAll(ParamsUtils.escapeFileParams(fileParams))
        return this as R
    }

    fun addHeader(key: String, value: String?): R {
        mHeaders[key] = value ?: ""
        return this as R
    }

    fun addHeaders(headers: MutableMap<String, String>?): R {
        if (headers?.isNotEmpty() == true) {
            mHeaders.putAll(headers)
        }
        return this as R
    }

    protected abstract fun generateRequest(): Observable<ResponseBody>?

    fun build(): RequestCall {
        return RequestCall(generateRequest())
    }

    companion object {

        @JvmField
        val mRestService = FlyUtils.getAppComponent().repositoryManager()
                .obtainRetrofitService(RestService::class.java)
    }
}
