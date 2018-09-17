package com.xia.baseproject.ui.imageloader;

import android.widget.ImageView;

/**
 * 这里是图片加载配置信息的基类,定义一些所有图片加载框架都可以用的通用参数
 * 每个 {@link BaseImageLoaderStrategy} 应该对应一个 {@link ImageConfig} 实现类
 *
 * @author xia
 * @date 2018/9/17.
 */
public class ImageConfig {
    private String mUrl;
    private ImageView mImageView;
    private int mPlaceholder;//占位符
    private int mErrorPic;//错误占位符

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
}
