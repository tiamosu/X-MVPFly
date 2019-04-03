package com.xia.fly.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.xia.fly.R;
import com.xia.fly.utils.FragmentUtils;
import com.xia.fly.utils.Preconditions;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ContentFrameLayout;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author xia
 * @date 2018/7/3.
 */
public abstract class ProxyActivity extends FlySupportActivity {

    /**
     * @return 设置根Fragment
     */
    @NonNull
    protected abstract Class<? extends ISupportFragment> setRootFragment();

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

    @SuppressLint("RestrictedApi")
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
        initMvp();
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

            final ISupportFragment toFragment = FragmentUtils.newInstance(setRootFragment());
            if (toFragment != null) {
                loadRootFragment(proxyContainerId, toFragment);
            }
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
