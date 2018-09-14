package com.xia.baseproject.demo.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;

import com.xia.baseproject.demo.R;
import com.xia.baseproject.demo.base.HeadViewFragment;
import com.xia.baseproject.demo.mvp.presenter.HomePresenter;
import com.xia.baseproject.demo.mvp.view.HomeView;
import com.xia.baseproject.utils.FragmentUtils;

import butterknife.BindView;

/**
 * @author xia
 * @date 2018/7/16.
 */
public class HomeFragment extends HeadViewFragment<HomePresenter> implements HomeView {
    @BindView(R.id.home_jump_btn)
    AppCompatButton mAppCompatButton;

    @Override
    public boolean isLoadHeadView() {
        return false;
    }

    @Override
    public HomePresenter newP() {
        return new HomePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        mAppCompatButton.setText("跳转下一页");
    }

    @Override
    public void initEvent() {
        if (getView() != null) {
            getView().findViewById(R.id.home_jump_btn)
                    .setOnClickListener(v -> {
                        final Bundle bundle = new Bundle();
                        bundle.putString("Hello", "你好");
                        start(true,
                                FragmentUtils.newInstance(SecondFragment.class, bundle));
                    });
        }
    }

    @Override
    public void onVisibleLazyLoad() {
        super.onVisibleLazyLoad();
//        getP().downloadFile();
//        new Handler().postDelayed(() -> getP().load(), 3000);
    }

    @Override
    public void onNetReConnect() {
        Log.e("xia", "onNetReConnect: ");
    }

    @Override
    public void onNetworkState(boolean isAvailable) {
        Log.e("xia", "onNetworkState: " + isAvailable);
    }

    @Override
    public void setData(String content) {
//        Log.e("weixi", "content:" + content);
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public int getNum() {
        return 99;
    }
}
