package com.xia.baseproject.demo.ui.fragments;

import android.os.Handler;
import android.util.Log;

import com.xia.baseproject.demo.R;
import com.xia.baseproject.demo.base.HeadViewFragment;
import com.xia.baseproject.demo.mvp.presenter.TestPresenter;
import com.xia.baseproject.demo.mvp.view.TestView;
import com.xia.baseproject.rxhttp.callback.AbstractStringCallback;
import com.xia.baseproject.rxhttp.request.GetRequest;

/**
 * @author xia
 * @date 2018/7/16.
 */
public class TestDelegate extends HeadViewFragment<TestPresenter> implements TestView {

    @Override
    public boolean isLoadHeadView() {
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
        test();
    }

    private void test() {
        ///friend/json
        new GetRequest("/api/app/page/home")///api/app/page/home
                .addParam("action", "banner,activity")
                .build()
                .request(this, new AbstractStringCallback() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("weixi", "onResponse: " + response);
                    }
                });
    }

    @Override
    public void initView() {
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
