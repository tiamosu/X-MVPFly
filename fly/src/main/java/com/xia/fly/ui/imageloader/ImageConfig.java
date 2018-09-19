package com.xia.fly.ui.imageloader;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * 这里是图片加载配置信息的基类,定义一些所有图片加载框架都可以用的通用参数
 * 每个 {@link BaseImageLoaderStrategy} 应该对应一个 {@link ImageConfig} 实现类
 *
 * @author xia
 * @date 2018/9/17.
 */
public class ImageConfig {
    public String mUrl;
    public Integer mResId;
    public File mFile;
    public Uri mUri;
    public Object mObject;
    public ImageView mImageView;
    public Target mTarget;
    public int mPlaceholder;//占位符
    public int mErrorPic;//错误占位符
}
