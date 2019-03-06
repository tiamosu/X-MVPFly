package com.xia.fly.demo.ui.fragments

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import butterknife.BindView
import butterknife.OnClick
import com.xia.fly.demo.R
import com.xia.fly.demo.base.HeadViewFragment
import com.xia.fly.mvp.BaseMvpPresenter
import me.yokeyword.fragmentation.ISupportFragment

/**
 * @author xia
 * @date 2018/7/3.
 */
class MainFragment : HeadViewFragment<BaseMvpPresenter<*>>() {
    @BindView(R.id.main_user_id_edit)
    lateinit var mUserIdEditText: AppCompatEditText
    @BindView(R.id.main_user_psd_edit)
    lateinit var mUserPsdEditText: AppCompatEditText

    private val mFragments = arrayOfNulls<ISupportFragment>(1)

    override fun isCheckNetWork(): Boolean {
        return false
    }

    override fun newP(): BaseMvpPresenter<*>? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initData() {
        val firstFragment = findChildFragment(HomeFragment::class.java)
        if (firstFragment == null) {
            mFragments[0] = HomeFragment()
            loadMultipleRootFragment(R.id.main_fl, 0, *mFragments)
        } else {
            mFragments[0] = firstFragment
        }
    }

    override fun initView() {}

    override fun initEvent() {}

    @OnClick(R.id.main_login_btn, R.id.main_open_btn)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.main_login_btn -> {
            }
            R.id.main_open_btn -> {
            }
        }
    }
}
