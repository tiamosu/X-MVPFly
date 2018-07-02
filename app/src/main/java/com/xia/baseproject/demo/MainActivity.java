package com.xia.baseproject.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.xia.baseproject.demo.helper.DialogHelper;

import java.util.List;

/**
 * @author xia
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    private boolean mIsRequestPermission = true;
    private boolean mIsToAppSetting = false;

    private void permission() {
        PermissionUtils.permission(PermissionConstants.PHONE, PermissionConstants.STORAGE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(final ShouldRequest shouldRequest) {
                        LogUtils.dTag("weixi", "rationale");
                        DialogHelper.showRationaleDialog(shouldRequest);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        LogUtils.dTag("weixi", permissionsGranted);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
//                        mIsRequest = true;
//                        if (!permissionsDeniedForever.isEmpty()) {
                        DialogHelper.showOpenAppSettingDialog(MainActivity.this);
//                        }
                        LogUtils.dTag("weixi", permissionsDeniedForever, permissionsDenied);
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
