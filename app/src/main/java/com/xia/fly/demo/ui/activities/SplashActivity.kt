package com.xia.fly.demo.ui.activities

import android.content.Intent
import android.util.Log
import butterknife.OnClick
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.xia.fly.demo.R
import com.xia.fly.demo.helper.DialogHelper
import com.xia.fly.mvp.BaseMvpPresenter
import com.xia.fly.ui.activities.SupportActivity

/**
 * @author xia
 * @date 2018/8/16.
 */
class SplashActivity<P : BaseMvpPresenter<*>> : SupportActivity<P>() {
    private var mIsRequestPermission = true
    private var mIsToAppSetting = false

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun newP(): P? {
        return null
    }

    override fun initData() {
//        Log.e("weixi", "initData")
    }

    override fun initView() {}

    override fun initEvent() {}

    override fun onLazyLoadData() {}

    override fun onResume() {
        super.onResume()
        if (mIsRequestPermission) {
            permission()
        }
        mIsRequestPermission = false
        if (mIsToAppSetting) {
            mIsRequestPermission = true
            mIsToAppSetting = false
        }
    }

    private fun permission() {
        PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                .rationale { shouldRequest -> DialogHelper.showRationaleDialog(context, shouldRequest) }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        ActivityUtils.startActivity(MainActivity::class.java)
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>,
                                          permissionsDenied: List<String>) {
                        DialogHelper.showOpenAppSettingDialog(context)
                    }
                })
                .request()
    }

    override fun onNetworkState(isAvailable: Boolean) {
        Log.e("xia", "$this    onNetworkState: $isAvailable")
    }

    override fun onNetReConnect() {
        Log.e("xia", "$this    onNetReConnect: ")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DialogHelper.APP_SETTINGS_CODE) {
            mIsToAppSetting = true
        }
    }

    @OnClick(R.id.splash_jump_btn)
    fun onViewClicked() {
        ActivityUtils.startActivity(MainActivity::class.java)
    }
}
