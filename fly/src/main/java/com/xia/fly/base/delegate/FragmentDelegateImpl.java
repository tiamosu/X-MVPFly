package com.xia.fly.base.delegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.blankj.rxbus.RxBusMessage;
import com.blankj.utilcode.util.NetworkUtils;
import com.xia.fly.constant.NetworkState;
import com.xia.fly.integration.rxbus.IRxBusCallback;
import com.xia.fly.integration.rxbus.RxBusEventTag;
import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.ui.dialog.loader.Loader;
import com.xia.fly.ui.fragments.IFragment;
import com.xia.fly.ui.fragments.SupportFragment;
import com.xia.fly.utils.Platform;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * {@link FragmentDelegate} 默认实现类
 *
 * @author xia
 * @date 2018/9/20.
 */
public class FragmentDelegateImpl implements FragmentDelegate {
    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private IFragment mIFragment;
    private Unbinder mUnbinder;

    public FragmentDelegateImpl(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        this.mFragmentManager = fragmentManager;
        this.mFragment = fragment;
        this.mIFragment = (IFragment) fragment;
    }

    //防止多次初始化
    private final AtomicBoolean mInitialized = new AtomicBoolean(false);
    //记录上一次网络连接状态
    private int mLastNetStatus = NetworkState.NETWORK_DEFAULT;
    //网络是否重新连接
    private boolean mNetReConnect;

    @Override
    public void onAttach(@NonNull Context context) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        final String tag = mFragment.getClass().getSimpleName().intern();
        RxBusHelper.subscribeWithTags(mFragment, new IRxBusCallback() {
            @Override
            public void callback(String eventTag, RxBusMessage rxBusMessage) {
                if (eventTag.equals(tag)) {
                    initAll();
                }
            }
        }, tag);
    }

    @SuppressLint("MissingPermission")
    private void initAll() {
        if (mInitialized.compareAndSet(false, true)) {
            mIFragment.initMvp();
            getBundle(mFragment.getArguments());
            mIFragment.initData();
            mIFragment.initView();
            mIFragment.initEvent();
            mIFragment.onLazyLoadData();

            checkNetEvent();
        }
        if (mIFragment.isCheckNetWork()) {
            hasNetWork(NetworkUtils.isConnected());
        }
        mIFragment.onVisibleLazyLoad();
    }

    private void getBundle(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            mIFragment.getBundleExtras(bundle);
        }
    }

    private void checkNetEvent() {
        if (mIFragment.isCheckNetWork()) {
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
            boolean isSupportVisible = mFragment.isResumed();
            if (mFragment instanceof SupportFragment) {
                final SupportFragment supportFragment = (SupportFragment) mFragment;
                isSupportVisible = supportFragment.isSupportVisible();
            }
            if (isSupportVisible) {
                mIFragment.onNetworkState(isAvailable);
                if (isAvailable && mNetReConnect) {
                    mIFragment.onNetReConnect();
                    mNetReConnect = false;
                }
                mLastNetStatus = currentNetStatus;
            }
        }
    }

    @Override
    public void onCreateView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            mUnbinder = ButterKnife.bind(mFragment, view);
        }
    }

    @Override
    public void onActivityCreate(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
        Platform.getLoadingHandler().removeCallbacksAndMessages(null);
        Loader.stopLoading();
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
    }

    @Override
    public void onDestroyView() {
        RxBusHelper.unregister(mFragment);
        Platform.getHandler().removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            try {
                //fix Bindings already cleared
                mUnbinder.unbind();
            } catch (IllegalStateException ignored) {
            }
            mUnbinder = null;
        }
        this.mFragmentManager = null;
        this.mFragment = null;
        this.mIFragment = null;
    }

    @Override
    public void onDetach() {
    }

    @Override
    public boolean isAdded() {
        return false;
    }
}
