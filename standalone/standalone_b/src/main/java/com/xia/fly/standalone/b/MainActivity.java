package com.xia.fly.standalone.b;

import com.xia.fly.module.b.ui.BFragment;
import com.xia.fly.ui.activities.ProxyActivity;
import com.xia.fly.ui.fragments.FlySupportFragment;

import androidx.annotation.NonNull;

public class MainActivity extends ProxyActivity {

    @NonNull
    @Override
    protected Class<? extends FlySupportFragment> setRootFragment() {
        return BFragment.class;
    }
}
