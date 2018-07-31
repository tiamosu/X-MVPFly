package com.xia.baseproject.rxbus.event;

/**
 * @author xia
 * @date 2018/7/31.
 */
@SuppressWarnings("WeakerAccess")
public class NetworkChangeEvent {
    //是否存在网络
    public boolean isAvailable;

    public NetworkChangeEvent(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
