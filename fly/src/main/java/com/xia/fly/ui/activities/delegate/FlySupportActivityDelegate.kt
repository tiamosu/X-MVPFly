package com.xia.fly.ui.activities.delegate

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.NetworkUtils
import com.xia.fly.constant.NetworkState
import com.xia.fly.integration.ConnectionLiveData
import com.xia.fly.integration.rxbus.RxBusEventTag
import com.xia.fly.integration.rxbus.RxBusHelper
import com.xia.fly.mvp.BaseMvpPresenter
import com.xia.fly.ui.activities.FlySupportActivity
import com.xia.fly.ui.activities.ProxyActivity
import com.xia.fly.utils.FlyUtils
import com.xia.fly.utils.Platform
import com.xia.flyrxbus.RxBusMessage

/**
 * @author weixia
 * @date 2019/2/25.
 */
class FlySupportActivityDelegate(private var mActivity: FlySupportActivity<*>) {
    //网络状态监听广播
    private var mConnectionLiveData: ConnectionLiveData? = null
    //记录上一次网络连接状态
    private var mLastNetStatus = NetworkState.NETWORK_DEFAULT
    //网络是否重新连接
    private var mNetReConnect: Boolean = false

    fun onCreate(savedInstanceState: Bundle?) {
        var proxyActivity: ProxyActivity<*>? = null
        if (mActivity is ProxyActivity) {
            proxyActivity = mActivity as ProxyActivity
            if (savedInstanceState != null && !proxyActivity.isRestartRestore()) {
                return
            }
        }
        if (mActivity.getLayoutId() > 0) {
            val rootView: View
            if (!mActivity.isLoadTitleBar()) {
                rootView = mActivity.layoutInflater.inflate(mActivity.getLayoutId(), null)
            } else {
                rootView = LinearLayout(mActivity)
                rootView.layoutParams = LinearLayout.LayoutParams(-1, -1)
                rootView.orientation = LinearLayout.VERTICAL

                //预留添加TitleBar
                val titleBarContainer = FrameLayout(mActivity)
                titleBarContainer.layoutParams = FrameLayout.LayoutParams(-1, -2)
                rootView.addView(titleBarContainer)
                mActivity.onCreateTitleBar(titleBarContainer)

                //加载页面布局（预留TitleBar之下）
                val contentContainer = FrameLayout(mActivity)
                contentContainer.layoutParams = FrameLayout.LayoutParams(-1, -1)
                rootView.addView(contentContainer)

                View.inflate(mActivity, mActivity.getLayoutId(), contentContainer)
            }
            mActivity.setContentView(rootView)
            mActivity.onBindAny(rootView)
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

    fun <P : BaseMvpPresenter<*>> onDestroy(presenter: P?) {
        RxBusHelper.unregister(mActivity)
        Platform.getHandler().removeCallbacksAndMessages(null)
        if (presenter != null) {
            presenter.detachView()
            mActivity.lifecycle.removeObserver(presenter)
        }
        if (mConnectionLiveData != null) {
            mConnectionLiveData!!.removeObservers(mActivity)
            mConnectionLiveData = null
        }
    }
}
