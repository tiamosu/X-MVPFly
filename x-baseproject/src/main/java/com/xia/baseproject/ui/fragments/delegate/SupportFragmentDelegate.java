package com.xia.baseproject.ui.fragments.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.NetworkUtils;
import com.xia.baseproject.R;
import com.xia.baseproject.app.Rest;
import com.xia.baseproject.constant.NetworkState;
import com.xia.baseproject.rxbus.RxBusHelper;
import com.xia.baseproject.ui.fragments.SupportFragment;
import com.xia.baseproject.utils.NetworkHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author xia
 * @date 2018/8/1.
 */
public class SupportFragmentDelegate {
    private SupportFragment mFragment;
    private Unbinder mUnbinder = null;

    //保证转场动画的流畅性
    private boolean mIsOnSupportVisible;
    private boolean mIsOnEnterAnimationEnd;

    //防止多次初始化
    private boolean mIsInitAll = true;
    //记录上一次网络连接状态
    private int mLastNetStatus = NetworkState.NETWORK_DEFAULT;
    //网络是否重新连接
    private boolean mNetReConnect;

    public SupportFragmentDelegate(SupportFragment fragment) {
        mFragment = fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        final View rootView = inflater.inflate(R.layout.base_layout_root_view, container, false);
        if (mFragment.isLoadHeadView()) {
            final FrameLayout titleBarContainer = rootView.findViewById(R.id.layout_root_view_head_container);
            mFragment.onCreateTitleBar(titleBarContainer);
        }
        if (mFragment.getLayoutId() > 0) {
            final FrameLayout contentContainer = rootView.findViewById(R.id.layout_root_view_content_container);
            View.inflate(mFragment.getContext(), mFragment.getLayoutId(), contentContainer);
        }
        mUnbinder = ButterKnife.bind(mFragment, rootView);
        return rootView;
    }

    public void onEnterAnimationEnd() {
        mIsOnEnterAnimationEnd = true;
        if (mIsOnSupportVisible) {
            initAll();
        }
    }

    public void onSupportVisible() {
        mIsOnSupportVisible = true;
        if (mIsOnEnterAnimationEnd) {
            initAll();
        }
    }

    public void onNewBundle(Bundle args) {
        getBundle(args);
    }

    public void onDestroy() {
        Rest.getHandler().removeCallbacksAndMessages(null);
        RxBusHelper.unregister(mFragment);
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    private void initAll() {
        if (NetworkHelper.isGlobalCheckNetwork(mFragment.isCheckNetWork())) {
            hasNetWork(NetworkUtils.isConnected());
        }
        if (mIsInitAll) {
            mIsInitAll = false;
            initNetworkChangeEvent();
            getBundle(mFragment.getArguments());
            mFragment.initData();
            mFragment.initView();
            mFragment.initEvent();
        }
        mFragment.onVisibleLazyLoad();
    }

    private void getBundle(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            mFragment.getBundleExtras(bundle);
        }
    }

    /**
     * 这边Callback不能使用lambda语句，否则解析报错
     */
    private void initNetworkChangeEvent() {
        if (NetworkHelper.isGlobalCheckNetwork(mFragment.isCheckNetWork())) {
            NetworkHelper.networkChangeEvent(mFragment, rxBusMessage -> {
                final boolean isAvailable = (boolean) rxBusMessage.mObj;
                hasNetWork(isAvailable);
            });
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
}
