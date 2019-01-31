package com.xia.fly.ui.activities;

import android.os.Bundle;

import com.xia.fly.R;
import com.xia.fly.ui.fragments.SupportFragment;
import com.xia.fly.utils.FragmentUtils;
import com.xia.fly.utils.Preconditions;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ContentFrameLayout;

/**
 * @author xia
 * @date 2018/7/3.
 */
@SuppressWarnings("all")
public abstract class ProxyActivity extends SupportActivity {

    /**
     * @return 设置根Fragment
     */
    @NonNull
    protected abstract Class<? extends SupportFragment> setRootFragment();

    /**
     * @return APP被杀死重启时，是否还原到被杀死前保存的状态
     */
    public boolean isRestartRestore() {
        return true;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    private ContentFrameLayout mContainerLayout;

    @Nullable
    public ContentFrameLayout getContainerLayout() {
        return mContainerLayout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isRestartRestore() && savedInstanceState != null) {
            finish();
            return;
        }

        final int containerId = R.id.delegate_container;
        if (getLayoutId() == 0) {
            mContainerLayout = new ContentFrameLayout(getContext());
            mContainerLayout.setId(containerId);
            setContentView(mContainerLayout);
        }

        loadProxyRootFragment(containerId);
        initData();
        initView();
        initEvent();
        onLazyLoadData();
    }

    @CallSuper
    protected void loadProxyRootFragment(int proxyContainerId) {
        Preconditions.checkNotNull(setRootFragment(),
                "you must set the rootFragment not be null!");
        if (findFragment(setRootFragment()) == null) {
            if (getLayoutId() != 0 && proxyContainerId == R.id.delegate_container) {
                Preconditions.checkArgument(false,
                        "you should override loadProxyRootFragment(proxyContainerId)!");
            }
            loadRootFragment(proxyContainerId, FragmentUtils.newInstance(setRootFragment()));
        }
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void onLazyLoadData() {
    }
}
