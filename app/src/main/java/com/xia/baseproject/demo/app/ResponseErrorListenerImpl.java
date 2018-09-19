package com.xia.baseproject.demo.app;

import android.content.Context;
import android.net.ParseException;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import retrofit2.HttpException;

/**
 * @author xia
 * @date 2018/9/19.
 */
public class ResponseErrorListenerImpl implements ResponseErrorListener {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    @Override
    public void handleResponseError(Context context, Throwable t) {
        //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
        //这里只是对几个常用错误进行简单的处理, 展示这个类的用法, 在实际开发中请您自行对更多错误进行更严谨的处理
        String msg = "未知错误";
        if (t instanceof HttpException) {
            final HttpException httpException = (HttpException) t;
            msg = convertStatusCode(httpException);
        } else if (t instanceof JsonSyntaxException
                || t instanceof JSONException
                || t instanceof JsonParseException
                || t instanceof ParseException
                || t instanceof NotSerializableException) {
            msg = "数据解析错误";
        } else if (t instanceof ClassCastException) {
            msg = "类型转换错误";
        } else if (t instanceof ConnectException) {
            msg = "无法连接网络";
        } else if (t instanceof ConnectTimeoutException) {
            msg = "网络连接超时";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof SSLHandshakeException) {
            msg = "证书验证失败";
        } else if (t instanceof UnknownHostException) {
            msg = "无法解析该域名";
        } else if (t instanceof NullPointerException) {
            msg = "空指针异常";
        } else if (t instanceof RuntimeException) {
            final RuntimeException runtimeException = (RuntimeException) t;
            msg = runtimeException.getMessage();
        }
        ToastUtils.showShort(msg);
    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        switch (httpException.code()) {
            case UNAUTHORIZED:
            case FORBIDDEN:
            case NOT_FOUND:
            case REQUEST_TIMEOUT:
            case GATEWAY_TIMEOUT:
            case INTERNAL_SERVER_ERROR:
            case BAD_GATEWAY:
            case SERVICE_UNAVAILABLE:
            default:
                msg = "连接服务器超时";
        }
        return msg;
    }
}
