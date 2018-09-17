package com.xia.baseproject.demo.mvp.view;

import com.xia.baseproject.demo.mvp.presenter.HomePresenter;
import com.xia.fly.mvp.BaseMvpView;

/**
 * @author xia
 * @date 2018/7/20.
 */
public interface HomeView extends BaseMvpView<HomePresenter> {

    void setData(String content);

    boolean getBoolean();

    int getNum();
}
