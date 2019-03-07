package com.xia.fly.demo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import butterknife.BindView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xia.fly.demo.R
import com.xia.fly.demo.base.HeadViewFragment
import com.xia.fly.demo.mvp.presenter.HomePresenter
import com.xia.fly.demo.mvp.view.HomeView
import com.xia.fly.imageloader.ImageConfigImpl
import com.xia.fly.imageloader.TranscodeType
import com.xia.fly.ui.imageloader.ImageLoader
import com.xia.fly.utils.FragmentUtils
import me.yokeyword.fragmentation.AbstractSupportFragment

/**
 * @author xia
 * @date 2018/7/16.
 */
class HomeFragment : HeadViewFragment<HomePresenter>(), HomeView {
    @BindView(R.id.home_jump_btn)
    lateinit var mAppCompatButton: AppCompatButton
    @BindView(R.id.home_photo_iv)
    lateinit var mPhotoView: AppCompatImageView

    override fun isCheckNetWork(): Boolean {
        return true
    }

    override fun isLoadTitleBar(): Boolean {
        return false
    }

    override fun newP(): HomePresenter {
        return HomePresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        val str: String? = null
        val ss = str ?: "aa"
        Log.e("weixi", ss)
    }

    override fun initView() {
        mAppCompatButton.text = "跳转下一页"
    }

    override fun initEvent() {
        view?.findViewById<View>(R.id.home_jump_btn)?.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("Hello", "你好")
            getParentDelegate<AbstractSupportFragment>()
                    .start(FragmentUtils.newInstance(SecondFragment::class.java, bundle))
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
                        .into(mPhotoView)
                        .addListener(object : RequestListener<Any> {
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Any>?, isFirstResource: Boolean): Boolean {
                                return false
                            }

                            override fun onResourceReady(resource: Any?, model: Any?, target: Target<Any>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                return false
                            }
                        })
                        .build()
        )
    }

    override fun onNetReConnect() {
        Log.e("xia", "$this    onNetReConnect: ")
    }

    override fun onNetworkState(isAvailable: Boolean) {
        Log.e("xia", "$this    onNetworkState: $isAvailable")
    }

    override fun setData(content: String) {
//        Log.e("weixi", "content:$content")
    }

    override val boolean: Boolean
        get() = true

    override val num: Int
        get() = 99
}
