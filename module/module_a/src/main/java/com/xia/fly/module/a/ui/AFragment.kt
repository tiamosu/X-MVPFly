package com.xia.fly.module.a.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.xia.fly.imageloader.ImageConfigImpl
import com.xia.fly.imageloader.TranscodeType
import com.xia.fly.integration.handler.WeakHandler
import com.xia.fly.module.a.R
import com.xia.fly.module.a.mvp.presenter.APresenter
import com.xia.fly.module.a.mvp.view.AView
import com.xia.fly.module.common.base.BaseFragment
import com.xia.fly.module.common.router.Router
import com.xia.fly.ui.imageloader.ImageLoader
import com.xia.fly.utils.FragmentUtils
import kotlinx.android.synthetic.main.fragment_a.*
import me.yokeyword.fragmentation.SupportFragment
import java.lang.ref.WeakReference

/**
 * @author weixia
 * @date 2019/3/15.
 */
class AFragment : BaseFragment<APresenter>(), AView {

    override fun isLoadTitleBar(): Boolean {
        return false
    }

    override fun newP(): APresenter {
        return APresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_a
    }

    override fun initData() {
        val str: String? = null
        val ss = str ?: "aa"
        Log.e("susu", ss)

        val bundle: Bundle? = null
        Log.e("susu", "${bundle?.isEmpty} zz ${bundle?.javaClass?.name}")

        val callback: Handler.Callback? = null
        Log.e("susu", "" + WeakReference<Handler.Callback>(callback))

        WeakHandler().post(Runnable {
            Log.e("susu", "run")
        })

        Log.e("susu", "cls:" + Router.obtainFragmentBCls())

        val equals: String? = null
        Log.e("susu", "boolean:" + ("" == equals))
    }

    override fun initView() {
        jump_btn.text = "跳转下一页"
    }

    override fun initEvent() {
        applyWidgetClickListener(
                rootView?.findViewById(R.id.jump_btn),
                rootView?.findViewById(R.id.test_btn)
        )
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
                        .into(object : DrawableImageViewTarget(photo_iv) {
                            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                super.onResourceReady(resource, transition)
                                jump_btn.text = "加载完毕，点击进入下一页"
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
//        Log.e("susu", "content:$content")
    }

    override val boolean: Boolean
        get() = true

    override val num: Int
        get() = 99

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.jump_btn -> {
                val bundle = Bundle()
                bundle.putString("Hello", "你好")
                val fragmentB = Router.obtainFragmentBCls()
                getParentDelegate<SupportFragment>()
                        .start(FragmentUtils.newInstance(fragmentB, bundle)!!)
            }
            R.id.test_btn -> {
                p.test()
            }
        }
    }
}
