package com.xia.baseproject.demo.ui.fragments;

import android.util.Log;

import com.xia.baseproject.demo.R;
import com.xia.baseproject.demo.base.HeadViewFragment;
import com.xia.baseproject.demo.mvp.presenter.HomePresenter;
import com.xia.baseproject.demo.mvp.view.HomeView;

/**
 * @author xia
 * @date 2018/7/16.
 */
public class HomeFragment extends HeadViewFragment<HomePresenter> implements HomeView {

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
        return R.layout.fragment_test;
    }

    @Override
    public void initData() {
        getP().downloadFile();
//        new Handler().postDelayed(() -> getP().load(), 3000);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initEvent() {
        if (getView() != null) {
            getView().findViewById(R.id.main_btn).setOnClickListener(v ->
                    getParentDelegate().start(new SecondFragment()));
        }
    }

    @Override
    public void reConnect() {
        Log.e("xia", "reConnect: ");
    }

    @Override
    public void networkState(boolean isAvailable) {
        Log.e("xia", "networkState: " + isAvailable);
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
