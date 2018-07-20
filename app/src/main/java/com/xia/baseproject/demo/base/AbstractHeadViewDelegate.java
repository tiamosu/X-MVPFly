package com.xia.baseproject.demo.base;

import android.view.View;
import android.widget.FrameLayout;

import com.xia.baseproject.demo.R;
import com.xia.baseproject.fragments.BaseDelegate;
import com.xia.baseproject.mvp.BaseMvpPresenter;

/**
 * @author xia
 * @date 2018/7/3.
 */
public abstract class AbstractHeadViewDelegate<P extends BaseMvpPresenter> extends BaseDelegate<P> {

    @Override
    protected void onCreateHeadView(FrameLayout headContainer) {
        final View headView = View.inflate(getContext(), R.layout.layout_head_view, null);
        headContainer.addView(headView);
    }
}
