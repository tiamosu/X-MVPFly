package com.xia.baseproject.rxhttp.exception;

/**
 * @author xia
 * @date 2018/7/27.
 */
public class Exceptions {

    public static void illegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }
}
