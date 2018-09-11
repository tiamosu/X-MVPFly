package com.xia.baseproject.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.xia.baseproject.R;
import com.xia.baseproject.ui.fragments.SupportFragment;
import com.xia.baseproject.utils.FragmentUtils;

/**
 * @author xia
 * @date 2018/7/3.
 */
public abstract class ProxyActivity extends SupportActivity {

    /**
     * @return 设置根Fragment
     */
    @NonNull
    public abstract Class<? extends SupportFragment> setRootFragment();

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
}
