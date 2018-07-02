package com.xia.baseproject.constant;

import com.blankj.utilcode.util.Utils;

/**
 * @author xia
 * @date 2018/7/2.
 */
public final class BaseConstant {

    public static String getAuthority() {
        return Utils.getApp().getPackageName() + ".utilcode.provider";
    }
}
