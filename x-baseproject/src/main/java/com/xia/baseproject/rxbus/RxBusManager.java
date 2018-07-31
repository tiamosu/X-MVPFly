package com.xia.baseproject.rxbus;

import com.blankj.rxbus.RxBus;

/**
 * @author xia
 * @date 2018/7/31.
 */
public final class RxBusManager {

    public static void post(final Object event) {
        RxBus.getDefault().post(event);
    }

    public static void post(final Object event, final String tag) {
        RxBus.getDefault().post(event, tag);
    }

    public static void postSticky(final Object event) {
        RxBus.getDefault().postSticky(event);
    }

    public static void postSticky(final Object event, final String tag) {
        RxBus.getDefault().postSticky(event, tag);
    }

    public static void unregister(final Object subscriber) {
        RxBus.getDefault().unregister(subscriber);
    }

    public static <T> void subscribe(final Object subscriber, final RxBus.Callback<T> callback) {
        RxBus.getDefault().subscribe(subscriber, callback);
    }

    public static <T> void subscribe(final Object subscriber,
                                     final String tag,
                                     final RxBus.Callback<T> callback) {
        RxBus.getDefault().subscribe(subscriber, tag, callback);
    }

    public static <T> void subscribeSticky(final Object subscriber, final RxBus.Callback<T> callback) {
        RxBus.getDefault().subscribeSticky(subscriber, callback);
    }

    public static <T> void subscribeSticky(final Object subscriber,
                                           final String tag,
                                           final RxBus.Callback<T> callback) {
        RxBus.getDefault().subscribeSticky(subscriber, tag, callback);
    }
}
