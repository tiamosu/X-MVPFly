package com.xia.fly.imageloader;

import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.xia.fly.imageloader.CacheStrategy.StrategyType;
import com.xia.fly.ui.imageloader.BaseImageLoaderStrategy;
import com.xia.fly.ui.imageloader.ImageConfig;
import com.xia.fly.ui.imageloader.ImageLoader;

import java.io.File;

/**
 * 这里存放图片请求的配置信息,可以一直扩展字段,如果外部调用时想让图片加载框架
 * 做一些操作,比如清除缓存或者切换缓存策略,则可以定义一个 int 类型的变量,内部根据 switch(int) 做不同的操作
 * 其他操作同理
 *
 * @author xia
 * @date 2018/9/17.
 */
@SuppressWarnings("WeakerAccess")
public class ImageConfigImpl extends ImageConfig {
    public RequestOptions mRequestOptions;
    public RequestListener mRequestListener;
    public BitmapTransformation mTransformation;//glide用它来改变图形的形状
    public ImageView[] mImageViews;
    public int mCacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
    public int mFallback; //请求 url 为空,则使用此图片作为占位符
    public int mImageRadius;//图片每个圆角的大小
    public int mBlurValue;//高斯模糊值, 值越大模糊效果越大
    public int mTargetWidth, mTargetHeight;//重新设定图片大小
    public boolean mIsCrossFade;//是否使用淡入淡出过渡动画
    public boolean mIsCenterCrop;//是否将图片剪切为 CenterCrop
    public boolean mIsCenterInside;//是否将图片剪切为 CenterInside
    public boolean mIsCircleCrop;//是否将图片剪切为圆形
    public boolean mIsClearMemory;//清理内存缓存
    public boolean mIsClearDiskCache;//清理本地缓存
    public boolean mIsDontAnimate;

    private ImageConfigImpl(Object o) {
        this.mObject = o;
        if (o != null) {
            if (o instanceof String) {
                this.mUrl = (String) o;
            } else if (o instanceof Integer) {
                this.mResId = (Integer) o;
            } else if (o instanceof File) {
                this.mFile = (File) o;
            } else if (o instanceof Uri) {
                this.mUri = (Uri) o;
            }
        }
    }

    public static ImageConfigImpl load(@NonNull String url) {
        return new ImageConfigImpl(url);
    }

    public static ImageConfigImpl load(@RawRes @DrawableRes Integer resId) {
        return new ImageConfigImpl(resId);
    }

    public static ImageConfigImpl load(@NonNull File file) {
        return new ImageConfigImpl(file);
    }

    public static ImageConfigImpl load(@NonNull Uri uri) {
        return new ImageConfigImpl(uri);
    }

    public static ImageConfigImpl load(Object o) {
        return new ImageConfigImpl(o);
    }

    public ImageConfigImpl into(ImageView imageView) {
        this.mImageView = imageView;
        return this;
    }

    public ImageConfigImpl into(Target target) {
        this.mTarget = target;
        return this;
    }

    public ImageConfigImpl into(ImageView... imageViews) {
        this.mImageViews = imageViews;
        return this;
    }

    public ImageConfigImpl placeholder(int placeholder) {
        this.mPlaceholder = placeholder;
        return this;
    }

    public ImageConfigImpl error(int errorPic) {
        this.mErrorPic = errorPic;
        return this;
    }

    public ImageConfigImpl fallback(int fallback) {
        this.mFallback = fallback;
        return this;
    }

    public ImageConfigImpl cacheStrategy(@StrategyType int cacheStrategy) {
        this.mCacheStrategy = cacheStrategy;
        return this;
    }

    public ImageConfigImpl imageRadius(int imageRadius) {
        this.mImageRadius = imageRadius;
        return this;
    }

    public ImageConfigImpl blurValue(int blurValue) { //blurValue 建议设置为 15
        this.mBlurValue = blurValue;
        return this;
    }

    /**
     * 给图片添加 Glide 独有的 BitmapTransformation
     * <p>
     * 因为 BitmapTransformation 是 Glide 独有的类, 所以如果 BitmapTransformation 出现在 {@link ImageConfigImpl} 中
     * 会使 {@link ImageLoader} 难以切换为其他图片加载框架, 在 {@link ImageConfigImpl} 中只能配置基础类型和 Android 包里的类
     * 此 API 会在后面的版本中被删除, 请使用其他 API 替代
     *
     * @param transformation {@link BitmapTransformation}
     *                       请使用 {@link #mIsCircleCrop}, {@link #mIsCenterCrop}, {@link #mImageRadius} 替代
     *                       如果有其他自定义 BitmapTransformation 的需求, 请自行扩展 {@link BaseImageLoaderStrategy}
     */
    public ImageConfigImpl transform(BitmapTransformation transformation) {
        this.mTransformation = transformation;
        return this;
    }

    public ImageConfigImpl crossFade() {
        this.mIsCrossFade = true;
        return this;
    }

    public ImageConfigImpl centerCrop() {
        this.mIsCenterCrop = true;
        return this;
    }

    public ImageConfigImpl centerInside() {
        this.mIsCenterInside = true;
        return this;
    }

    public ImageConfigImpl circleCrop() {
        this.mIsCircleCrop = true;
        return this;
    }

    public ImageConfigImpl override(int targetWidth, int targetHeight) {
        this.mTargetWidth = targetWidth;
        this.mTargetHeight = targetHeight;
        return this;
    }

    public ImageConfigImpl clearMemory() {
        this.mIsClearMemory = true;
        return this;
    }

    public ImageConfigImpl clearDiskCache() {
        this.mIsClearDiskCache = true;
        return this;
    }

    public ImageConfigImpl apply(@NonNull RequestOptions requestOptions) {
        this.mRequestOptions = requestOptions;
        return this;
    }

    public ImageConfigImpl addListener(RequestListener requestListener) {
        this.mRequestListener = requestListener;
        return this;
    }

    public ImageConfigImpl dontAnimate() {
        this.mIsDontAnimate = true;
        return this;
    }
}
