package com.xia.fly.integration.rxbus

import com.xia.flyrxbus.RxBus
import com.xia.flyrxbus.RxBusManager
import com.xia.flyrxbus.RxBusMessage

/**
 * @author xia
 * @date 2018/8/13.
 */
@Suppress("unused")
object RxBusHelper {

    @JvmStatic
    fun post(tag: String) {
        post(RxBusMessage(""), tag)
    }

    @JvmStatic
    fun post(event: RxBusMessage, tag: String) {
        RxBusManager.post(event, tag)
    }

    @JvmStatic
    fun postSticky(tag: String) {
        postSticky(RxBusMessage(""), tag)
    }

    @JvmStatic
    fun postSticky(event: RxBusMessage, tag: String) {
        RxBusManager.postSticky(event, tag)
    }

    @JvmStatic
    fun removeSticky(event: Any) {
        RxBusManager.removeSticky(event)
    }

    @JvmStatic
    fun removeSticky(event: Any, tag: String) {
        RxBusManager.removeSticky(event, tag)
    }

    @JvmStatic
    fun subscribeWithTags(subscriber: Any,
                          callback: IRxBusCallback?,
                          vararg tags: String) {
        RxBusManager.subscribeWithTags(subscriber, object : RxBus.Callback<RxBusMessage>() {
            override fun onEvent(tag: String, t: RxBusMessage) {
                callback?.callback(tag, t)
            }
        }, *tags)
    }

    @JvmStatic
    fun subscribeStickyWithTags(subscriber: Any,
                                callback: IRxBusCallback?,
                                vararg tags: String) {
        RxBusManager.subscribeStickyWithTags(subscriber, object : RxBus.Callback<RxBusMessage>() {
            override fun onEvent(tag: String, t: RxBusMessage) {
                callback?.callback(tag, t)
            }
        }, *tags)
    }

    @JvmStatic
    fun unregister(subscriber: Any?) {
        RxBusManager.unregister(subscriber)
    }
}
