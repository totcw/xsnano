package com.betterda.xsnano.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.xsnano.R;

/**
 * 加载的页面
 * Created by Administrator on 2016/5/11.
 */
public class LoadingPager extends FrameLayout {
    private LoadingView2 loadview_pager;//加载页面
    private FrameLayout frame_error, frame_empty;//加载错误,加载为空页面
    private TextView tv_empty;//数据为空的文字
    private ImageView iv_empty;//数据为空的图片

    public LoadingPager(Context context) {
        this(context, null);
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.loading_pager, this);
        loadview_pager = (LoadingView2) findViewById(R.id.loadview_pager);
        frame_empty = (FrameLayout) findViewById(R.id.frame_empty);
        frame_error = (FrameLayout) findViewById(R.id.frame_error);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        iv_empty = (ImageView) findViewById(R.id.iv_empty);

    }

    /**
     * 设置数据为空的显示文字
     */
    public void setTitle(String title) {
        if (null != tv_empty) {
            tv_empty.setText(title);
        }
    }

    /**
     * 设置点击事件
     *
     * @param listener
     */
    public void setonEmptyClickListener(OnClickListener listener) {
        if (frame_empty != null) {
            frame_empty.setOnClickListener(listener);
        }
    }

    public void setonErrorClickListener(OnClickListener listener) {
        if (frame_error != null) {
            frame_error.setOnClickListener(listener);
        }
    }

    public void hide() {
        if (null != frame_empty && null != frame_error && loadview_pager != null) {
            frame_empty.setVisibility(View.INVISIBLE);
            frame_error.setVisibility(View.INVISIBLE);
            loadview_pager.setVisibility(View.INVISIBLE);
            loadview_pager.stopAnim();
        }
    }

    public void setEmptyVisable() {
        hide();
        if (null != frame_empty) {

            frame_empty.setVisibility(View.VISIBLE);
        }
    }
    public void setErrorVisable() {
        hide();
        if (null != frame_error) {

            frame_error.setVisibility(View.VISIBLE);
        }
    }
    public void setLoadVisable() {
        hide();
        if (null != loadview_pager) {

            loadview_pager.setVisibility(View.VISIBLE);
            loadview_pager.startAnim();
        }
    }

    /**
     * 设置数据为空时的背景图片
     * @param resid
     */
    public void setEmptyBackground(int resid) {
        if (iv_empty != null) {
            iv_empty.setBackgroundResource(resid);
        }
    }

    /**
     * 设置数据为空时的文字
     */

    public void setEmptyText(String string) {
        if (tv_empty != null) {
            tv_empty.setText(string);
        }
    }



}
