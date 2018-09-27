package com.xia.fly.demo.mvp.presenter;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xia.fly.demo.mvp.view.HomeView;
import com.xia.fly.http.RxHttp;
import com.xia.fly.http.callback.AbstractFileCallback;
import com.xia.fly.http.callback.AbstractStringCallback;
import com.xia.fly.http.subscriber.CallbackSubscriber;
import com.xia.fly.mvp.BaseMvpPresenter;

import java.io.File;

import io.reactivex.disposables.Disposable;

/**
 * @author xia
 * @date 2018/7/20.
 */
public class HomePresenter extends BaseMvpPresenter<HomeView> {

    public void downloadFile() {
        RxHttp.get("https://www.leverking.cn/apk/xry_test.apk")
                .upDownload()
                .build()
                .request(new CallbackSubscriber(new AbstractFileCallback(mLifecycleOwner, "xry/apk", "test.apk") {
                    @Override
                    public void onResponse(File response) {
//                        Log.e("weixi", "onResponse: " + response.getName());
                    }

                    @Override
                    public void inProgress(float progress, long total) {
//                        Log.e("weixi", "inProgress: " + progress);
                    }
                }));
    }

    public void load(int index) {
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
                        Log.e("weixi", "onResponse" + index + " :" + response);
                    }
                }) {
                    @Override
                    protected boolean isShowLoadingDialog() {
                        return super.isShowLoadingDialog();
                    }

                    @Override
                    protected Dialog getLoadingDialog() {
                        return super.getLoadingDialog();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        if (!NetworkUtils.isConnected()) {
                            d.dispose();
                            ToastUtils.showShort("无法连接网络");
                            return;
                        }
                        super.onSubscribe(d);
                    }
                });
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        super.onCreate(owner);
//        Log.e("weixi", "onCreate: ");
    }
}
