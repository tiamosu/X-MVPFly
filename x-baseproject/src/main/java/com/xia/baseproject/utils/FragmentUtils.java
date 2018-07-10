package com.xia.baseproject.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author xia
 * @date 2018/7/10.
 */
public final class FragmentUtils {

    @SuppressWarnings("unchecked")
    public static <T extends ISupportFragment> T newInstance(
            final Class cls, final Bundle bundle) {
        try {
            final T t = (T) cls.newInstance();
            if (bundle != null && !bundle.isEmpty()) {
                ((Fragment) t).setArguments(bundle);
                t.putNewBundle(bundle);
            }
            return t;
        } catch (IllegalAccessException ignored) {
        } catch (java.lang.InstantiationException ignored) {
        }
        return null;
    }
}
