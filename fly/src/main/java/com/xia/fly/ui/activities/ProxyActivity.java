package com.xia.fly.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.xia.fly.R;
import com.xia.fly.ui.fragments.SupportFragment;
import com.xia.fly.utils.FragmentUtils;

/**
 * @author xia
 * @date 2018/7/3.
 */
public abstract class ProxyActivity extends SupportActivity {

    /**
     * @return 设置根Fragment
     */
    @NonNull
    protected abstract Class<? extends SupportFragment> setRootFragment();

    /**
     * @return APP被杀死重启时，是否还原到被杀死前保存的状态
     */
    protected boolean isRestartSaved() {
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

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isRestartSaved() && savedInstanceState != null) {
            finish();
            return;
        }
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (findFragment(setRootFragment()) == null) {
            loadRootFragment(R.id.delegate_container, FragmentUtils.newInstance(setRootFragment()));
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
