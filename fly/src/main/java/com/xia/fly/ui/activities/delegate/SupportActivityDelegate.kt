package com.xia.fly.ui.activities.delegate

import android.annotation.SuppressLint
import android.os.Bundle
import butterknife.ButterKnife
import butterknife.Unbinder
import com.blankj.utilcode.util.NetworkUtils
import com.xia.fly.constant.NetworkState
import com.xia.fly.integration.rxbus.IRxBusCallback
import com.xia.fly.integration.rxbus.RxBusEventTag
import com.xia.fly.integration.rxbus.RxBusHelper
import com.xia.fly.receiver.NetworkChangeReceiver
import com.xia.fly.ui.activities.ProxyActivity
import com.xia.fly.ui.activities.SupportActivity
import com.xia.fly.utils.FlyUtils
import com.xia.fly.utils.Platform

/**
 * @author weixia
 * @date 2019/2/25.
 */
class SupportActivityDelegate(private var mActivity: SupportActivity<*>?) {
    private var mUnbinder: Unbinder? = null

    //网络状态监听广播
    private var mNetworkChangeReceiver: NetworkChangeReceiver? = null
    //记录上一次网络连接状态
    private var mLastNetStatus = NetworkState.NETWORK_DEFAULT
    //网络是否重新连接
    private var mNetReConnect: Boolean = false

    fun onCreate(savedInstanceState: Bundle?) {
        var proxyActivity: ProxyActivity? = null
        if (mActivity is ProxyActivity) {
            proxyActivity = mActivity as ProxyActivity?
            if (savedInstanceState != null && !proxyActivity!!.isRestartRestore) {
                return
            }
        }
        if (mActivity!!.layoutId != 0) {
            val rootView = mActivity!!.layoutInflater.inflate(mActivity!!.layoutId, null)
            mActivity!!.setContentView(rootView)
            mUnbinder = ButterKnife.bind(mActivity!!, rootView)
        }
        //Activity为ProxyActivity时，下面方法置于ProxyActivity中执行
        if (proxyActivity == null) {
            mActivity!!.initMvp()
            mActivity!!.initData()
            mActivity!!.initView()
            mActivity!!.initEvent()
            mActivity!!.onLazyLoadData()
        }

        checkNetEvent()
    }

    private fun checkNetEvent() {
        if (mActivity!!.isCheckNetWork) {
            mNetworkChangeReceiver = NetworkChangeReceiver.register(mActivity!!)
            RxBusHelper.subscribeWithTags(mActivity, IRxBusCallback { eventTag, rxBusMessage ->
                if (eventTag == RxBusEventTag.NETWORK_CHANGE) {
                    val isAvailable = rxBusMessage.mObj as Boolean
                    hasNetWork(isAvailable)
                }
            }, RxBusEventTag.NETWORK_CHANGE)
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
            if (FlyUtils.isCurrentVisible(mActivity!!)) {
                mActivity!!.onNetworkState(isAvailable)
                if (isAvailable && mNetReConnect) {
                    mActivity!!.onNetReConnect()
                    mNetReConnect = false
                }
                mLastNetStatus = currentNetStatus
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun onResume() {
        if (mActivity!!.isCheckNetWork) {
            hasNetWork(NetworkUtils.isConnected())
        }
    }

    fun onDestroy() {
        RxBusHelper.unregister(mActivity)
        Platform.handler.removeCallbacksAndMessages(null)
        if (mUnbinder != null && mUnbinder !== Unbinder.EMPTY) {
            mUnbinder!!.unbind()
            mUnbinder = null
        }
        if (mNetworkChangeReceiver != null) {
            mActivity!!.unregisterReceiver(mNetworkChangeReceiver)
            mNetworkChangeReceiver = null
        }
        this.mActivity = null
    }
}
