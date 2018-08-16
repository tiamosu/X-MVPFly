package com.xia.baseproject.demo.ui.activities;

import android.content.Intent;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.xia.baseproject.demo.R;
import com.xia.baseproject.demo.helper.DialogHelper;
import com.xia.baseproject.ui.activities.SupportActivity;

import java.util.List;

/**
 * @author xia
 * @date 2018/8/16.
 */
public class SplashActivity extends SupportActivity {
    private boolean mIsRequestPermission = true;
    private boolean mIsToAppSetting = false;

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
                        ActivityUtils.finishActivity(SplashActivity.class);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DialogHelper.APP_SETTINGS_CODE) {
            mIsToAppSetting = true;
        }
    }
}
