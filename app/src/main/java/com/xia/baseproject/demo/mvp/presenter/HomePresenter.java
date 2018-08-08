package com.xia.baseproject.demo.mvp.presenter;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.util.Log;

import com.xia.baseproject.demo.mvp.view.HomeView;
import com.xia.baseproject.mvp.BaseMvpPresenter;
import com.xia.baseproject.rxhttp.RxHttp;
import com.xia.baseproject.rxhttp.callback.AbstractStringCallback;
import com.xia.baseproject.rxhttp.subscriber.CallbackSubscriber;

/**
 * @author xia
 * @date 2018/7/20.
 */
public class HomePresenter extends BaseMvpPresenter<HomeView> {

    public void load() {
//        Log.e("weixi", "load");
//        Log.e("weixi", "getView:" + getV());
//        Log.e("weixi", "boolean:" + getV().getBoolean());
//        Log.e("weixi", "" + getV().getNum());

        getV().setData("你好啊！！！");

        RxHttp.get("/friend/json")
                .build()
                .request(new CallbackSubscriber(new AbstractStringCallback(mLifecycleOwner) {
                    @Override
                    public void onResponse(String response) {
                        Log.e("weixi", "onResponse: " + response);
                    }
                }) {
                    @Override
                    protected boolean isShowDialog() {
                        return super.isShowDialog();
                    }

                    @Override
                    protected Dialog getDialog() {
                        return super.getDialog();
                    }
                });
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        super.onCreate(owner);
//        Log.e("weixi", "onCreate: ");
    }
}
