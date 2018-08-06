package com.xia.baseproject.ui.fragments.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blankj.rxbus.RxBus;
import com.blankj.rxbus.RxBusManager;
import com.blankj.rxbus.RxBusMessage;
import com.blankj.utilcode.util.NetworkUtils;
import com.xia.baseproject.R;
import com.xia.baseproject.app.Rest;
import com.xia.baseproject.app.RestConfigKeys;
import com.xia.baseproject.rxbus.NetworkChangeEvent;
import com.xia.baseproject.ui.fragments.SupportFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;

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
    private int mLastNetStatus = -1;

    //网络是否重新连接
    private boolean mNetReConnect;

    public SupportFragmentDelegate(ISupportFragment support) {
        if (!(support instanceof Fragment)) {
            throw new RuntimeException("Must extends Fragment");
        }
        this.mFragment = (SupportFragment) support;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        final View rootView = inflater.inflate(R.layout.base_layout_root_view, container, false);
        if (mFragment.isLoadHeadView()) {
            final FrameLayout headContainer = rootView.findViewById(R.id.layout_root_view_head_container);
            mFragment.onCreateHeadView(headContainer);
        }
        if (mFragment.getLayoutId() > 0) {
            final FrameLayout contentContainer = rootView.findViewById(R.id.layout_root_view_content_container);
            View.inflate(mFragment.getContext(), mFragment.getLayoutId(), contentContainer);
        }
        mUnbinder = ButterKnife.bind(this, rootView);
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
        RxBusManager.unregister(mFragment);
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    private void initAll() {
        if (isGlobalCheckNetWork()) {
            hasNetWork(NetworkUtils.isAvailableByPing());
        }
        if (!mIsInitAll && mNetReConnect) {
            mFragment.reConnect();
        }
        if (mIsInitAll) {
            mIsInitAll = false;
            registerNetCheckEvent();
            getBundle(mFragment.getArguments());
            mFragment.initData();
            mFragment.initView();
            mFragment.initEvent();
            mFragment.initRxBusEvent();
        }
        mFragment.onVisibleLazyLoad();
    }

    private void getBundle(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            mFragment.getBundleExtras(bundle);
        }
    }

    private boolean isGlobalCheckNetWork() {
        final boolean isCheckNetWork = Rest.getConfiguration(RestConfigKeys.NETWORK_CHECK);
        return isCheckNetWork && mFragment.isCheckNetWork();
    }

    /**
     * 这边Callback不能使用lambda语句，否则解析报错
     */
    @SuppressWarnings("Convert2Lambda")
    private void registerNetCheckEvent() {
        if (isGlobalCheckNetWork()) {
            RxBusManager.subscribe(mFragment, NetworkChangeEvent.NET_CHANGE_TAG,
                    new RxBus.Callback<NetworkChangeEvent>() {
                        @Override
                        public void onEvent(String tag, NetworkChangeEvent event) {
                            hasNetWork(event.isAvailable);
                        }
                    });
        }
    }

    private void hasNetWork(boolean isAvailable) {
        final int currentNetStatus = isAvailable ? 1 : 0;
        if (currentNetStatus != mLastNetStatus) {
            mLastNetStatus = currentNetStatus;
            //当网络重新连接时并且只有当前页面才执行方法
            if (isAvailable && mFragment.isSupportVisible()) {
                mNetReConnect = true;
                mFragment.reConnect();
            }
            Log.e("Net", "网络是否连接: " + isAvailable);
        }
    }

    @SuppressWarnings("Convert2Lambda")
    public void subscribeWithTags(final String... tags) {
        RxBusManager.subscribeWithTags(mFragment, new RxBus.Callback<RxBusMessage>() {
            @Override
            public void onEvent(String tag, RxBusMessage rxBusMessage) {
                mFragment.handleRxBusMessage(tag, rxBusMessage);
            }
        }, tags);
    }

    @SuppressWarnings("Convert2Lambda")
    public void subscribeStickyWithTags(final String... tags) {
        RxBusManager.subscribeStickyWithTags(mFragment, new RxBus.Callback<RxBusMessage>() {
            @Override
            public void onEvent(String tag, RxBusMessage rxBusMessage) {
                mFragment.handleRxBusMessage(tag, rxBusMessage);
            }
        }, tags);
    }
}
