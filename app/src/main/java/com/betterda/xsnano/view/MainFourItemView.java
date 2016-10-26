package com.betterda.xsnano.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.util.AnimationUtil;


/**
 * 分类,赛选的自定义view
 *
 * @author Administrator
 */
public class MainFourItemView extends RelativeLayout {

    private ImageView iv_bottom; //图片
    private TextView tv_bottom; //文字

    private int normaliv; //图片的默认图片
    private int selectiv;//图片的选中图片

    private int normalColor; //默认的文字颜色
    private int selectColor; //选中的文字颜色

    private boolean isSelected;//是否被选中
    private Animation anim;

    @Override
    public boolean isSelected() {
        return isSelected;
    }



    public MainFourItemView(Context context) {
        this(context, null);
    }

    public MainFourItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 将布局文件和 代码进行绑定
        View.inflate(context, R.layout.item_main_four, this);

        iv_bottom = (ImageView) findViewById(R.id.iv_main_four);
        tv_bottom = (TextView) findViewById(R.id.tv_main_four);
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
     * 设置文字的背景颜色
     *
     * @param normalColor
     * @param selectColor
     */
    public void setLineBackground(int normalColor, int selectColor) {
        this.normalColor = normalColor;
        this.selectColor = selectColor;

        tv_bottom.setTextColor(normalColor);

    }

    /**
     * 设置是否选中
     *
     * @param selected
     */
    public void setTabSelected(boolean selected) {
        this.isSelected = selected;
        if (selected) {


            // 创建一个淡入动画
            anim = new RotateAnimation(0f,180f, Animation.RELATIVE_TO_SELF,
                    0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            anim.setFillAfter(true);//动画执行完后是否停留在执行完的状态
            anim.setDuration(200);
            tv_bottom.setTextColor(selectColor);
            iv_bottom.setBackgroundResource(selectiv);
            iv_bottom.startAnimation(anim);
        } else {
            //将动画取消,否则在5.0以上会有问题
            if (null != anim){

                anim.cancel();
                iv_bottom.clearAnimation();
            }
            tv_bottom.setTextColor(normalColor);
            iv_bottom.setBackgroundResource(normaliv);
        }
    }



}
