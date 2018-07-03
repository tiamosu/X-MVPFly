package com.xia.baseproject.demo.base;

import android.view.View;
import android.widget.FrameLayout;

import com.xia.baseproject.demo.R;
import com.xia.baseproject.fragments.BaseDelegate;

/**
 * @author xia
 * @date 2018/7/3.
 */
public abstract class AbstractHeadViewDelegate extends BaseDelegate {

    @Override
    protected void onCreateHeadView(FrameLayout rootView) {
        final View headView = View.inflate(getContext(), R.layout.layout_head_view, null);
        rootView.addView(headView);
    }
}
