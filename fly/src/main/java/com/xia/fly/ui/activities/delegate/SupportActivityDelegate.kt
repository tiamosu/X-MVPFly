package com.xia.fly.ui.activities.delegate

import android.os.Bundle
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import butterknife.Unbinder
import com.blankj.rxbus.RxBusMessage
import com.blankj.utilcode.util.NetworkUtils
import com.xia.fly.constant.NetworkState
import com.xia.fly.integration.rxbus.RxBusEventTag
import com.xia.fly.integration.rxbus.RxBusHelper
import com.xia.fly.integration.eventbus.ConnectionLiveData
import com.xia.fly.ui.activities.ProxyActivity
import com.xia.fly.ui.activities.SupportActivity
import com.xia.fly.utils.FlyUtils
import com.xia.fly.utils.Platform

/**
 * @author weixia
 * @date 2019/2/25.
 */
class SupportActivityDelegate(private var mActivity: SupportActivity<*>) {
    private var mUnbinder: Unbinder? = null
    //网络状态监听广播
    private var mConnectionLiveData: ConnectionLiveData? = null
    //记录上一次网络连接状态
    private var mLastNetStatus = NetworkState.NETWORK_DEFAULT
    //网络是否重新连接
    private var mNetReConnect: Boolean = false

    fun onCreate(savedInstanceState: Bundle?) {
        var proxyActivity: ProxyActivity? = null
        if (mActivity is ProxyActivity) {
            proxyActivity = mActivity as ProxyActivity
            if (savedInstanceState != null && !proxyActivity.isRestartRestore) {
                return
            }
        }
        if (mActivity.getLayoutId() != 0) {
            val rootView = mActivity.layoutInflater.inflate(mActivity.getLayoutId(), null)
            mActivity.setContentView(rootView)
            mUnbinder = ButterKnife.bind(mActivity, rootView)
        }
        //Activity为ProxyActivity时，下面方法置于ProxyActivity中执行
        if (proxyActivity == null) {
            mActivity.initMvp()
            mActivity.initData()
            mActivity.initView()
            mActivity.initEvent()
            mActivity.onLazyLoadData()
        }

        checkNetEvent()
    }

    private fun checkNetEvent() {
        if (mActivity.isCheckNetWork()) {
            mConnectionLiveData = ConnectionLiveData(mActivity)
            mConnectionLiveData!!.observe(mActivity, Observer { isAvailable ->
                isAvailable?.let {
                    //Fragment保持同步通信
                    RxBusHelper.post(RxBusMessage(isAvailable), RxBusEventTag.NETWORK_CHANGE)
                    hasNetWork(isAvailable)
                }
            })
        }
    }

    private fun hasNetWork(isAvailable: Boolean) {
        val currentNetStatus = if (isAvailable) NetworkState.NETWORK_ON else NetworkState.NETWORK_OFF
        if (currentNetStatus != mLastNetStatus || mNetReConnect) {
            //判断网络是否是重连接的
            if (isAvailable && mLastNetStatus == NetworkState.NETWORK_OFF) {
                mNetReConnect = true
            }
            //APP位于前台并且当前页处于栈顶（可见）时执行
            if (FlyUtils.isCurrentVisible(mActivity)) {
                mActivity.onNetworkState(isAvailable)
                if (isAvailable && mNetReConnect) {
                    mActivity.onNetReConnect()
                    mNetReConnect = false
                }
                mLastNetStatus = currentNetStatus
            }
        }
    }

    fun onResume() {
        if (mActivity.isCheckNetWork()) {
            hasNetWork(NetworkUtils.isConnected())
        }
    }

    fun onDestroy() {
        RxBusHelper.unregister(mActivity)
        Platform.getHandler().removeCallbacksAndMessages(null)
        if (mUnbinder != null && mUnbinder !== Unbinder.EMPTY) {
            mUnbinder!!.unbind()
            mUnbinder = null
        }
        if (mConnectionLiveData != null) {
            mConnectionLiveData!!.removeObservers(mActivity)
            mConnectionLiveData = null
        }
    }
}
