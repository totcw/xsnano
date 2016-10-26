package com.betterda.xsnano.acitivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.betterda.xsnano.R;
import com.betterda.xsnano.view.LuckPan;

/**
 * 使用动画实现抽奖
 * Created by lyf
 */
public class LuckPanViewActivity extends Activity {
    //设置一个时间常量,指针转一圈所需要的时间，现设置为500毫秒
    private static final long ONE_WHEEL_TIME = 500;

    //开始转动时候的角度，初始值为0
    private int startDegree = 0;

    private LuckPan luckPan;//抽奖转盘
    private ImageView pointIv;


    private boolean isStop;//转盘是否停止

    //指针转圈圈数数据源
    private int[] laps = { 5, 7, 10, 15 };
    //指针所指向的角度数据源，因为有6个选项，所有此处是6个值
    private int[] angles = { 0, 60, 120, 180, 240, 300 };
    //转盘内容数组
    private String[] lotteryStr = { "索尼PSP", "10元红包", "谢谢参与", "DNF钱包",
            "OPPO MP3", "5元红包", };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_luckpanview);
        luckPan = (LuckPan) findViewById(R.id.luckPan);
        pointIv = (ImageView) findViewById(R.id.iv_luckpan);
        pointIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStop) {
                    isStop = true;
                    //0-3的随机数
                    int lap = laps[(int) (Math.random() * 4)];
                    //随机选择停在那个奖品上
                    int angle = angles[(int) (Math.random() * 6)];
                    //每次转圈角度增量 要旋转的角度加上 要停下的奖品的角度
                    int increaseDegree = lap * 360 + angle;
                    //初始化旋转动画，后面的四个参数是用来设置以自己的中心点为圆心转圈
                    RotateAnimation rotateAnimation = new RotateAnimation(
                            startDegree, startDegree + increaseDegree,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    //将最后的角度赋值给startDegree作为下次转圈的初始角度,如果不设置,下次开始的时候转盘会跳动到0不好看
                    startDegree += increaseDegree;
                    //计算动画播放总时间
                    long time = (lap + angle / 360) * ONE_WHEEL_TIME;
                    //设置动画播放时间
                    rotateAnimation.setDuration(time);
                    //设置动画播放完后，停留在最后一帧画面上
                    rotateAnimation.setFillAfter(true);
                    //设置动画的加速行为，是先加速后减速
                    rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                    //设置动画的监听器
                    rotateAnimation.setAnimationListener(al);
                    //开始播放动画
                    luckPan.startAnimation(rotateAnimation);
                }
            }
        });
    }


    //监听动画状态的监听器
    private Animation.AnimationListener al = new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            isStop = false;
            //提示中了什么奖
            String name = lotteryStr[startDegree % 360 / 60];
            Toast.makeText(LuckPanViewActivity.this, name, Toast.LENGTH_LONG).show();
        }
    };



}
