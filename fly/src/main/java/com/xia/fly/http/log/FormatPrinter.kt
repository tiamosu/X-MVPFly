package com.xia.fly.http.log

import com.xia.fly.di.module.GlobalConfigModule
import okhttp3.MediaType
import okhttp3.Request

/**
 * 对 [okhttp3] 的请求和响应信息进行更规范和清晰的打印, 开发者可更根据自己的需求自行扩展打印格式
 *
 * @author xia
 * @date 2018/9/14.
 * @see DefaultFormatPrinter
 *
 * @see GlobalConfigModule.Builder.formatPrinter
 */
interface FormatPrinter {

    /**
     * 打印网络请求信息, 当网络请求时 {[okhttp3.RequestBody]} 可以解析的情况
     *
     * @param bodyString 发送给服务器的请求体中的数据(已解析)
     */
    fun printJsonRequest(request: Request, bodyString: String)

    /**
     * 打印网络请求信息, 当网络请求时 {[okhttp3.RequestBody]} 为 `null` 或不可解析的情况
     */
    fun printFileRequest(request: Request)

    /**
     * 打印网络响应信息, 当网络响应时 {[okhttp3.ResponseBody]} 可以解析的情况
     *
     * @param chainMs      服务器响应耗时(单位毫秒)
     * @param isSuccessful 请求是否成功
     * @param code         响应码
     * @param headers      请求头
     * @param contentType  服务器返回数据的数据类型
     * @param bodyString   服务器返回的数据(已解析)
     * @param segments     域名后面的资源地址
     * @param message      响应信息
     * @param responseUrl  请求地址
     */
    fun printJsonResponse(chainMs: Long, isSuccessful: Boolean, code: Int, headers: String,
                          contentType: MediaType?, bodyString: String?,
                          segments: List<String>, message: String, responseUrl: String)

    /**
     * 打印网络响应信息, 当网络响应时 {[okhttp3.ResponseBody]} 为 `null` 或不可解析的情况
     *
     * @param chainMs      服务器响应耗时(单位毫秒)
     * @param isSuccessful 请求是否成功
     * @param code         响应码
     * @param headers      请求头
     * @param segments     域名后面的资源地址
     * @param message      响应信息
     * @param responseUrl  请求地址
     */
    fun printFileResponse(chainMs: Long, isSuccessful: Boolean, code: Int, headers: String,
                          segments: List<String>, message: String, responseUrl: String)
}
