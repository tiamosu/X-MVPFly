package com.xia.fly.ui.fragments.delegate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.blankj.rxbus.RxBusMessage;
import com.blankj.utilcode.util.NetworkUtils;
import com.xia.fly.constant.NetworkState;
import com.xia.fly.integration.rxbus.IRxBusCallback;
import com.xia.fly.integration.rxbus.RxBusEventTag;
import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.ui.dialog.loader.Loader;
import com.xia.fly.ui.fragments.SupportFragment;
import com.xia.fly.utils.Platform;

import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author weixia
 * @date 2019/2/25.
 */
public final class SupportFragmentDelegate {
    private SupportFragment mFragment;
    private Unbinder mUnbinder;

    //防止多次初始化
    private final AtomicBoolean mInitialized = new AtomicBoolean(false);
    //记录上一次网络连接状态
    private int mLastNetStatus = NetworkState.NETWORK_DEFAULT;
    //网络是否重新连接
    private boolean mNetReConnect;
    //保证转场动画的流畅性
    private boolean mIsOnSupportVisible;
    private boolean mIsOnEnterAnimationEnd;

    public SupportFragmentDelegate(SupportFragment fragment) {
        mFragment = fragment;
    }

    public View onCreateView() {
        if (mFragment.getLayoutId() == 0) {
            return null;
        }

        final LinearLayout rootView = new LinearLayout(mFragment.getContext());
        rootView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        rootView.setOrientation(LinearLayout.VERTICAL);

        //预留添加TitleBar
        final FrameLayout titleBarContainer = new FrameLayout(mFragment.getContext());
        titleBarContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        rootView.addView(titleBarContainer);
        mFragment.onCreateTitleBar(titleBarContainer);

        //加载页面布局（预留TitleBar之下）
        final FrameLayout contentContainer = new FrameLayout(mFragment.getContext());
        contentContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        rootView.addView(contentContainer);
        View.inflate(mFragment.getContext(), mFragment.getLayoutId(), contentContainer);
        mUnbinder = ButterKnife.bind(mFragment, rootView);
        return rootView;
    }

    public void onEnterAnimationEnd() {
        mIsOnEnterAnimationEnd = true;
        if (mIsOnSupportVisible) {
            onVisibleLazyInit();
        }
    }

    public void onSupportVisible() {
        mIsOnSupportVisible = true;
        if (mIsOnEnterAnimationEnd) {
            onVisibleLazyInit();
        }
    }

    @SuppressLint("MissingPermission")
    private void onVisibleLazyInit() {
        if (mInitialized.compareAndSet(false, true)) {
            mFragment.initMvp();
            getBundle(mFragment.getArguments());
            mFragment.initData();
            mFragment.initView();
            mFragment.initEvent();
            mFragment.onLazyLoadData();

            checkNetEvent();
        }
        if (mFragment.isCheckNetWork()) {
            hasNetWork(NetworkUtils.isConnected());
        }
        mFragment.onVisibleLazyLoad();
    }

    public void getBundle(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            mFragment.getBundleExtras(bundle);
        }
    }

    private void checkNetEvent() {
        if (mFragment.isCheckNetWork()) {
            RxBusHelper.subscribeWithTags(mFragment, new IRxBusCallback() {
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
            if (mFragment.isSupportVisible()) {
                mFragment.onNetworkState(isAvailable);
                if (isAvailable && mNetReConnect) {
                    mFragment.onNetReConnect();
                    mNetReConnect = false;
                }
                mLastNetStatus = currentNetStatus;
            }
        }
    }

    public void onDestroy() {
        RxBusHelper.unregister(mFragment);
        Platform.getHandler().removeCallbacksAndMessages(null);
        Platform.getLoadingHandler().removeCallbacksAndMessages(null);
        Loader.stopLoading();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            try {
                //fix Bindings already cleared
                mUnbinder.unbind();
            } catch (IllegalStateException ignored) {
            }
            mUnbinder = null;
        }
        this.mFragment = null;
    }
}
