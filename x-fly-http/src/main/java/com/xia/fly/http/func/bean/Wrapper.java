package com.xia.fly.http.func.bean;

/**
 * @author xia
 * @date 2018/9/5.
 */
public class Wrapper {
    private int index;
    private Throwable throwable;

    public int getIndex() {
        return index;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public Wrapper(Throwable throwable, int index) {
        this.index = index;
        this.throwable = throwable;
    }
}
