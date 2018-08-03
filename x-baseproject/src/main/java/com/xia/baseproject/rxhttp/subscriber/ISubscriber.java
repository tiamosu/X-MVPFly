package com.xia.baseproject.rxhttp.subscriber;

import io.reactivex.disposables.Disposable;

/**
 * @author xia
 * @date 2018/8/3.
 */
public interface ISubscriber<T> {

    /**
     * doOnSubscribe 回调
     *
     * @param disposable Disposable
     */
    void doOnSubscribe(Disposable disposable);

    /**
     * 成功回调
     *
     * @param t 泛型
     */
    void doOnNext(T t);

    /**
     * 错误回调
     *
     * @param error 错误信息
     */
    void doOnError(String error);

    /**
     * 请求完成回调
     */
    void doonComplete();
}
