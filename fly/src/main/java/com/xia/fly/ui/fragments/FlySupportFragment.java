package com.xia.fly.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.CacheType;
import com.xia.fly.integration.rxbus.IRxBusCallback;
import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.mvp.BaseMvpPresenter;
import com.xia.fly.mvp.BaseMvpView;
import com.xia.fly.ui.fragments.delegate.FlySupportFragmentDelegate;
import com.xia.fly.utils.FlyUtils;

import java.lang.ref.WeakReference;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author xia
 * @date 2018/8/1.
 */
@SuppressWarnings("unused")
public abstract class FlySupportFragment<P extends BaseMvpPresenter>
        extends SupportFragment implements IFragment, BaseMvpView<P> {

    private final FlySupportFragmentDelegate mDelegate = new FlySupportFragmentDelegate(this);
    private P mPresenter;
    private Cache mCache;
    private WeakReference<View> mRootView;

    protected View getRootView() {
        return mRootView != null ? mRootView.get() : null;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = FlyUtils.getAppComponent().cacheFactory().build(CacheType.Companion.getFRAGMENT_CACHE());
        }
        return mCache;
    }

    @SuppressWarnings("unchecked")
    public <T extends SupportFragment> T getParentDelegate() {
        final Fragment parentFragment = getParentFragment();
        return (T) (parentFragment != null ? parentFragment : this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getRootView() == null) {
            final View view = mDelegate.onCreateView(inflater, container);
            mRootView = new WeakReference<>(view);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewParent viewParent;
            if ((viewParent = getRootView().getParent()) instanceof ViewGroup) {
                final ViewGroup parent = (ViewGroup) viewParent;
                parent.removeView(getRootView());
            }
        }
        return getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onBindAny(getRootView());
    }

    /**
     * 在{@link #loadMultipleRootFragment(int, int, ISupportFragment...)}的情况下，
     * 该方法将会先于{{@link #onSupportVisible()}先执行
     */
    @CallSuper
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mDelegate.onEnterAnimationEnd();
    }

    @CallSuper
    @Override
    public void onSupportVisible() {
        mDelegate.onSupportVisible();
    }

    @CallSuper
    @Override
    public void onNewBundle(@NonNull Bundle bundle) {
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
        mDelegate.onDestroy(mPresenter);
        super.onDestroy();
    }

    protected P getP() {
        return mPresenter != null ? mPresenter : newP();
    }

    @Override
    public void onLazyLoadData() {
    }

    @Override
    public boolean isLoadTitleBar() {
        return true;
    }

    @Override
    public void onCreateTitleBar(@NonNull FrameLayout titleBarContainer) {
    }

    @Override
    public void getBundleExtras(@NonNull Bundle bundle) {
    }

    @CallSuper
    @Override
    public void onVisibleLazyLoad() {
    }

    @Override
    public boolean isCheckNetWork() {
        return true;
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
