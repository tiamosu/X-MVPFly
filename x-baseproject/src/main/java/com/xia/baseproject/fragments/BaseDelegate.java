package com.xia.baseproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blankj.rxbus.RxBus;
import com.blankj.utilcode.util.NetworkUtils;
import com.xia.baseproject.R;
import com.xia.baseproject.app.Rest;
import com.xia.baseproject.app.RestConfigKeys;
import com.xia.baseproject.mvp.BaseMvpPresenter;
import com.xia.baseproject.rxbus.RxBusHelper;
import com.xia.baseproject.rxbus.RxBusManager;
import com.xia.baseproject.rxbus.event.NetworkChangeEvent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author xia
 * @date 2018/7/3.
 */
@SuppressWarnings("all")
public abstract class BaseDelegate<P extends BaseMvpPresenter> extends AbstractMvpFragment<P> {
    private Unbinder mUnbinder = null;

    //保证转场动画的流畅性
    private boolean mIsOnSupportVisible;
    private boolean mIsOnEnterAnimationEnd;

    //防止多次初始化
    private boolean mIsInitAll = true;
    private int mLastNetStatus = -1;

    /**
     * 该方法用来替代{@link #onSupportVisible()}，保证转场动画的流畅性
     */
    public void onVisibleLazyLoadData() {
    }

    /**
     * @return 是否加载头部标题栏
     */
    protected boolean isLoadHeadView() {
        return true;
    }

    /**
     * @return 是否检查网络状态
     */
    protected boolean isCheckNetWork() {
        return true;
    }

    /**
     * @param headContainer 头部标题栏容器，可用于自定义添加视图
     */
    protected void onCreateHeadView(FrameLayout headContainer) {
    }

    /**
     * @param bundle 用于获取页面跳转传参数据
     */
    protected void getBundleExtras(Bundle bundle) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.base_layout_root_view, container, false);
        if (isLoadHeadView()) {
            final FrameLayout headContainer = rootView.findViewById(R.id.layout_root_view_head_container);
            onCreateHeadView(headContainer);
        }
        if (getLayoutId() > 0) {
            final FrameLayout contentContainer = rootView.findViewById(R.id.layout_root_view_content_container);
            View.inflate(getContext(), getLayoutId(), contentContainer);
        }
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 在{@link #loadMultipleRootFragment(int, int, ISupportFragment...)}的情况下，
     * <p>
     * {@link #onEnterAnimationEnd(Bundle)}方法，将会先于{@link #onSupportVisible()}先执行
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mIsOnEnterAnimationEnd = true;
        if (mIsOnSupportVisible) {
            initAll();
        }
    }

    @Override
    public void onSupportVisible() {
        mIsOnSupportVisible = true;
        if (mIsOnEnterAnimationEnd) {
            initAll();
        }
    }

    private void initAll() {
        if (isGlobalCheckNetWork()) {
            hasNetWork(NetworkUtils.isAvailableByPing());
        }
        if (mIsInitAll) {
            mIsInitAll = false;
            getBundle(getArguments());
            initData();
            initView();
            initEvent();
            netWorkChangeEvent();
        }
        onVisibleLazyLoadData();
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        getBundle(args);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBusManager.unregister(this);
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    private void getBundle(Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            getBundleExtras(bundle);
        }
    }

    private void netWorkChangeEvent() {
        if (isGlobalCheckNetWork()) {
            RxBusManager.subscribe(this,
                    RxBusHelper.NET_CHANGE_TAG, new RxBus.Callback<NetworkChangeEvent>() {
                        @Override
                        public void onEvent(NetworkChangeEvent event) {
                            hasNetWork(event.isAvailable);
                        }
                    });
        }
    }

    private void hasNetWork(boolean isAvailable) {
        final int currentNetStatus = isAvailable ? 1 : 0;
        if (currentNetStatus != mLastNetStatus) {
            mLastNetStatus = currentNetStatus;
            Log.e("weixi", "hasNetWork: " + isAvailable);
        }
    }

    private boolean isGlobalCheckNetWork() {
        final boolean isCheckNetWork = Rest.getConfiguration(RestConfigKeys.NETWORK_CHECK);
        return isCheckNetWork && isCheckNetWork();
    }
}
