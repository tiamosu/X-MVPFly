package com.xia.fly.ui.fragments.delegate

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import butterknife.ButterKnife
import butterknife.Unbinder
import com.blankj.utilcode.util.NetworkUtils
import com.xia.fly.constant.NetworkState
import com.xia.fly.integration.rxbus.IRxBusCallback
import com.xia.fly.integration.rxbus.RxBusEventTag
import com.xia.fly.integration.rxbus.RxBusHelper
import com.xia.fly.ui.fragments.SupportFragment
import com.xia.fly.utils.Platform
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author weixia
 * @date 2019/2/25.
 */
class SupportFragmentDelegate(private var mFragment: SupportFragment<*>) {
    private var mUnbinder: Unbinder? = null
    //防止多次初始化
    private val mInitialized = AtomicBoolean(false)
    //记录上一次网络连接状态
    private var mLastNetStatus = NetworkState.NETWORK_DEFAULT
    //网络是否重新连接
    private var mNetReConnect: Boolean = false
    //保证转场动画的流畅性
    private var mIsOnSupportVisible: Boolean = false
    private var mIsOnEnterAnimationEnd: Boolean = false

    fun onCreateView(): View? {
        if (mFragment.getLayoutId() == 0) {
            return null
        }

        val rootView = LinearLayout(mFragment.context)
        rootView.layoutParams = LinearLayout.LayoutParams(-1, -1)
        rootView.orientation = LinearLayout.VERTICAL

        //预留添加TitleBar
        val titleBarContainer = FrameLayout(mFragment.context)
        titleBarContainer.layoutParams = FrameLayout.LayoutParams(-1, -2)
        rootView.addView(titleBarContainer)
        mFragment.onCreateTitleBar(titleBarContainer)

        //加载页面布局（预留TitleBar之下）
        val contentContainer = FrameLayout(mFragment.context)
        contentContainer.layoutParams = FrameLayout.LayoutParams(-1, -1)
        rootView.addView(contentContainer)
        View.inflate(mFragment.context, mFragment.getLayoutId(), contentContainer)
        mUnbinder = ButterKnife.bind(mFragment, rootView)
        return rootView
    }

    fun onEnterAnimationEnd() {
        mIsOnEnterAnimationEnd = true
        if (mIsOnSupportVisible) {
            onVisibleLazyInit()
        }
    }

    fun onSupportVisible() {
        mIsOnSupportVisible = true
        if (mIsOnEnterAnimationEnd) {
            onVisibleLazyInit()
        }
    }

    private fun onVisibleLazyInit() {
        if (mInitialized.compareAndSet(false, true)) {
            mFragment.initMvp()
            getBundle(mFragment.arguments)
            mFragment.initData()
            mFragment.initView()
            mFragment.initEvent()
            mFragment.onLazyLoadData()

            checkNetEvent()
        }
        if (mFragment.isCheckNetWork()) {
            hasNetWork(NetworkUtils.isConnected())
        }
        mFragment.onVisibleLazyLoad()
    }

    fun getBundle(bundle: Bundle?) {
        if (bundle?.isEmpty == false) {
            mFragment.getBundleExtras(bundle)
        }
    }

    private fun checkNetEvent() {
        if (mFragment.isCheckNetWork()) {
            RxBusHelper.subscribeWithTags(mFragment, IRxBusCallback { eventTag, rxBusMessage ->
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
            if (mFragment.isSupportVisible) {
                mFragment.onNetworkState(isAvailable)
                if (isAvailable && mNetReConnect) {
                    mFragment.onNetReConnect()
                    mNetReConnect = false
                }
                mLastNetStatus = currentNetStatus
            }
        }
    }

    fun onDestroy() {
        RxBusHelper.unregister(mFragment)
        Platform.getHandler().removeCallbacksAndMessages(null)
        if (mUnbinder != null && mUnbinder !== Unbinder.EMPTY) {
            try {
                //fix Bindings already cleared
                mUnbinder!!.unbind()
            } catch (ignored: IllegalStateException) {
            }
            mUnbinder = null
        }
    }
}
