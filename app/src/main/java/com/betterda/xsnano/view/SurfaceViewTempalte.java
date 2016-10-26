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
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.betterda.xsnano.R;

/**
 * surfaceView模版类,抽奖类
 * Created by Administrator on 2016/4/22.
 */
public class SurfaceViewTempalte extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;

    /**
     * 用来绘制的线程
     */
    private Thread t;

    /**
     * 线程的控制开关
     */
    private boolean isRunning;

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
    //文字的大小
    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());

    private OnChangedListener listener;


    public SurfaceViewTempalte(Context context) {
        this(context, null);
    }

    public SurfaceViewTempalte(Context context, AttributeSet attrs) {
        super(context, attrs);
        System.out.println("start");
        mHolder = getHolder();
        //管理surface的生命周期
        mHolder.addCallback(this);
        //设置获取焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //设置常亮
        setKeepScreenOn(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //取较小值,设置为正方形
        int width = Math.min(getMeasuredHeight(), getMeasuredWidth());
        mPadding = getPaddingLeft();
        mRadius = width - mPadding * 2;
        mCenter = width / 2;
        setMeasuredDimension(width, width);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //surface创建的方法,开启线程绘制

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setTextSize(mTextSize);

        //初始化盘块的范围
        mRange = new RectF(mPadding, mPadding, mPadding + mRadius, mPadding + mRadius);
        //初始化奖励图片
        mImgsBitmap = new Bitmap[mItemCount];
        for (int i = 0; i < mItemCount; i++) {
            mImgsBitmap[i] = BitmapFactory.decodeResource(getResources(), mImgs[i]);
        }

        //开启线程
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {

            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            long time = end - start;
            if (time < 50) {
                try {
                    Thread.sleep(50 - time);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 绘制的方法
     */
    private void draw() {
        try {
            if (null != mHolder) {
                mCanvas = mHolder.lockCanvas();
            }
            if (null != mCanvas) {
                //绘制背景
                drawBg();
                //绘制盘块
                float tmpAngle = mStartAngle; //开始的角度
                float sweepAngle = 360 / mItemCount;//每个盘块的角度
                for (int i = 0; i < mItemCount; i++) {
                    mArcPaint.setColor(mColor[i]);
                    //绘制盘块
                    mCanvas.drawArc(mRange, tmpAngle, sweepAngle, true, mArcPaint);
                    //绘制文本
                    drawText(tmpAngle, sweepAngle, mStrs[i]);
                    //绘制icon
                    drawIcon(tmpAngle, mImgsBitmap[i]);
                    tmpAngle += sweepAngle;

                }
                //让角度不断变化,转盘就旋转起来了
                mStartAngle += mSpeed;
                //点击了停止
                if (isShouldEnd) {
                    //让速度递减
                    mSpeed -= 1;

                }



                if (mSpeed <= 0) {

                    mSpeed = 0;

                    if (isShouldEnd ) {

                        //当停下的时候回调changed方法,更换图片
                        if (null != listener) {
                            listener.changed();
                        }
                    }
                    isShouldEnd = false;

                }

            }
        } catch (Exception e) {

        } finally {
            if (null != mCanvas && null != mHolder) {
                //释放
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }

    /**
     * 开始
     *
     * @param index 控制停下的位置
     */
    public void luckStart(int index) {

        //计算每一项的角度
        float angle = 360 / mItemCount;
        //index的范围
        float from = 270 - (index + 2) * angle;
        float end = from + angle;

        //设置停下的位置在这个区间距离
        float targeFrom = 4 * 360 + from;
        float targeEnd = 4 * 360 + end;

        //设置要停在这个区间的速度
        float v1 = (float) ((-1 + Math.sqrt(1 + 8 * targeFrom)) / 2);
        float v2 = (float) ((-1 + Math.sqrt(1 + 8 * targeEnd)) / 2);

        double d = Math.random() * (v2 - v1);

        //防止停在边线
        while (d == 0) {
            d = Math.random() * (v2 - v1);
        }

        mSpeed = v1 + d;

        isShouldEnd = false;
    }

    /**
     * 停止
     */
    public void luckEnd() {
        //为了控制停下的角度,必须将mstartAngle设置为0,因为从0开始计算
        mStartAngle = 0;

        isShouldEnd = true;
    }

    /**
     * 是否还在旋转
     *
     * @return
     */
    public boolean isStart() {
        return mSpeed != 0;
    }

    /**
     * 是否按了停止
     *
     * @return
     */
    public boolean isShouldEnd() {
        return isShouldEnd;
    }

    private void drawIcon(float tmpAngle, Bitmap bitmap) {
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

    private void drawText(float tmpAngle, float sweepAngle, String mStr) {
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
    private void drawBg() {


        mCanvas.drawColor(0xFFFFFFFF);
        mCanvas.drawBitmap(mBgBitmap, null, new Rect(mPadding / 2, mPadding / 2,
                getMeasuredHeight() - mPadding / 2, getMeasuredHeight() - mPadding / 2), null);

    }

   public interface OnChangedListener {
        public void changed();
    }

    public void setListener(OnChangedListener listener) {
        this.listener = listener;
    }
}
