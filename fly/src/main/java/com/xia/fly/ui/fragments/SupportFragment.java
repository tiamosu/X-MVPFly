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

import com.xia.fly.R;
import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.CacheType;
import com.xia.fly.integration.rxbus.IRxBusCallback;
import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.mvp.BaseMvpPresenter;
import com.xia.fly.mvp.BaseMvpView;
import com.xia.fly.utils.FlyUtils;

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
        if (getLayoutId() != 0) {
            //预留添加TitleBar
            final FrameLayout titleBarContainer = rootView.findViewById(R.id.layout_root_view_head_container);
            onCreateTitleBar(titleBarContainer);
            //加载页面布局（预留TitleBar之下）
            final FrameLayout contentContainer = rootView.findViewById(R.id.layout_root_view_content_container);
            View.inflate(getContext(), getLayoutId(), contentContainer);
        }
        return rootView;
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
    public void onNewBundle(Bundle bundle) {
        super.onNewBundle(bundle);
        if (bundle != null && !bundle.isEmpty()) {
            getBundleExtras(bundle);
        }
    }

    private void onVisibleLazyInit() {
        RxBusHelper.post(getClass().getSimpleName().intern());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
            getLifecycle().removeObserver(mPresenter);
            mPresenter = null;
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
