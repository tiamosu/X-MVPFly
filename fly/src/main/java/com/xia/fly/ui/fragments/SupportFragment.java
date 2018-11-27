package com.xia.fly.ui.fragments;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.NetworkUtils;
import com.xia.fly.R;
import com.xia.fly.constant.NetworkState;
import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.CacheType;
import com.xia.fly.integration.rxbus.IRxBusCallback;
import com.xia.fly.integration.rxbus.RxBusEventTag;
import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.mvp.BaseMvpPresenter;
import com.xia.fly.mvp.BaseMvpView;
import com.xia.fly.utils.FlyUtils;

import java.util.concurrent.atomic.AtomicBoolean;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author xia
 * @date 2018/8/1.
 */
public abstract class SupportFragment<P extends BaseMvpPresenter>
        extends AbstractSupportFragment implements IFragment, BaseMvpView<P> {

    private P mPresenter;
    private Cache<String, Object> mCache;

    //保证转场动画的流畅性
    private boolean mIsOnSupportVisible;
    private boolean mIsOnEnterAnimationEnd;

    //防止多次初始化
    private final AtomicBoolean mInitialized = new AtomicBoolean(false);
    //记录上一次网络连接状态
    private int mLastNetStatus = NetworkState.NETWORK_DEFAULT;
    //网络是否重新连接
    private boolean mNetReConnect;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = FlyUtils.getAppComponent().cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.base_layout_root_view, container, false);
        if (isLoadHeadView()) {
            final FrameLayout titleBarContainer = rootView.findViewById(R.id.layout_root_view_head_container);
            onCreateTitleBar(titleBarContainer);
        }
        if (getLayoutId() != 0) {
            final FrameLayout contentContainer = rootView.findViewById(R.id.layout_root_view_content_container);
            View.inflate(getContext(), getLayoutId(), contentContainer);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMvp();
        getBundle(getArguments());
        initData();
    }

    /**
     * 在{@link #loadMultipleRootFragment(int, int, ISupportFragment...)}的情况下，
     * 该方法将会先于{{@link #onSupportVisible()}先执行
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mIsOnEnterAnimationEnd = true;
        if (mIsOnSupportVisible) {
            onVisibleLazyInit();
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mIsOnSupportVisible = true;
        if (mIsOnEnterAnimationEnd) {
            onVisibleLazyInit();
        }
    }

    @Override
    public void onNewBundle(Bundle args) {
        getBundle(args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    private void onVisibleLazyInit() {
        if (mInitialized.compareAndSet(false, true)) {
            initView();
            initEvent();
            onLazyLoadData();
            checkNetEvent();
        }
        if (isCheckNetWork()) {
            hasNetWork(NetworkUtils.isConnected());
        }
        onVisibleLazyLoad();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initMvp() {
        if (mPresenter == null) {
            mPresenter = newP();
            if (mPresenter != null) {
                mPresenter.attachView(this);
                getLifecycle().addObserver(mPresenter);
            }
        }
    }

    private void getBundle(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            getBundleExtras(bundle);
        }
    }

    private void checkNetEvent() {
        if (isCheckNetWork()) {
            RxBusHelper.subscribeWithTags(this, (eventTag, rxBusMessage) -> {
                if (eventTag.equals(RxBusEventTag.NETWORK_CHANGE)) {
                    final boolean isAvailable = (boolean) rxBusMessage.mObj;
                    hasNetWork(isAvailable);
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
            if (isSupportVisible()) {
                onNetworkState(isAvailable);
                if (isAvailable && mNetReConnect) {
                    onNetReConnect();
                    mNetReConnect = false;
                }
                mLastNetStatus = currentNetStatus;
            }
        }
    }

    protected P getP() {
        return mPresenter == null ? newP() : mPresenter;
    }

    @Override
    public FragmentActivity getContext() {
        return super.getContext();
    }

    @Override
    public void onLazyLoadData() {
    }

    @Override
    public boolean isLoadHeadView() {
        return true;
    }

    @Override
    public void onCreateTitleBar(FrameLayout titleBarContainer) {
    }

    @Override
    public void getBundleExtras(Bundle bundle) {
    }

    @CallSuper
    @Override
    public void onVisibleLazyLoad() {
    }

    @Override
    public boolean isCheckNetWork() {
        return false;
    }

    @Override
    public void onNetworkState(boolean isAvailable) {
    }

    @Override
    public void onNetReConnect() {
    }

    protected void subscribeWithTags(final IRxBusCallback callback, final String... tags) {
        RxBusHelper.subscribeWithTags(this, callback, tags);
    }

    protected void subscribeStickyWithTags(final IRxBusCallback callback, final String... tags) {
        RxBusHelper.subscribeStickyWithTags(this, callback, tags);
    }
}
