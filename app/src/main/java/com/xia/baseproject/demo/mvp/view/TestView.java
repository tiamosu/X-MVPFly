package com.xia.baseproject.demo.mvp.view;

import com.xia.baseproject.demo.mvp.presenter.TestPresenter;
import com.xia.baseproject.mvp.BaseMvpView;

/**
 * @author xia
 * @date 2018/7/20.
 */
public interface TestView extends BaseMvpView<TestPresenter> {

    void setData(String content);

    boolean getBoolean();

    int getNum();
}
