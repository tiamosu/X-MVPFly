package com.xia.baseproject.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.blankj.rxbus.RxBusMessage;
import com.blankj.utilcode.util.NetworkUtils;
import com.xia.baseproject.integration.rxbus.RxBusEventTag;
import com.xia.baseproject.integration.rxbus.RxBusHelper;

/**
 * @author xia
 * @date 2018/7/31.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //**判断当前的网络连接状态是否可用*/
        final boolean isAvailable = NetworkUtils.isConnected();
        RxBusHelper.post(new RxBusMessage(isAvailable), RxBusEventTag.NETWORK_CHANGE);
    }

    public static NetworkChangeReceiver register(Activity activity) {
        final NetworkChangeReceiver receiver = new NetworkChangeReceiver();
        final IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        activity.registerReceiver(receiver, filter);
        return receiver;
    }
}
