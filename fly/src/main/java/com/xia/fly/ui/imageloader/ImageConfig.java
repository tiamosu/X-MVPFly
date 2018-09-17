package com.xia.fly.ui.imageloader;

import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;

/**
 * 这里是图片加载配置信息的基类,定义一些所有图片加载框架都可以用的通用参数
 * 每个 {@link BaseImageLoaderStrategy} 应该对应一个 {@link ImageConfig} 实现类
 *
 * @author xia
 * @date 2018/9/17.
 */
public class ImageConfig {
    protected String mUrl;
    protected ImageView mImageView;
    protected int mPlaceholder;//占位符
    protected int mErrorPic;//错误占位符
    protected Target mTarget;

    public String getUrl() {
        return mUrl;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public int getPlaceholder() {
        return mPlaceholder;
    }

    public int getErrorPic() {
        return mErrorPic;
    }

    public Target getTarget() {
        return mTarget;
    }
}