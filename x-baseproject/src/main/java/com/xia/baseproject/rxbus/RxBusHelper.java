package com.xia.baseproject.rxbus;

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
}
