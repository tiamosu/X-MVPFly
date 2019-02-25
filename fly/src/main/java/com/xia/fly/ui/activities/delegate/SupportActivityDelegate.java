package com.xia.fly.ui.activities.delegate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.blankj.rxbus.RxBusMessage;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.xia.fly.constant.NetworkState;
import com.xia.fly.integration.rxbus.IRxBusCallback;
import com.xia.fly.integration.rxbus.RxBusEventTag;
import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.receiver.NetworkChangeReceiver;
import com.xia.fly.ui.activities.ProxyActivity;
import com.xia.fly.ui.activities.SupportActivity;
import com.xia.fly.ui.dialog.loader.Loader;
import com.xia.fly.utils.Platform;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author weixia
 * @date 2019/2/25.
 */
public final class SupportActivityDelegate {
    private SupportActivity mActivity;
    private Unbinder mUnbinder;

    //网络状态监听广播
    private NetworkChangeReceiver mNetworkChangeReceiver;
    //记录上一次网络连接状态
    private int mLastNetStatus = NetworkState.NETWORK_DEFAULT;
    //网络是否重新连接
    private boolean mNetReConnect;

    public SupportActivityDelegate(SupportActivity activity) {
        mActivity = activity;
    }

    public void onCreate(Bundle savedInstanceState) {
        ProxyActivity proxyActivity = null;
        if (mActivity instanceof ProxyActivity) {
            proxyActivity = (ProxyActivity) mActivity;
            if (savedInstanceState != null && !proxyActivity.isRestartRestore()) {
                return;
            }
        }
        if (mActivity.getLayoutId() != 0) {
            final View rootView = mActivity.getLayoutInflater().inflate(mActivity.getLayoutId(), null);
            mActivity.setContentView(rootView);
            mUnbinder = ButterKnife.bind(mActivity, rootView);
        }
        //Activity为ProxyActivity时，下面方法置于ProxyActivity中执行
        if (proxyActivity == null) {
            mActivity.initMvp();
            mActivity.initData();
            mActivity.initView();
            mActivity.initEvent();
            mActivity.onLazyLoadData();
        }

        checkNetEvent();
    }

    private void checkNetEvent() {
        if (mActivity.isCheckNetWork()) {
            mNetworkChangeReceiver = NetworkChangeReceiver.register(mActivity);
            RxBusHelper.subscribeWithTags(mActivity, new IRxBusCallback() {
                @Override
                public void callback(String eventTag, RxBusMessage rxBusMessage) {
                    if (eventTag.equals(RxBusEventTag.NETWORK_CHANGE)) {
                        final boolean isAvailable = (boolean) rxBusMessage.mObj;
                        hasNetWork(isAvailable);
                    }
                }
            }, RxBusEventTag.NETWORK_CHANGE);
        }
    }

    private void hasNetWork(boolean isAvailable) {
        final int currentNetStatus = isAvailable ? NetworkState.NETWORK_ON : NetworkState.NETWORK_OFF;
        if (currentNetStatus != mLastNetStatus || mNetReConnect) {
            //判断网络是否是重连接的
            if (isAvailable && mLastNetStatus == NetworkState.NETWORK_OFF) {
                mNetReConnect = true;
            }
            //APP位于前台并且当前页处于栈顶（可见）时执行
            if (AppUtils.isAppForeground()
                    && ActivityUtils.getTopActivity() == mActivity) {
                mActivity.onNetworkState(isAvailable);
                if (isAvailable && mNetReConnect) {
                    mActivity.onNetReConnect();
                    mNetReConnect = false;
                }
                mLastNetStatus = currentNetStatus;
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void onResume() {
        if (mActivity.isCheckNetWork()) {
            hasNetWork(NetworkUtils.isConnected());
        }
    }

    public void onDestroy() {
        RxBusHelper.unregister(mActivity);
        Platform.getHandler().removeCallbacksAndMessages(null);
        Platform.getLoadingHandler().removeCallbacksAndMessages(null);
        Loader.stopLoading();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        if (mNetworkChangeReceiver != null) {
            mActivity.unregisterReceiver(mNetworkChangeReceiver);
            mNetworkChangeReceiver = null;
        }
        this.mActivity = null;
    }
}
