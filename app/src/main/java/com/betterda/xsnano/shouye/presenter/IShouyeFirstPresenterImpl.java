package com.betterda.xsnano.shouye.presenter;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.betterda.xsnano.R;
import com.betterda.xsnano.animation.CubeTransformer;
import com.betterda.xsnano.interfac.ParserJsonInterface;
import com.betterda.xsnano.shouye.model.LunBoTu;
import com.betterda.xsnano.shouye.view.IShouyeView;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.UtilMethod;
import com.facebook.drawee.view.SimpleDraweeView;


import org.xutils.http.RequestParams;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 负责与首页的轮播图交互
 * Created by Administrator on 2016/4/26.
 */
public class IShouyeFirstPresenterImpl implements IShouyeFirstPresenter, ViewPager.OnPageChangeListener {
    private IShouyeView iShouyeView;
    private Fragment fragment;
    private List<LunBoTu> stringList; //轮播图图片的容器
    private int currentIndex;
    private InternalHandler mHandler;


    public IShouyeFirstPresenterImpl(IShouyeView iShouyeView, Fragment fragment) {
        this.iShouyeView = iShouyeView;
        this.fragment = fragment;

    }

    /**
     * 获取轮播图的数据
     */
    public void getLunBoTuData() {
        stringList = new ArrayList<>();

        RequestParams params = new RequestParams(Constants.URL_LUNBO);
        params.addBodyParameter("regionId", Constants.regiondId);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                UtilMethod.parSerJson(s, new ParserJsonInterface() {
                    @Override
                    public void parser(Map<String, Object> map) {
                        if (map != null) {
                            LunBoTu lunBoTu = new LunBoTu();
                            lunBoTu.setUrl(UtilMethod.url(map.get("picture").toString()));
                            lunBoTu.setId(map.get("id").toString());
                            if (stringList != null) {
                                stringList.add(lunBoTu);
                            }
                        }

                    }
                });

                if (iShouyeView != null) {
                    iShouyeView.setLunBoAdapter();
                  //  iShouyeView.getViewPager().setPageTransformer(true,new CubeTransformer());

                }
                if (iShouyeView != null) {
                    if (iShouyeView.getImageviewMianFirst() != null) {
                        iShouyeView.getImageviewMianFirst().setVisibility(View.INVISIBLE);
                    }

                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (iShouyeView != null) {
                    if (iShouyeView.getImageviewMianFirst() != null) {
                        iShouyeView.getImageviewMianFirst().setVisibility(View.VISIBLE);
                    }

                }

            }
        });


    }



    public List<LunBoTu> getStringList() {
        return stringList;
    }

    public void cratePoint() {
        if (null != stringList && stringList.size() > 0) {
            int size = stringList.size();
            // 添加图片
            for (int i = 0; i < size; i++) {
                // 设置圆点
                View viewPoint = new View(iShouyeView.getmActivity());
                viewPoint.setBackgroundResource(R.drawable.point_background);
                int weight = UtilMethod.dip2px(iShouyeView.getmActivity(), 5);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(weight, weight);
                lp.leftMargin = weight;
                viewPoint.setLayoutParams(lp);
                viewPoint.setEnabled(false);
                iShouyeView.getLpoint().addView(viewPoint);
            }
            View childAt = iShouyeView.getLpoint().getChildAt(0);
            if (null != childAt) {
                childAt.setEnabled(true);
            }
        }


    }

    public void createHandler() {
        iShouyeView.getViewPager().setOnPageChangeListener(this);

        if (mHandler == null) {
            if (iShouyeView != null && iShouyeView.getmActivity() != null)
                mHandler = new InternalHandler(fragment, iShouyeView.getmActivity());
        }

        mHandler.removeCallbacksAndMessages(null); // 把所有的消息和任务清空
        mHandler.postDelayed(new AutoSwitchPagerRunnable(iShouyeView.getmActivity()), 2000);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (null != iShouyeView.getLpoint()) {
            View childAt = iShouyeView.getLpoint().getChildAt(currentIndex);
            if (null != childAt) {
                childAt.setEnabled(false);
            }
            if (stringList.size() > 0) {

                View childAt1 = iShouyeView.getLpoint().getChildAt(position % stringList.size());
                if (null != childAt1) {
                    childAt1.setEnabled(true);
                }
                currentIndex = position % stringList.size();
            }
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void start() {
        getLunBoTuData();
    }


    class InternalHandler extends Handler {

        private WeakReference<Activity> mContextWeakReference;
        private WeakReference<Fragment> mfragment; //使用肉引用来保存这个对象

        public InternalHandler(Fragment fragment, Activity mContext) {
            mfragment = new WeakReference<Fragment>(fragment);
            mContextWeakReference = new WeakReference<Activity>(mContext);
        }

        @Override
        public void handleMessage(Message msg) {
            if (null != iShouyeView && null != mfragment && null != iShouyeView.getViewPager() && null != mfragment.get()) { //当fragment存在时
                int currentItem = iShouyeView.getViewPager().getCurrentItem() + 1;
                if (null != stringList) {
                    if (stringList.size() != 0) {
                        //这里取余是为了当数据超过适配器的长度,时候回到第一页,避免异常. 其实也是会突然跳到第一页,只是不一般达不到这个值
                        iShouyeView.getViewPager().setCurrentItem(currentItem % iShouyeView.getViewPager().getAdapter().getCount(),true);
                    }
                }
                if (null != mHandler) {

                    mHandler.postDelayed(new AutoSwitchPagerRunnable(iShouyeView.getmActivity()), 2000);
                }
            }

        }

    }

    /**
     * @author andong 自动切换页面任务类
     */
    class AutoSwitchPagerRunnable implements Runnable {
        private WeakReference<Activity> mContextWeakReference;

        public AutoSwitchPagerRunnable(Activity activity) {
            this.mContextWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void run() {
            mHandler.obtainMessage().sendToTarget();
        }
    }


    @Override
    public View ctreaImageView(final int position) {
        if (null != iShouyeView && iShouyeView.getmActivity() != null) {
            SimpleDraweeView iv = (SimpleDraweeView) View.inflate(iShouyeView.getmActivity(), R.layout.lunbotu, null);
            iv.setOnTouchListener(new ViewPagerTouchListener());
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   // UtilMethod.Toast(iShouyeView.getmActivity(), "" + position);
                }
            });
            // 从服务器获取图片
            if (null != stringList) {
                String url = stringList.get(position).getUrl();

                if (!TextUtils.isEmpty(url)) {

                    Uri uri = Uri.parse(url);
                    iv.setImageURI(uri);
                }
            }
            return iv;
        }
        return null;
    }

    @Override
    public void onDestroy() {
        //将handler关闭防止 内存泄漏
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null); // 把所有的消息和任务清空
            mHandler = null;
        }
    }

    /**
     * 触摸控制viewpager的切换
     *
     * @author Administrator
     */
    class ViewPagerTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (null != mHandler) {

                        mHandler.removeCallbacksAndMessages(null);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (null != mHandler) {

                        mHandler.postDelayed(new AutoSwitchPagerRunnable(iShouyeView.getmActivity()), 2000);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:// 加这个 防止 和下拉刷新冲突
                    if (null != mHandler) {

                        mHandler.postDelayed(new AutoSwitchPagerRunnable(iShouyeView.getmActivity()), 2000);
                    }
                    break;
            }
            return false;
        }
    }
}
