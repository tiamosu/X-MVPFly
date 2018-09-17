package com.xia.fly.integration.rxbus;

import com.blankj.rxbus.RxBusMessage;

/**
 * @author xia
 * @date 2018/8/13.
 */
public interface IRxBusCallback {

    void callback(String eventTag, RxBusMessage rxBusMessage);
}
