package com.xia.fly.ui.imageloader

import android.widget.ImageView

import com.bumptech.glide.request.target.Target

/**
 * 这里是图片加载配置信息的基类,定义一些所有图片加载框架都可以用的通用参数
 * 每个 [BaseImageLoaderStrategy] 应该对应一个 [ImageConfig] 实现类
 *
 * @author xia
 * @date 2018/9/17.
 */
open class ImageConfig {
    @JvmField
    var mObject: Any? = null//所要加载的资源
    @JvmField
    var mImageView: ImageView? = null
    @JvmField
    var mTarget: Target<*>? = null
    @JvmField
    var mPlaceholder: Int = 0//占位符
    @JvmField
    var mError: Int = 0//错误占位符
}
