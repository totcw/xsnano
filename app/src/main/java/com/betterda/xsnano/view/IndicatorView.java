package com.betterda.xsnano.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;


/**
 * 主界面底部的自定义View
 *
 * @author Administrator
 */
public class IndicatorView extends LinearLayout {
   // private View bottom_line;  //线
    private ImageView iv_bottom; //图片
    private TextView tv_bottom; //文字
    private int normalColor; //线的默认图片
    private int selectColor;//线的选中图片
    private int normaliv; //图片的默认图片
    private int selectiv;//图片的选中图片

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 将布局文件和 代码进行绑定
        View.inflate(context, R.layout.bottom_layout, this);
      //  bottom_line = findViewById(R.id.bottom_line);
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tv_bottom.setText(title);
    }

    /**
     * 设置图片的背景图片
     *
     * @param normaliv
     * @param selectiv
     */
    public void setIvBackground(int normaliv, int selectiv) {
        this.normaliv = normaliv;
        this.selectiv = selectiv;
        //设置为默认的颜色
        iv_bottom.setBackgroundResource(normaliv);

    }

    /**
     * 设置线和文字的背景颜色
     *
     * @param normalColor
     * @param selectColor
     */
    public void setLineBackground(int normalColor, int selectColor) {
        this.normalColor = normalColor;
        this.selectColor = selectColor;
        //设置为默认的颜色
     //   bottom_line.setBackgroundColor(normalColor);
        tv_bottom.setTextColor(normalColor);

    }

    /**
     * 设置是否选中
     *
     * @param selected
     */
    public void setTabSelected(boolean selected) {
        if (selected) {
          //  bottom_line.setBackgroundColor(selectColor);
          tv_bottom.setTextColor(selectColor);
            iv_bottom.setBackgroundResource(selectiv);
        } else {
          //  bottom_line.setBackgroundColor(normalColor);
            tv_bottom.setTextColor(normalColor);
            iv_bottom.setBackgroundResource(normaliv);
        }
    }


}
