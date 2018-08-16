package com.xia.baseproject.utils;

import com.blankj.rxbus.RxBusMessage;
import com.xia.baseproject.rxbus.RxBusEventTag;
import com.xia.baseproject.rxbus.RxBusHelper;

/**
 * @author xia
 * @date 2018/8/16.
 */
public final class NetworkHelper {

    public static void networkChangeEvent(Object subscriber, NetworkChangeCallback callback) {
        RxBusHelper.subscribeWithTags(subscriber, (eventTag, rxBusMessage) -> {
            if (eventTag.equals(RxBusEventTag.NETWORK_CHANGE)) {
                if (callback != null) {
                    callback.callback(rxBusMessage);
                }
            }
        }, RxBusEventTag.NETWORK_CHANGE);
    }

    public interface NetworkChangeCallback {
        void callback(RxBusMessage rxBusMessage);
    }
}
