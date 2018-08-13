package com.xia.baseproject.rxbus;

import com.blankj.rxbus.RxBus;
import com.blankj.rxbus.RxBusManager;
import com.blankj.rxbus.RxBusMessage;

/**
 * @author xia
 * @date 2018/8/13.
 */
@SuppressWarnings("WeakerAccess")
public final class RxBusHelper {

    public static void post(final String tag) {
        post(new RxBusMessage(""), tag);
    }

    public static void post(final RxBusMessage event, final String tag) {
        RxBusManager.post(event, tag);
    }

    public static void postSticky(final String tag) {
        postSticky(new RxBusMessage(""), tag);
    }

    public static void postSticky(final RxBusMessage event, final String tag) {
        RxBusManager.postSticky(event, tag);
    }

    @SuppressWarnings("Convert2Lambda")
    public static void subscribeWithTags(final Object subscriber,
                                         final IRxBusCallback callback,
                                         final String... tags) {
        RxBusManager.subscribeWithTags(subscriber, new RxBus.Callback<RxBusMessage>() {
            @Override
            public void onEvent(String tag, RxBusMessage rxBusMessage) {
                if (callback != null) {
                    callback.callback(tag, rxBusMessage);
                }
            }
        }, tags);
    }

    @SuppressWarnings("Convert2Lambda")
    public static void subscribeStickyWithTags(final Object subscriber,
                                               final IRxBusCallback callback,
                                               final String... tags) {
        RxBusManager.subscribeStickyWithTags(subscriber, new RxBus.Callback<RxBusMessage>() {
            @Override
            public void onEvent(String tag, RxBusMessage rxBusMessage) {
                if (callback != null) {
                    callback.callback(tag, rxBusMessage);
                }
            }
        }, tags);
    }
}
