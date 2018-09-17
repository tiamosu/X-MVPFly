package com.xia.fly.rxhttp.body.upload;

import java.io.Serializable;

/**
 * @author xia
 * @date 2018/9/5.
 */
public class ProgressModel implements Serializable {
    //当前读取字节长度
    private long currentBytes;
    //总字节长度
    private long contentLength;
    //是否读取完成
    private boolean done;

    ProgressModel(long currentBytes, long contentLength, boolean done) {
        this.currentBytes = currentBytes;
        this.contentLength = contentLength;
        this.done = done;
    }

    public long getCurrentBytes() {
        return currentBytes;
    }

    public ProgressModel setCurrentBytes(long currentBytes) {
        this.currentBytes = currentBytes;
        return this;
    }

    public long getContentLength() {
        return contentLength;
    }

    public ProgressModel setContentLength(long contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public boolean isDone() {
        return done;
    }

    public ProgressModel setDone(boolean done) {
        this.done = done;
        return this;
    }

    @Override
    public String toString() {
        return "ProgressModel{" +
                "currentBytes=" + currentBytes +
                ", contentLength=" + contentLength +
                ", done=" + done +
                '}';
    }
}
