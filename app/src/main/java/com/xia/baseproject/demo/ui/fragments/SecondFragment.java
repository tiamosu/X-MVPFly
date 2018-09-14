package com.xia.baseproject.demo.ui.fragments;

import android.os.Bundle;

import com.xia.baseproject.demo.R;
import com.xia.baseproject.demo.base.HeadViewFragment;
import com.xia.baseproject.demo.mvp.presenter.HomePresenter;
import com.xia.baseproject.demo.mvp.view.HomeView;

/**
 * @author xia
 * @date 2018/8/6.
 */
public class SecondFragment extends HeadViewFragment<HomePresenter> implements HomeView {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    public HomePresenter newP() {
        return new HomePresenter();
    }

    @Override
    public void getBundleExtras(Bundle bundle) {
        final String s = bundle.getString("Hello");
//        Log.e("weixi", "getBundleExtras: " + s);
    }

    @Override
    public void initData() {
        getP().load(1);
        getP().load(2);
        getP().load(3);
        getP().load(4);
        getP().load(5);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initEvent() {
        if (getView() != null) {
            getView().findViewById(R.id.btn_jump)
                    .setOnClickListener(v -> start(new SecondFragment()));
        }
    }

    @Override
    public void setData(String content) {
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public int getNum() {
        return 0;
    }
}
