package com.xia.fly.demo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.xia.fly.demo.R;
import com.xia.fly.demo.base.HeadViewFragment;
import com.xia.fly.demo.mvp.presenter.HomePresenter;
import com.xia.fly.demo.mvp.view.HomeView;
import com.xia.fly.imageloader.ImageConfigImpl;
import com.xia.fly.ui.imageloader.ImageLoader;
import com.xia.fly.utils.FragmentUtils;

import butterknife.BindView;

/**
 * @author xia
 * @date 2018/7/16.
 */
public class HomeFragment extends HeadViewFragment<HomePresenter> implements HomeView {
    @BindView(R.id.home_jump_btn)
    AppCompatButton mAppCompatButton;
    @BindView(R.id.home_photo_iv)
    AppCompatImageView mPhotoView;

    @Override
    public boolean isCheckNetWork() {
        return true;
    }

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

        ImageLoader.loadImage(
                ImageConfigImpl
                        .load(R.mipmap.ic_launcher_round)
                        .into(mPhotoView)
                        .addListener(new RequestListener() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                                Log.e("weixi", "onResourceReady: ");
                                return false;
                            }
                        }));
    }

    @Override
    public void onNetReConnect() {
        Log.e("xia", this + "    onNetReConnect: ");
    }

    @Override
    public void onNetworkState(boolean isAvailable) {
        Log.e("xia", this + "    onNetworkState: " + isAvailable);
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
