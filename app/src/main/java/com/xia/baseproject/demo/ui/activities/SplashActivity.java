package com.xia.baseproject.demo.ui.activities;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.xia.baseproject.demo.R;
import com.xia.baseproject.demo.helper.DialogHelper;
import com.xia.fly.ui.activities.SupportActivity;

import java.util.List;

import butterknife.OnClick;

/**
 * @author xia
 * @date 2018/8/16.
 */
public class SplashActivity extends SupportActivity {
    private boolean mIsRequestPermission = true;
    private boolean mIsToAppSetting = false;

    @Override
    public boolean isCheckNetWork() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void onLazyLoadData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsRequestPermission) {
            permission();
        }
        mIsRequestPermission = false;
        if (mIsToAppSetting) {
            mIsRequestPermission = true;
            mIsToAppSetting = false;
        }
    }

    private void permission() {
        PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                .rationale(shouldRequest -> DialogHelper.showRationaleDialog(getContext(), shouldRequest))
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        ActivityUtils.startActivity(MainActivity.class);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        DialogHelper.showOpenAppSettingDialog(getContext());
                    }
                })
                .request();
    }

    @Override
    public void onNetworkState(boolean isAvailable) {
        Log.e("xia", this + "    onNetworkState: " + isAvailable);
    }

    @Override
    public void onNetReConnect() {
        Log.e("xia", this + "    onNetReConnect: ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DialogHelper.APP_SETTINGS_CODE) {
            mIsToAppSetting = true;
        }
    }

    @OnClick(R.id.splash_jump_btn)
    public void onViewClicked(View view) {
        ActivityUtils.startActivity(MainActivity.class);
    }
}
