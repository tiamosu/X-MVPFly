package com.xia.fly.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.xia.fly.integration.rxbus.RxBusHelper;
import com.xia.fly.ui.fragments.IFragment;
import com.xia.fly.utils.Platform;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * {@link IFragmentDelegate} 默认实现类
 *
 * @author xia
 * @date 2018/9/20.
 */
public class FragmentDelegateImpl implements IFragmentDelegate {
    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private IFragment mIFragment;
    private Unbinder mUnbinder;

    public FragmentDelegateImpl(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        this.mFragmentManager = fragmentManager;
        this.mFragment = fragment;
        this.mIFragment = (IFragment) fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onCreateView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            mUnbinder = ButterKnife.bind(mFragment, view);
        }
    }

    @Override
    public void onActivityCreate(@Nullable Bundle savedInstanceState) {
        mIFragment.initMvp();
        mIFragment.getBundleExtras(mFragment.getArguments());
        mIFragment.initData();
        mIFragment.initView();
        mIFragment.initEvent();
        mIFragment.onLazyLoadData();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
    }

    @Override
    public void onDestroyView() {
    }

    @Override
    public void onDestroy() {
        Platform.getHandler().removeCallbacksAndMessages(null);
        RxBusHelper.unregister(mFragment);
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            try {
                //fix Bindings already cleared
                mUnbinder.unbind();
            } catch (IllegalStateException ignored) {
            }
            mUnbinder = null;
        }
        this.mFragmentManager = null;
        this.mFragment = null;
        this.mIFragment = null;
    }

    @Override
    public void onDetach() {
    }

    @Override
    public boolean isAdded() {
        return false;
    }
}
