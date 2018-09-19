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
    private int mCacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
    private int mFallback; //请求 url 为空,则使用此图片作为占位符
    private int mImageRadius;//图片每个圆角的大小
    private int mBlurValue;//高斯模糊值, 值越大模糊效果越大
    private BitmapTransformation mTransformation;//glide用它来改变图形的形状
    private ImageView[] mImageViews;
    private boolean mIsCrossFade;//是否使用淡入淡出过渡动画
    private boolean mIsCenterCrop;//是否将图片剪切为 CenterCrop
    private boolean mIsCenterInside;//是否将图片剪切为 CenterInside
    private boolean mIsCircleCrop;//是否将图片剪切为圆形
    private boolean mIsClearMemory;//清理内存缓存
    private boolean mIsClearDiskCache;//清理本地缓存
    private int mTargetWidth, mTargetHeight;//重新设定图片大小
    private RequestOptions mRequestOptions;
    private RequestListener mRequestListener;
    private boolean mIsDontAnimate;

    private ImageConfigImpl(Builder builder) {
        this.mUrl = builder.mUrl;
        this.mResId = builder.mResId;
        this.mFile = builder.mFile;
        this.mUri = builder.mUri;
        this.mObject = builder.mObject;
        this.mImageView = builder.mImageView;
        this.mTarget = builder.mTarget;
        this.mPlaceholder = builder.mPlaceholder;
        this.mErrorPic = builder.mErrorPic;
        this.mFallback = builder.mFallback;
        this.mCacheStrategy = builder.mCacheStrategy;
        this.mImageRadius = builder.mImageRadius;
        this.mBlurValue = builder.mBlurValue;
        this.mTransformation = builder.mTransformation1;
        this.mImageViews = builder.mImageViews;
        this.mIsCrossFade = builder.mIsCrossFade;
        this.mIsCenterCrop = builder.mIsCenterCrop;
        this.mIsCenterInside = builder.mIsCenterInside;
        this.mIsCircleCrop = builder.mIsCircleCrop;
        this.mIsClearMemory = builder.mIsClearMemory;
        this.mIsClearDiskCache = builder.mIsClearDiskCache;
        this.mTargetWidth = builder.mTargetWidth;
        this.mTargetHeight = builder.mTargetHeight;
        this.mRequestOptions = builder.mRequestOptions;
        this.mRequestListener = builder.mRequestListener;
        this.mIsDontAnimate = builder.mIsDontAnimate;
    }

    public int getCacheStrategy() {
        return mCacheStrategy;
    }

    public BitmapTransformation getTransformation() {
        return mTransformation;
    }

    public ImageView[] getImageViews() {
        return mImageViews;
    }

    public boolean isClearMemory() {
        return mIsClearMemory;
    }

    public boolean isClearDiskCache() {
        return mIsClearDiskCache;
    }

    public int getFallback() {
        return mFallback;
    }

    public int getBlurValue() {
        return mBlurValue;
    }

    public boolean isBlurImage() {
        return mBlurValue > 0;
    }

    public int getImageRadius() {
        return mImageRadius;
    }

    public boolean isImageRadius() {
        return mImageRadius > 0;
    }

    public boolean isCrossFade() {
        return mIsCrossFade;
    }

    public boolean isCenterCrop() {
        return mIsCenterCrop;
    }

    public boolean isCenterInside() {
        return mIsCenterInside;
    }

    public boolean isCircleCrop() {
        return mIsCircleCrop;
    }

    public int getTargetWidth() {
        return mTargetWidth;
    }

    public int getTargetHeight() {
        return mTargetHeight;
    }

    public RequestOptions getRequestOptions() {
        return mRequestOptions;
    }

    public RequestListener getRequestListener() {
        return mRequestListener;
    }

    public boolean isDontAnimate() {
        return mIsDontAnimate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String mUrl;
        private Integer mResId;
        private File mFile;
        private Uri mUri;
        private Object mObject;
        private ImageView mImageView;
        private Target mTarget;
        private int mPlaceholder;
        private int mErrorPic;
        private int mFallback; //请求 url 为空,则使用此图片作为占位符
        private int mCacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
        private int mImageRadius;//图片每个圆角的大小
        private int mBlurValue;//高斯模糊值, 值越大模糊效果越大
        private BitmapTransformation mTransformation1;//glide用它来改变图形的形状
        private ImageView[] mImageViews;
        private boolean mIsCrossFade;//是否使用淡入淡出过渡动画
        private boolean mIsCenterCrop;//是否将图片剪切为 CenterCrop
        private boolean mIsCenterInside;
        private boolean mIsCircleCrop;//是否将图片剪切为圆形
        private boolean mIsClearMemory;//清理内存缓存
        private boolean mIsClearDiskCache;//清理本地缓存
        private int mTargetWidth, mTargetHeight;
        private RequestOptions mRequestOptions;
        private RequestListener mRequestListener;
        private boolean mIsDontAnimate;

        public Builder load(String url) {
            this.mUrl = url;
            return this;
        }

        public Builder load(@RawRes @DrawableRes Integer resId) {
            this.mResId = resId;
            return this;
        }

        public Builder load(File file) {
            this.mFile = file;
            return this;
        }

        public Builder load(Uri uri) {
            this.mUri = uri;
            return this;
        }

        public Builder load(Object o) {
            this.mObject = o;
            return this;
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

        public Builder cacheStrategy(@StrategyType int cacheStrategy) {
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
         *                       请使用 {@link #isCircleCrop()}, {@link #isCenterCrop()}, {@link #isImageRadius()} 替代
         *                       如果有其他自定义 BitmapTransformation 的需求, 请自行扩展 {@link BaseImageLoaderStrategy}
         */
        public Builder transform(BitmapTransformation transformation) {
            this.mTransformation1 = transformation;
            return this;
        }

        public Builder crossFade(boolean isCrossFade) {
            this.mIsCrossFade = isCrossFade;
            return this;
        }

        public Builder centerCrop(boolean isCenterCrop) {
            this.mIsCenterCrop = isCenterCrop;
            return this;
        }

        public Builder centerInside(boolean isCenterInside) {
            this.mIsCenterInside = isCenterInside;
            return this;
        }

        public Builder circleCrop(boolean isCircleCrop) {
            this.mIsCircleCrop = isCircleCrop;
            return this;
        }

        public Builder override(int targetWidth, int targetHeight) {
            this.mTargetWidth = targetWidth;
            this.mTargetHeight = targetHeight;
            return this;
        }

        public Builder clearMemory(boolean isClearMemory) {
            this.mIsClearMemory = isClearMemory;
            return this;
        }

        public Builder clearDiskCache(boolean isClearDiskCache) {
            this.mIsClearDiskCache = isClearDiskCache;
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

        public Builder dontAnimate(boolean isDontAnimate) {
            this.mIsDontAnimate = isDontAnimate;
            return this;
        }

        public ImageConfigImpl build() {
            return new ImageConfigImpl(this);
        }
    }
}
