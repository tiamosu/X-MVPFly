package com.xia.baseproject.rxhttp.subscriber;

import io.reactivex.disposables.Disposable;

/**
 * @author xia
 * @date 2018/7/27.
 */
public interface ISubscriber<T> {

    /**
     * doOnSubscribe 回调
     *
     * @param d Disposable
     */
    void doOnSubscribe(Disposable d);

    /**
     * 成功回调
     *
     * @param t 泛型
     */
    void doOnNext(T t);

    /**
     * 错误回调
     *
     * @param message 错误信息
     */
    void doOnError(String message);

    /**
     * 请求完成回调
     */
    void doOnCompleted();
}
