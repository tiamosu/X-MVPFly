package com.xia.fly.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.xia.fly.ui.fragments.IFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * {@link FragmentDelegate} 默认实现类
 *
 * @author xia
 * @date 2018/9/20.
 */
public class FragmentDelegateImpl implements FragmentDelegate {
    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private IFragment mIFragment;

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
    }

    @Override
    public void onActivityCreate(@Nullable Bundle savedInstanceState) {
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
    }

    @Override
    public void onDetach() {
    }

    @Override
    public boolean isAdded() {
        return false;
    }
}
