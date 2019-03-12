package com.xia.fly.http.interceptors

import android.util.Log
import com.xia.fly.di.module.GlobalConfigModule
import com.xia.fly.http.GlobalHttpHandler
import com.xia.fly.http.log.FormatPrinter
import com.xia.fly.utils.CharacterHandler
import com.xia.fly.utils.ZipHelper
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 解析框架中的网络请求和响应结果,并以日志形式输出,调试神器
 * 可使用 [GlobalConfigModule.Builder.printHttpLogLevel] 控制或关闭日志
 *
 * @author xia
 * @date 2018/9/14.
 */
@Singleton
class RequestInterceptor @Inject
constructor() : BaseInterceptor() {

    @JvmField
    @Inject
    internal var mHandler: GlobalHttpHandler? = null
    @JvmField
    @Inject
    internal var mPrinter: FormatPrinter? = null
    @JvmField
    @Inject
    internal var printLevel: Level? = null

    enum class Level {
        /**
         * 不打印log
         */
        NONE,
        /**
         * 只打印请求信息
         */
        REQUEST,
        /**
         * 只打印响应信息
         */
        RESPONSE,
        /**
         * 所有数据全部打印
         */
        ALL
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val logRequest = printLevel == Level.ALL || printLevel != Level.NONE && printLevel == Level.REQUEST

        if (logRequest) {
            //打印请求信息
            val requestBody = request.body()
            if (requestBody != null && isParseable(requestBody.contentType())) {
                val parseString = parseParams(request)
                if (parseString != null) {
                    mPrinter!!.printJsonRequest(request, parseString)
                }
            } else {
                mPrinter!!.printFileRequest(request)
            }
        }

        val logResponse = printLevel == Level.ALL || printLevel != Level.NONE && printLevel == Level.RESPONSE
        val t1 = if (logResponse) System.nanoTime() else 0
        val originalResponse: Response

        try {
            originalResponse = chain.proceed(request)
        } catch (e: IOException) {
            Log.w(TAG, "Http Error: $e")
            throw e
        }

        val t2 = if (logResponse) System.nanoTime() else 0
        val responseBody = originalResponse.body()

        //打印响应结果
        var bodyString: String? = null
        if (responseBody != null && isParseable(responseBody.contentType())) {
            bodyString = printResult(request, originalResponse, logResponse)
        }

        if (logResponse) {
            val segmentList = request.url().encodedPathSegments()
            val header = originalResponse.headers().toString()
            val code = originalResponse.code()
            val isSuccessful = originalResponse.isSuccessful
            val message = originalResponse.message()
            val url = originalResponse.request().url().toString()

            if (responseBody != null && isParseable(responseBody.contentType())) {
                mPrinter!!.printJsonResponse(TimeUnit.NANOSECONDS.toMillis(t2 - t1), isSuccessful,
                        code, header, responseBody.contentType(), bodyString, segmentList, message, url)
            } else {
                mPrinter!!.printFileResponse(TimeUnit.NANOSECONDS.toMillis(t2 - t1),
                        isSuccessful, code, header, segmentList, message, url)
            }
        }

        //这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
        return if (mHandler != null) {
            mHandler!!.onHttpResultResponse(bodyString, chain, originalResponse)
        } else originalResponse
    }

    /**
     * 打印响应结果
     *
     * @param request     [Request]
     * @param response    [Response]
     * @param logResponse 是否打印响应结果
     * @return 解析后的响应结果
     */
    @Suppress("UNUSED_PARAMETER")
    private fun printResult(request: Request, response: Response, logResponse: Boolean): String? {
        try {
            //读取服务器返回的结果
            val responseBody = response.newBuilder().build().body() ?: return "responseBody is null"
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()

            //获取content的压缩类型
            val encoding = response.headers().get("Content-Encoding")
            val clone = buffer.clone()

            //解析response content
            return parseContent(responseBody, encoding, clone)
        } catch (e: IOException) {
            e.printStackTrace()
            return "{\"error\": \"" + e.message + "\"}"
        }
    }

    /**
     * 解析服务器响应的内容
     *
     * @param responseBody [ResponseBody]
     * @param encoding     编码类型
     * @param clone        克隆后的服务器响应内容
     * @return 解析后的响应结果
     */
    private fun parseContent(responseBody: ResponseBody, encoding: String?, clone: Buffer): String? {
        var charset: Charset? = Charset.forName("UTF-8")
        val contentType = responseBody.contentType()
        if (contentType != null) {
            charset = contentType.charset(charset)
        }
        charset ?: return ""
        return when {
            "gzip".equals(encoding, ignoreCase = true) -> //content 使用 gzip 压缩
                ZipHelper.decompressForGzip(clone.readByteArray(), convertCharset(charset))//解压
            "zlib".equals(encoding, ignoreCase = true) -> //content 使用 zlib 压缩
                ZipHelper.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset))//解压
            else -> //content 没有被压缩, 或者使用其他未知压缩方式
                clone.readString(charset)
        }
    }

    companion object {
        private const val TAG = "RequestInterceptor"

        /**
         * 解析请求服务器的请求参数
         *
         * @param request [Request]
         * @return 解析后的请求信息
         * @throws UnsupportedEncodingException
         */
        @JvmStatic
        fun parseParams(request: Request): String? {
            try {
                val body = request.newBuilder().build().body() ?: return ""
                val requestBuffer = Buffer()
                body.writeTo(requestBuffer)
                var charset: Charset? = Charset.forName("UTF-8")
                val contentType = body.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                }
                return if (charset != null) {
                    CharacterHandler.jsonFormat(URLDecoder.decode(requestBuffer.readString(charset), convertCharset(charset)))
                } else null
            } catch (e: IOException) {
                e.printStackTrace()
                return "{\"error\": \"" + e.message + "\"}"
            }
        }

        /**
         * 是否可以解析
         *
         * @param mediaType [MediaType]
         * @return `true` 为可以解析
         */
        @JvmStatic
        fun isParseable(mediaType: MediaType?): Boolean {
            return if (mediaType == null) {
                false
            } else isText(mediaType) || isPlain(mediaType)
                    || isJson(mediaType) || isForm(mediaType)
                    || isHtml(mediaType) || isXml(mediaType)
        }

        @JvmStatic
        fun isText(mediaType: MediaType?): Boolean {
            return "text" == mediaType?.type()
        }

        @JvmStatic
        fun isPlain(mediaType: MediaType?): Boolean {
            return mediaType?.subtype()?.toLowerCase()?.contains("plain") == true
        }

        @JvmStatic
        fun isJson(mediaType: MediaType?): Boolean {
            return mediaType?.subtype()?.toLowerCase()?.contains("json") == true
        }

        @JvmStatic
        fun isXml(mediaType: MediaType?): Boolean {
            return mediaType?.subtype()?.toLowerCase()?.contains("xml") == true
        }

        @JvmStatic
        fun isHtml(mediaType: MediaType?): Boolean {
            return mediaType?.subtype()?.toLowerCase()?.contains("html") == true
        }

        @JvmStatic
        fun isForm(mediaType: MediaType?): Boolean {
            return mediaType?.subtype()?.toLowerCase()?.contains("x-www-form-urlencoded") == true
        }

        @JvmStatic
        fun convertCharset(charset: Charset): String {
            val s = charset.toString()
            val i = s.indexOf("[")
            return if (i == -1) {
                s
            } else s.substring(i + 1, s.length - 1)
        }
    }
}
