package com.xia.fly.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.xia.fly.imageloader.DiskCacheStrategy.StrategyType;
import com.xia.fly.imageloader.TranscodeType.Type;
import com.xia.fly.ui.imageloader.BaseImageLoaderStrategy;
import com.xia.fly.ui.imageloader.ImageConfig;
import com.xia.fly.ui.imageloader.ImageLoader;

import java.io.File;
import java.net.URL;

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
    public RequestOptions mRequestOptions;//加载配置
    public RequestListener mRequestListener;//加载监听
    public BitmapTransformation mTransformation;//glide用它来改变图形的形状
    public ImageView[] mImageViews;//视图控件数组
    public int mCacheStrategy;//缓存策略
    public int mTranscodeType;
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
    public boolean mIsDontAnimate;//不显示动画

    private ImageConfigImpl(Builder builder) {
        this.mObject = builder.mObject;
        this.mImageView = builder.mImageView;
        this.mTarget = builder.mTarget;
        this.mPlaceholder = builder.mPlaceholder;
        this.mErrorPic = builder.mErrorPic;
        this.mRequestOptions = builder.mRequestOptions;
        this.mRequestListener = builder.mRequestListener;
        this.mTransformation = builder.mTransformation;
        this.mImageViews = builder.mImageViews;
        this.mCacheStrategy = builder.mCacheStrategy;
        this.mTranscodeType = builder.mTranscodeType;
        this.mFallback = builder.mFallback;
        this.mImageRadius = builder.mImageRadius;
        this.mBlurValue = builder.mBlurValue;
        this.mTargetWidth = builder.mTargetWidth;
        this.mTargetHeight = builder.mTargetHeight;
        this.mIsCrossFade = builder.mIsCrossFade;
        this.mIsCenterCrop = builder.mIsCenterCrop;
        this.mIsCenterInside = builder.mIsCenterInside;
        this.mIsCircleCrop = builder.mIsCircleCrop;
        this.mIsClearMemory = builder.mIsClearMemory;
        this.mIsClearDiskCache = builder.mIsClearDiskCache;
        this.mIsDontAnimate = builder.mIsDontAnimate;
    }

    public static Builder load(@Nullable Bitmap bitmap) {
        return new Builder(bitmap);
    }

    public static Builder load(@Nullable Drawable drawable) {
        return new Builder(drawable);
    }

    public static Builder load(@Nullable String string) {
        return new Builder(string);
    }

    public static Builder load(@Nullable Uri uri) {
        return new Builder(uri);
    }

    public static Builder load(@Nullable File file) {
        return new Builder(file);
    }

    public static Builder load(@RawRes @DrawableRes @Nullable Integer id) {
        return new Builder(id);
    }

    public static Builder load(@Nullable URL url) {
        return new Builder(url);
    }

    public static Builder load(@Nullable byte[] bytes) {
        return new Builder(bytes);
    }

    public static Builder load(Object o) {
        return new Builder(o);
    }

    public static class Builder {
        private Object mObject;//所要加载的资源
        private ImageView mImageView;
        private Target mTarget;
        private int mPlaceholder;//占位符
        private int mErrorPic;//错误占位符
        private RequestOptions mRequestOptions;//加载配置
        private RequestListener mRequestListener;//加载监听
        private BitmapTransformation mTransformation;//glide用它来改变图形的形状
        private ImageView[] mImageViews;//视图控件数组
        private int mCacheStrategy;//缓存策略
        private int mTranscodeType;
        private int mFallback; //请求 url 为空,则使用此图片作为占位符
        private int mImageRadius;//图片每个圆角的大小
        private int mBlurValue;//高斯模糊值, 值越大模糊效果越大
        private int mTargetWidth, mTargetHeight;//重新设定图片大小
        private boolean mIsCrossFade;//是否使用淡入淡出过渡动画
        private boolean mIsCenterCrop;//是否将图片剪切为 CenterCrop
        private boolean mIsCenterInside;//是否将图片剪切为 CenterInside
        private boolean mIsCircleCrop;//是否将图片剪切为圆形
        private boolean mIsClearMemory;//清理内存缓存
        private boolean mIsClearDiskCache;//清理本地缓存
        private boolean mIsDontAnimate;//不显示动画

        private Builder(Object o) {
            this.mObject = o;
        }

        public Builder into(ImageView imageView) {
            this.mImageView = imageView;
            return this;
        }

        public Builder into(Target target) {
            this.mTarget = target;
            return this;
        }

        public Builder into(ImageView... imageViews) {
            this.mImageViews = imageViews;
            return this;
        }

        public Builder as(@Type int transcodeType) {
            this.mTranscodeType = transcodeType;
            return this;
        }

        public Builder placeholder(int placeholder) {
            this.mPlaceholder = placeholder;
            return this;
        }

        public Builder error(int errorPic) {
            this.mErrorPic = errorPic;
            return this;
        }

        public Builder fallback(int fallback) {
            this.mFallback = fallback;
            return this;
        }

        public Builder diskCacheStrategy(@StrategyType int cacheStrategy) {
            this.mCacheStrategy = cacheStrategy;
            return this;
        }

        public Builder imageRadius(int imageRadius) {
            this.mImageRadius = imageRadius;
            return this;
        }

        public Builder blurValue(int blurValue) { //blurValue 建议设置为 15
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
        public Builder transform(BitmapTransformation transformation) {
            this.mTransformation = transformation;
            return this;
        }

        public Builder crossFade() {
            this.mIsCrossFade = true;
            return this;
        }

        public Builder centerCrop() {
            this.mIsCenterCrop = true;
            return this;
        }

        public Builder centerInside() {
            this.mIsCenterInside = true;
            return this;
        }

        public Builder circleCrop() {
            this.mIsCircleCrop = true;
            return this;
        }

        public Builder override(int targetWidth, int targetHeight) {
            this.mTargetWidth = targetWidth;
            this.mTargetHeight = targetHeight;
            return this;
        }

        public Builder clearMemory() {
            this.mIsClearMemory = true;
            return this;
        }

        public Builder clearDiskCache() {
            this.mIsClearDiskCache = true;
            return this;
        }

        public Builder apply(@NonNull RequestOptions requestOptions) {
            this.mRequestOptions = requestOptions;
            return this;
        }

        public Builder addListener(RequestListener requestListener) {
            this.mRequestListener = requestListener;
            return this;
        }

        public Builder dontAnimate() {
            this.mIsDontAnimate = true;
            return this;
        }

        public ImageConfigImpl build() {
            return new ImageConfigImpl(this);
        }
    }
}
