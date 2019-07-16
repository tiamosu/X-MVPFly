package com.xia.fly.ui.activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.xia.fly.integration.cache.Cache;
import com.xia.fly.integration.cache.CacheType;
import com.xia.fly.integration.rxbus.IRxBusCallback;
import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.mvp.BaseMvpPresenter;
import com.xia.fly.mvp.BaseMvpView;
import com.xia.fly.ui.activities.delegate.FlySupportActivityDelegate;
import com.xia.fly.utils.FlyUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author xia
 * @date 2018/8/16.
 */
@SuppressWarnings("unused")
public abstract class FlySupportActivity<P extends BaseMvpPresenter>
        extends SupportActivity implements IActivity, BaseMvpView<P> {

    private final FlySupportActivityDelegate mDelegate = new FlySupportActivityDelegate(this);
    private P mPresenter;
    private Cache mCache;

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = FlyUtils.getAppComponent().cacheFactory().build(CacheType.Companion.getACTIVITY_CACHE());
        }
        return mCache;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDelegate.onResume();
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
    protected void onDestroy() {
        mDelegate.onDestroy(mPresenter);
        super.onDestroy();
    }

    protected P getP() {
        return mPresenter != null ? mPresenter : newP();
    }

    @Override
    public boolean isLoadTitleBar() {
        return false;
    }

    @Override
    public void onCreateTitleBar(@NonNull FrameLayout titleBarContainer) {
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
