package com.xia.fly.rxhttp.exception;

/**
 * @author xia
 * @date 2018/8/6.
 */
public class ServerException extends RuntimeException {
    private int errCode;
    private String message;

    public ServerException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message == null ? "" : message;
    }
}
