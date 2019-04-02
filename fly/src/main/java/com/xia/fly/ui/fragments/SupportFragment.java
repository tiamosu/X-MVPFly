package com.xia.fly.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.CacheType;
import com.xia.fly.integration.rxbus.IRxBusCallback;
import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.mvp.BaseMvpPresenter;
import com.xia.fly.mvp.BaseMvpView;
import com.xia.fly.ui.fragments.delegate.SupportFragmentDelegate;
import com.xia.fly.utils.FlyUtils;

import org.jetbrains.annotations.NotNull;

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
@SuppressWarnings("unused")
public abstract class SupportFragment<P extends BaseMvpPresenter>
        extends AbstractSupportFragment implements IFragment, BaseMvpView<P> {

    private final SupportFragmentDelegate mDelegate = new SupportFragmentDelegate(this);
    private P mPresenter;
    private Cache<String, Object> mCache;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = FlyUtils.getAppComponent().cacheFactory().build(CacheType.Companion.getFRAGMENT_CACHE());
        }
        return mCache;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mDelegate.onCreateView(inflater, container);
    }

    /**
     * 在{@link #loadMultipleRootFragment(int, int, ISupportFragment...)}的情况下，
     * 该方法将会先于{{@link #onSupportVisible()}先执行
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mDelegate.onEnterAnimationEnd();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mDelegate.onSupportVisible();
    }

    @Override
    public void onNewBundle(Bundle bundle) {
        super.onNewBundle(bundle);
        mDelegate.getBundle(bundle);
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
        mDelegate.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            getLifecycle().removeObserver(mPresenter);
        }
        super.onDestroy();
    }

    @Nullable
    protected P getP() {
        return mPresenter != null ? mPresenter : newP();
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
    public boolean isLoadTitleBar() {
        return false;
    }

    @Override
    public void onCreateTitleBar(@NotNull FrameLayout titleBarContainer) {
    }

    @Override
    public void getBundleExtras(@NotNull Bundle bundle) {
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
