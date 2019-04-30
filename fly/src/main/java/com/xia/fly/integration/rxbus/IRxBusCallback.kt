package com.xia.fly.integration.rxbus

import com.xia.flyrxbus.RxBusMessage

/**
 * @author xia
 * @date 2018/8/13.
 */
interface IRxBusCallback {

    fun callback(eventTag: String, rxBusMessage: RxBusMessage)
}
