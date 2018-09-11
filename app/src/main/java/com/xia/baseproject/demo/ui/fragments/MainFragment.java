package com.xia.baseproject.demo.ui.fragments;

import android.util.Log;

import com.xia.baseproject.demo.R;
import com.xia.baseproject.demo.base.HeadViewFragment;

import butterknife.OnClick;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author xia
 * @date 2018/7/3.
 */
public class MainFragment extends HeadViewFragment {

    private ISupportFragment[] mFragments = new ISupportFragment[1];

    @Override
    public boolean isCheckNetWork() {
        return false;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initData() {
        final ISupportFragment firstFragment = findChildFragment(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[0] = new HomeFragment();
            loadMultipleRootFragment(R.id.main_fl, 0, mFragments);
        } else {
            mFragments[0] = firstFragment;
        }
    }

    @Override
    public void initView() {
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @OnClick(R.id.main_btn)
    public void onViewClicked() {
        Log.e("weixi", "zzzzzzz");
    }
}
