package com.xia.baseproject.ui.fragments.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blankj.rxbus.RxBusMessage;
import com.blankj.utilcode.util.NetworkUtils;
import com.xia.baseproject.R;
import com.xia.baseproject.app.Rest;
import com.xia.baseproject.app.RestConfigKeys;
import com.xia.baseproject.rxbus.IRxBusCallback;
import com.xia.baseproject.rxbus.RxBusEventTag;
import com.xia.baseproject.rxbus.RxBusHelper;
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
        final String httpTag = mFragment.getClass().getSimpleName();
        RxHttpDisposableManager.getInstance().remove(httpTag);
        Rest.getHandler().removeCallbacksAndMessages(null);
        RxBusHelper.unregister(mFragment);
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
        final int currentNetStatus = isAvailable ? NET_ON : NET_OFF;
        if (currentNetStatus != mLastNetStatus || mNetReConnect) {
            //判断网络是否是重连接的
            if (isAvailable && mLastNetStatus == NET_OFF) {
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
