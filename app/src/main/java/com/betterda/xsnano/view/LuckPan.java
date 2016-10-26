package com.betterda.xsnano.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;


import com.betterda.xsnano.R;
import com.betterda.xsnano.util.UtilMethod;

/**
 * Created by lyf
 */
public class LuckPan extends View {


    //奖项的文字
    private String[] mStrs = new String[]{"单反相机", "IPAD", "恭喜发财",
            "iphone", "服装一套", "恭喜发财"};
    //奖项的图片
    private int[] mImgs = new int[]{R.mipmap.danfan, R.mipmap.ipad,
            R.mipmap.f015, R.mipmap.iphone, R.mipmap.meizi, R.mipmap.f040};
    //盘块的颜色
    private int[] mColor = new int[]{0xFFFFC300, 0XFFF17E01, 0xFFFFC300,
            0XFFF17E01, 0xFFFFC300, 0XFFF17E01};
    //盘块个数
    private int mItemCount = 6;
    //与图片对应的bitmap
    private Bitmap[] mImgsBitmap;
    //盘块的范围
    private RectF mRange;
    //直径
    private int mRadius;
    //绘制盘块的画笔
    private Paint mArcPaint;
    //绘制文本的画笔
    private Paint mTextPaint;
    //滚动的速度
    private double mSpeed;
    //开始的角度
    private volatile float mStartAngle = 0;
    //判断是否点击了停止按钮
    private boolean isShouldEnd;
    //转盘的中心位置
    private int mCenter;
    //最小的padding值,直接以左边为准
    private int mPadding;
    //背景图片
    private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg2);
    //开始按钮的图片,这个图片最好是在不同的分辨率下都放置
    private Bitmap mStartBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.start);

    //文字的大小
    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());






    public LuckPan(Context context, AttributeSet attrs) {
        super(context, attrs);

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setTextSize(mTextSize);


        //初始化奖励图片
        mImgsBitmap = new Bitmap[mItemCount];
        for (int i = 0; i < mItemCount; i++) {
            mImgsBitmap[i] = BitmapFactory.decodeResource(getResources(), mImgs[i]);
        }
    }


    public LuckPan(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//不调用这个方法不会执行ondraw
        //取较小值,设置为正方形
        int width = Math.min(getMeasuredHeight(), getMeasuredWidth());
        mPadding = getPaddingLeft();
        mRadius = width - mPadding * 2;
        mCenter = width / 2;
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //初始化盘块的范围
        mRange = new RectF(mPadding, mPadding, mPadding + mRadius, mPadding + mRadius);
        /*mStartBitmapHeight = mStartBitmap.getHeight();
        mStartBitmapWidth = mStartBitmap.getWidth();*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawS(canvas);

    }


    /**
     * 绘制的方法
     */
    private void drawS(Canvas mCanvas) {

        //绘制背景
        drawBg(mCanvas);

        //绘制盘块
        float tmpAngle = mStartAngle; //开始的角度
        float sweepAngle = 360 / mItemCount;//每个盘块的角度
        for (int i = 0; i < mItemCount; i++) {
            mArcPaint.setColor(mColor[i]);
            //绘制盘块
            mCanvas.drawArc(mRange, tmpAngle, sweepAngle, true, mArcPaint);
            //绘制文本
            drawText(mCanvas, tmpAngle, sweepAngle, mStrs[i]);
            //绘制icon
            drawIcon(mCanvas, tmpAngle, mImgsBitmap[i]);
            tmpAngle += sweepAngle;

        }


        /*//确定开始图片的位置
        Rect rect = new Rect(mCenter - mStartBitmapWidth / 2, mCenter - mStartBitmapHeight / 2,
                mCenter + mStartBitmapWidth / 2, mCenter + mStartBitmapHeight / 2);
        mCanvas.drawBitmap(mStartBitmap, null, rect, null);*/


    }

    private void drawIcon(Canvas mCanvas, float tmpAngle, Bitmap bitmap) {
        //设置图片的宽度
        int imgWidth = mRadius / 8;
        float angle = (float) ((tmpAngle + 360 / mItemCount / 2) * Math.PI / 180);
        //图片中心点的坐标
        int x = (int) (mCenter + mRadius / 2 / 2 * Math.cos(angle));
        int y = (int) (mCenter + mRadius / 2 / 2 * Math.sin(angle));
        //确定图片的位置
        Rect rect = new Rect(x - imgWidth / 2, y - imgWidth / 2, x + imgWidth / 2, y + imgWidth / 2);
        mCanvas.drawBitmap(bitmap, null, rect, null);

    }

    private void drawText(Canvas mCanvas, float tmpAngle, float sweepAngle, String mStr) {
        Path path = new Path();
        path.addArc(mRange, tmpAngle, sweepAngle);
        //利用水平偏移量使文字居中
        float textWidth = mTextPaint.measureText(mStr);
        int hOffset = (int) (mRadius * Math.PI / mItemCount / 2 - textWidth / 2);
        //竖直偏移量
        int vOffset = mRadius / 2 / 6;
        mCanvas.drawTextOnPath(mStr, path, hOffset, vOffset, mTextPaint);
    }

    /**
     * 绘制背景
     */
    private void drawBg(Canvas mCanvas) {


        mCanvas.drawColor(0xFFFFFFFF);
        mCanvas.drawBitmap(mBgBitmap, null, new Rect(mPadding / 2, mPadding / 2,
                getMeasuredHeight() - mPadding / 2, getMeasuredHeight() - mPadding / 2), null);

    }





}
