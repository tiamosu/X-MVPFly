package com.xia.fly.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.xia.fly.constant.NetworkState;
import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.CacheType;
import com.xia.fly.integration.rxbus.IRxBusCallback;
import com.xia.fly.integration.rxbus.RxBusEventTag;
import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.mvp.BaseMvpPresenter;
import com.xia.fly.mvp.BaseMvpView;
import com.xia.fly.receiver.NetworkChangeReceiver;
import com.xia.fly.utils.DispatchTouchEventHelper;
import com.xia.fly.utils.FlyUtils;
import com.xia.fly.utils.Platform;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author xia
 * @date 2018/8/16.
 */
public abstract class SupportActivity<P extends BaseMvpPresenter>
        extends AbstractSupportActivity implements IActivity, BaseMvpView<P> {

    private P mPresenter;
    private Unbinder mUnbinder;
    private Cache<String, Object> mCache;

    //网络状态监听广播
    private NetworkChangeReceiver mNetworkChangeReceiver;
    //记录上一次网络连接状态
    private int mLastNetStatus = NetworkState.NETWORK_DEFAULT;
    //网络是否重新连接
    private boolean mNetReConnect;

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = FlyUtils.getAppComponent().cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            final View rootView = getLayoutInflater().inflate(getLayoutId(), null);
            setContentView(rootView);
            mUnbinder = ButterKnife.bind(this, rootView);
        }
        initAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCheckNetWork()) {
            hasNetWork(NetworkUtils.isConnected());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Platform.getHandler().removeCallbacksAndMessages(null);
        RxBusHelper.unregister(this);
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver);
            mNetworkChangeReceiver = null;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        DispatchTouchEventHelper.dispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    @SuppressWarnings("unchecked")
    private void initMvp() {
        if (mPresenter == null) {
            mPresenter = newP();
            if (mPresenter != null) {
                mPresenter.attachView(this);
                getLifecycle().addObserver(mPresenter);
            }
        }
    }

    private void initAll() {
        initMvp();
        initData();
        initView();
        initEvent();
        onLazyLoadData();

        if (isCheckNetWork()) {
            mNetworkChangeReceiver = NetworkChangeReceiver.register(this);
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
            //APP位于前台并且当前页处于栈顶（可见）时执行
            if (AppUtils.isAppForeground()
                    && ActivityUtils.getTopActivity() == this) {
                onNetworkState(isAvailable);
                if (isAvailable && mNetReConnect) {
                    onNetReConnect();
                    mNetReConnect = false;
                }
                mLastNetStatus = currentNetStatus;
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected P getP() {
        return mPresenter == null ? newP() : mPresenter;
    }

    @Override
    public AppCompatActivity getContext() {
        return this;
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

    @Override
    public boolean isDispatchTouchHideKeyboard() {
        return true;
    }

    @Override
    public void onDispatchTouchHideKeyboard(EditText editText) {
    }

    protected void subscribeWithTags(final IRxBusCallback callback, final String... tags) {
        RxBusHelper.subscribeWithTags(this, callback, tags);
    }

    protected void subscribeStickyWithTags(final IRxBusCallback callback, final String... tags) {
        RxBusHelper.subscribeStickyWithTags(this, callback, tags);
    }
}
