package com.xia.baseproject.demo.ui.fragments;

import android.os.Handler;
import android.util.Log;

import com.xia.baseproject.demo.R;
import com.xia.baseproject.demo.base.AbstractHeadViewDelegate;
import com.xia.baseproject.demo.mvp.presenter.TestPresenter;
import com.xia.baseproject.demo.mvp.view.TestView;

/**
 * @author xia
 * @date 2018/7/16.
 */
public class TestDelegate extends AbstractHeadViewDelegate<TestPresenter> implements TestView {

    @Override
    protected boolean isLoadHeadView() {
        return false;
    }

    @Override
    public TestPresenter newP() {
        return new TestPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public void initData() {
//        getP().detachView();
//        getP().load();
        new Handler().postDelayed(() -> getP().load(), 3000);
    }

    @Override
    public void initView() {
    }

    @Override
    public void setData(String content) {
        Log.e("weixi", "content:" + content);
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
