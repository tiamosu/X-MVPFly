package com.xia.fly.module.a.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.xia.fly.imageloader.ImageConfigImpl
import com.xia.fly.imageloader.TranscodeType
import com.xia.fly.integration.handler.WeakHandler
import com.xia.fly.module.a.R
import com.xia.fly.module.a.R2
import com.xia.fly.module.a.mvp.presenter.APresenter
import com.xia.fly.module.a.mvp.view.AView
import com.xia.fly.module.common.base.BaseFragment
import com.xia.fly.module.common.router.Router
import com.xia.fly.module.common.router.RouterConstant
import com.xia.fly.ui.imageloader.ImageLoader
import com.xia.fly.utils.FragmentUtils
import me.yokeyword.fragmentation.SupportFragment
import java.lang.ref.WeakReference

/**
 * @author weixia
 * @date 2019/3/15.
 */
@Route(path = RouterConstant.FRAGMENT_A)
class AFragment : BaseFragment<APresenter>(), AView {
    @BindView(R2.id.jump_btn)
    lateinit var mAppCompatButton: AppCompatButton
    @BindView(R2.id.photo_iv)
    lateinit var mImageView: AppCompatImageView

    override fun isLoadTitleBar(): Boolean {
        return false
    }

    override fun newP(): APresenter? {
        return APresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_a
    }

    override fun initData() {
        val str: String? = null
        val ss = str ?: "aa"
        Log.e("weixi", ss)

        val bundle: Bundle? = null
        Log.e("weixi", "${bundle?.isEmpty} zz ${bundle?.javaClass?.name}")

        val callback: Handler.Callback? = null
        Log.e("weixi", "" + WeakReference<Handler.Callback>(callback))

        WeakHandler().post(Runnable {
            Log.e("weixi", "run")
        })

        Log.e("weixi", "cls:" + Router.obtainFragmentB())
    }

    override fun initView() {
        mAppCompatButton.text = "跳转下一页"
    }

    override fun initEvent() {
        mAppCompatButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("Hello", "你好")
            val fragmentB = Router.obtainFragmentB()
            getParentDelegate<SupportFragment>()
                    .start(FragmentUtils.newInstance(fragmentB, bundle))
        }
    }

    override fun onVisibleLazyLoad() {
        super.onVisibleLazyLoad()
        //        getP().downloadFile();
        //        new Handler().postDelayed(() -> getP().load(), 3000);

        ImageLoader.loadImage(
                ImageConfigImpl
                        .load(R.mipmap.ic_launcher)
                        .`as`(TranscodeType.AS_DRAWABLE)
                        .crossFade()
                        .centerCrop()
                        .circleCrop()
                        .into(object : DrawableImageViewTarget(mImageView) {
                            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                super.onResourceReady(resource, transition)
                                mAppCompatButton.text = "加载完毕，点击进入下一页"
                            }
                        })
                        .build()
        )

//        ImageLoader.loadImage(
//                ImageConfigImpl
//                        .load(R.mipmap.ic_launcher)
//                        .`as`(TranscodeType.AS_DRAWABLE)
//                        .crossFade()
//                        .centerCrop()
//                        .circleCrop()
//                        .into(mImageView)
//                        .addListener(object : RequestListener<Any> {
//                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Any>?, isFirstResource: Boolean): Boolean {
//                                return false
//                            }
//
//                            override fun onResourceReady(resource: Any?, model: Any?, target: Target<Any>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                                return false
//                            }
//                        })
//                        .build()
//        )
    }

    override fun onNetworkState(isAvailable: Boolean) {
        Log.e("xia", "$this    onNetworkState: $isAvailable")
    }

    override fun onNetReConnect() {
        Log.e("xia", "$this    onNetReConnect: ")
    }

    override fun setData(content: String) {
//        Log.e("weixi", "content:$content")
    }

    override val boolean: Boolean
        get() = true

    override val num: Int
        get() = 99
}
