package com.xia.baseproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.rxbus.RxBusMessage;
import com.blankj.utilcode.util.NetworkUtils;
import com.xia.baseproject.rxbus.RxBusEventTag;
import com.xia.baseproject.rxbus.RxBusHelper;

/**
 * @author xia
 * @date 2018/7/31.
 */
public class NetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //**判断当前的网络连接状态是否可用*/
        final boolean isAvailable = NetworkUtils.isConnected();
        RxBusHelper.post(new RxBusMessage(isAvailable), RxBusEventTag.NETWORK_CHANGE);
    }
}
