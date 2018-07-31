package com.xia.baseproject.rxbus;

import com.xia.baseproject.rxbus.event.NetworkChangeEvent;

/**
 * @author xia
 * @date 2018/7/31.
 */
@SuppressWarnings("WeakerAccess")
public final class RxBusHelper {
    public static final String NET_CHANGE_TAG = "NET_CHANGE_TAG";

    public static void globalNetChange(final NetworkChangeEvent event) {
        RxBusManager.post(event, NET_CHANGE_TAG);
    }
}
