package com.xia.baseproject.ui.fragments.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blankj.rxbus.RxBus;
import com.blankj.rxbus.RxBusManager;
import com.blankj.utilcode.util.NetworkUtils;
import com.xia.baseproject.R;
import com.xia.baseproject.app.Rest;
import com.xia.baseproject.app.RestConfigKeys;
import com.xia.baseproject.handler.WeakHandler;
import com.xia.baseproject.rxbus.event.NetworkChangeEvent;
import com.xia.baseproject.rxhttp.RxHttpDisposableManager;
import com.xia.baseproject.ui.fragments.SupportFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author xia
 * @date 2018/8/1.
 */
public class SupportFragmentDelegate {

    //网络连接状态表示
    private static final int NET_OFF = 0;
    private static final int NET_ON = 1;
    private static final int NET_DEFAULT = -1;

    private SupportFragment mFragment;
    private Unbinder mUnbinder = null;

    //保证转场动画的流畅性
    private boolean mIsOnSupportVisible;
    private boolean mIsOnEnterAnimationEnd;

    //防止多次初始化
    private boolean mIsInitAll = true;
    private int mLastNetStatus = NET_DEFAULT;

    //网络是否重新连接
    private boolean mNetReConnect;
    private final WeakHandler mWeakHandler = new WeakHandler();

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

    public void onDestroyView() {
        mWeakHandler.removeCallbacksAndMessages(null);
        Rest.getHandler().removeCallbacksAndMessages(null);
        RxBusManager.unregister(mFragment);
        final String tagName = mFragment.getClass().getName();
        RxHttpDisposableManager.getInstance().remove(tagName);
    }

    public void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    private void initAll() {
        if (isGlobalCheckNetWork()) {
            hasNetWork(NetworkUtils.isConnected());
        }
        if (mIsInitAll) {
            mIsInitAll = false;
            registerNetCheckEvent();
            getBundle(mFragment.getArguments());
            mFragment.initData();
            mFragment.initView();
            mFragment.initEvent();
        }
        mWeakHandler.postDelayed(() -> mFragment.onVisibleLazyLoad(), 200);
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
        final int currentNetStatus = isAvailable ? NET_ON : NET_OFF;
        if (currentNetStatus != mLastNetStatus || mNetReConnect) {
            if (isAvailable) {
                //判断网络是否是重连接的
                if (mLastNetStatus == NET_OFF) {
                    mNetReConnect = true;
                }
                //当网络重新连接时并且只有当前页面才执行方法
                if (mFragment.isSupportVisible() && mNetReConnect) {
                    mFragment.reConnect();
                    mNetReConnect = false;
                }
            }
            mLastNetStatus = currentNetStatus;
        }
    }
}
