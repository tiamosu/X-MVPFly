package com.xia.fly.demo.ui.fragments;

import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.xia.fly.demo.R;
import com.xia.fly.demo.base.HeadViewFragment;
import com.xia.fly.demo.mvp.presenter.MainPresenter;
import com.xia.fly.demo.mvp.view.MainView;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author xia
 * @date 2018/7/3.
 */
public class MainFragment extends HeadViewFragment<MainPresenter> implements MainView {
    @BindView(R.id.main_user_id_edit)
    AppCompatEditText mUserIdEditText;
    @BindView(R.id.main_user_psd_edit)
    AppCompatEditText mUserPsdEditText;

    private final ISupportFragment[] mFragments = new ISupportFragment[1];

    @Override
    public boolean isCheckNetWork() {
        return false;
    }

    @Override
    public MainPresenter newP() {
        return new MainPresenter();
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

    @OnClick({R.id.main_login_btn, R.id.main_open_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_login_btn:

                break;
            case R.id.main_open_btn:

                break;
            default:
        }
    }
}
