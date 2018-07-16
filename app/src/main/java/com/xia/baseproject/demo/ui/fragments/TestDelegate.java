package com.xia.baseproject.demo.ui.fragments;

import com.xia.baseproject.demo.R;
import com.xia.baseproject.demo.base.AbstractHeadViewDelegate;

/**
 * @author xia
 * @date 2018/7/16.
 */
public class TestDelegate extends AbstractHeadViewDelegate {

    @Override
    protected boolean isLoadHeadView() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test;
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
}
