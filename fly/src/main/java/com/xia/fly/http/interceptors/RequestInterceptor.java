package com.xia.fly.http.interceptors;

import android.util.Log;

import com.xia.fly.di.module.GlobalConfigModule;
import com.xia.fly.http.GlobalHttpHandler;
import com.xia.fly.http.log.FormatPrinter;
import com.xia.fly.utils.CharacterHandler;
import com.xia.fly.utils.ZipHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 解析框架中的网络请求和响应结果,并以日志形式输出,调试神器
 * 可使用 {@link GlobalConfigModule.Builder#printHttpLogLevel(Level)} 控制或关闭日志
 *
 * @author xia
 * @date 2018/9/14.
 */
@Singleton
@SuppressWarnings("WeakerAccess")
public class RequestInterceptor extends BaseInterceptor {
    private static final String TAG = "RequestInterceptor";

    @Inject
    @Nullable
    GlobalHttpHandler mHandler;
    @Inject
    FormatPrinter mPrinter;
    @Inject
    Level printLevel;

    public enum Level {
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

    @Inject
    public RequestInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request request = chain.request();
        final boolean logRequest = printLevel == Level.ALL || (printLevel != Level.NONE && printLevel == Level.REQUEST);

        if (logRequest) {
            //打印请求信息
            final RequestBody requestBody = request.body();
            if (requestBody != null && isParseable(requestBody.contentType())) {
                final String parseString = parseParams(request);
                if (parseString != null) {
                    mPrinter.printJsonRequest(request, parseString);
                }
            } else {
                mPrinter.printFileRequest(request);
            }
        }

        final boolean logResponse = printLevel == Level.ALL || (printLevel != Level.NONE && printLevel == Level.RESPONSE);
        final long t1 = logResponse ? System.nanoTime() : 0;
        Response originalResponse;

        try {
            originalResponse = chain.proceed(request);
        } catch (IOException e) {
            Log.w(TAG, "Http Error: " + e);
            throw e;
        }
        final long t2 = logResponse ? System.nanoTime() : 0;
        final ResponseBody responseBody = originalResponse.body();

        //打印响应结果
        String bodyString = null;
        if (responseBody != null && isParseable(responseBody.contentType())) {
            bodyString = printResult(request, originalResponse, logResponse);
        }

        if (logResponse) {
            final List<String> segmentList = request.url().encodedPathSegments();
            final String header = originalResponse.headers().toString();
            final int code = originalResponse.code();
            final boolean isSuccessful = originalResponse.isSuccessful();
            final String message = originalResponse.message();
            final String url = originalResponse.request().url().toString();

            if (responseBody != null && isParseable(responseBody.contentType())) {
                mPrinter.printJsonResponse(TimeUnit.NANOSECONDS.toMillis(t2 - t1), isSuccessful,
                        code, header, responseBody.contentType(), bodyString, segmentList, message, url);
            } else {
                mPrinter.printFileResponse(TimeUnit.NANOSECONDS.toMillis(t2 - t1),
                        isSuccessful, code, header, segmentList, message, url);
            }
        }

        //这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
        if (mHandler != null) {
            return mHandler.onHttpResultResponse(bodyString, chain, originalResponse);
        }
        return originalResponse;
    }

    /**
     * 打印响应结果
     *
     * @param request     {@link Request}
     * @param response    {@link Response}
     * @param logResponse 是否打印响应结果
     * @return 解析后的响应结果
     */
    @Nullable
    private String printResult(Request request, Response response, boolean logResponse) {
        try {
            //读取服务器返回的结果
            final ResponseBody responseBody = response.newBuilder().build().body();
            if (responseBody == null) {
                return "responseBody is null";
            }
            final BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            final Buffer buffer = source.buffer();

            //获取content的压缩类型
            final String encoding = response.headers().get("Content-Encoding");
            final Buffer clone = buffer.clone();

            //解析response content
            return parseContent(responseBody, encoding, clone);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    /**
     * 解析服务器响应的内容
     *
     * @param responseBody {@link ResponseBody}
     * @param encoding     编码类型
     * @param clone        克隆后的服务器响应内容
     * @return 解析后的响应结果
     */
    private String parseContent(ResponseBody responseBody, String encoding, Buffer clone) {
        Charset charset = Charset.forName("UTF-8");
        final MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (charset == null) {
            return "";
        }
        if ("gzip".equalsIgnoreCase(encoding)) {//content 使用 gzip 压缩
            return ZipHelper.decompressForGzip(clone.readByteArray(), convertCharset(charset));//解压
        } else if ("zlib".equalsIgnoreCase(encoding)) {//content 使用 zlib 压缩
            return ZipHelper.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset));//解压
        } else {//content 没有被压缩, 或者使用其他未知压缩方式
            return clone.readString(charset);
        }
    }

    /**
     * 解析请求服务器的请求参数
     *
     * @param request {@link Request}
     * @return 解析后的请求信息
     * @throws UnsupportedEncodingException
     */
    public static String parseParams(Request request) {
        try {
            final RequestBody body = request.newBuilder().build().body();
            if (body == null) {
                return "";
            }
            final Buffer requestBuffer = new Buffer();
            body.writeTo(requestBuffer);
            Charset charset = Charset.forName("UTF-8");
            final MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            if (charset != null) {
                return CharacterHandler.jsonFormat(URLDecoder.decode(requestBuffer.readString(charset), convertCharset(charset)));
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    /**
     * 是否可以解析
     *
     * @param mediaType {@link MediaType}
     * @return {@code true} 为可以解析
     */
    public static boolean isParseable(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        return isText(mediaType) || isPlain(mediaType)
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType);
    }

    public static boolean isText(MediaType mediaType) {
        return mediaType != null && "text".equals(mediaType.type());
    }

    public static boolean isPlain(MediaType mediaType) {
        return mediaType != null && mediaType.subtype().toLowerCase().contains("plain");
    }

    public static boolean isJson(MediaType mediaType) {
        return mediaType != null && mediaType.subtype().toLowerCase().contains("json");
    }

    public static boolean isXml(MediaType mediaType) {
        return mediaType != null && mediaType.subtype().toLowerCase().contains("xml");
    }

    public static boolean isHtml(MediaType mediaType) {
        return mediaType != null && mediaType.subtype().toLowerCase().contains("html");
    }

    public static boolean isForm(MediaType mediaType) {
        return mediaType != null && mediaType.subtype().toLowerCase().contains("x-www-form-urlencoded");
    }

    public static String convertCharset(Charset charset) {
        final String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1) {
            return s;
        }
        return s.substring(i + 1, s.length() - 1);
    }
}
