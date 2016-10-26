package com.betterda.xsnano.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 用来首页测量首页需要隐藏的topview的高度
 * Created by Administrator on 2016/6/23.
 */
public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        int height = 0;
        for (int i = 0; i < count; ++i)
        {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec,heightMeasureSpec);
            height += childView.getMeasuredHeight();
        }

    }
}
