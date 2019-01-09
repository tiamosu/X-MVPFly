package com.xia.fly.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.CacheType;
import com.xia.fly.integration.rxbus.IRxBusCallback;
import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.mvp.BaseMvpPresenter;
import com.xia.fly.mvp.BaseMvpView;
import com.xia.fly.utils.FlyUtils;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import me.yokeyword.fragmentation.AbstractSupportFragment;
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
        if (getLayoutId() == 0) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        final LinearLayout rootView = new LinearLayout(getContext());
        rootView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        rootView.setOrientation(LinearLayout.VERTICAL);

        //预留添加TitleBar
        final FrameLayout titleBarContainer = new FrameLayout(getContext());
        titleBarContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        rootView.addView(titleBarContainer);
        onCreateTitleBar(titleBarContainer);

        //加载页面布局（预留TitleBar之下）
        final FrameLayout contentContainer = new FrameLayout(getContext());
        contentContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        rootView.addView(contentContainer);
        View.inflate(getContext(), getLayoutId(), contentContainer);
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
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            getLifecycle().removeObserver(mPresenter);
            mPresenter = null;
        }
    }

    protected P getP() {
        return mPresenter == null ? newP() : mPresenter;
    }

    @NonNull
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
