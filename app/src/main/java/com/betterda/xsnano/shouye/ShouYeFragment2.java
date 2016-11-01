package com.betterda.xsnano.shouye;

import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.home.HomeActivity;
import com.betterda.xsnano.message.MessageActivity;
import com.betterda.xsnano.sale.SaleActivity;
import com.betterda.xsnano.search.SearchActivity;
import com.betterda.xsnano.shouye.adapter.LunBoTuAdapter;
import com.betterda.xsnano.shouye.presenter.IShouyeFirstPresenter;
import com.betterda.xsnano.shouye.presenter.IShouyeFirstPresenterImpl;
import com.betterda.xsnano.shouye.presenter.IShouyeFourPresenter;
import com.betterda.xsnano.shouye.presenter.IShouyeFourPresenterImpl;
import com.betterda.xsnano.shouye.presenter.IShouyePresenter;
import com.betterda.xsnano.shouye.presenter.IShouyePresenterImpl;
import com.betterda.xsnano.shouye.presenter.IShouyeSecondPresenter;
import com.betterda.xsnano.shouye.presenter.IShouyeSecondPresenterImpl;
import com.betterda.xsnano.shouye.presenter.IShouyeThreePresenter;
import com.betterda.xsnano.shouye.presenter.IShouyeThreePresenterImpl;
import com.betterda.xsnano.shouye.view.IShouyeView;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.MainFourView;
import com.betterda.xsnano.view.MyRecycleView;
import com.betterda.xsnano.view.ScrollYScrollView;

/**
 * 负责首页的view事件处理
 * Created by Administrator on 2016/4/21.
 */
public class ShouYeFragment2 extends BaseFragment implements IShouyeView, View.OnClickListener, ScrollYScrollView.OnScrollListener {
    private LinearLayout linear_shouye;
    private ScrollYScrollView myscrollview;
    private LinearLayout linear_hide_shouye;//存放前面3个界面
    private FrameLayout frame_shouye2_three; //存放第4个界面 分类
    private MyRecycleView rv_shouye;
    private ViewPager vpager_lunbotu; //轮播图
    private View view_lunbotu; //头布局
    private View view_second;//第二个头布局
    private MainFourView view_three; //第三个头布局
    private View view_four;//第4个布局
    private LinearLayout ll_points;//点的布局
    private LinearLayout linear_location;//定位按钮
    private TextView tv_shouye_city;//显示城市
    private ListView mainlist; //显示popupwindow门店的listview
    private ListView morelist; //显示popupwindow门店更多订单listview
    private RecyclerView recyclerView, rv_shaixuan;
    private ImageView iv_shouye_search;//搜索
    private ImageView iv_shouye_more;//更多
    private RelativeLayout relative_tjq, relative_zpjy, relative_qcjy, relative_xiuc, relative_jyz,
            relative_xic, relative_xhfw, relative_dj,relative_shouye2_title;
    private ImageView iv_jbdb, iv_ykggl, iv_gcwy, iv_mrtm, iv_main_first;
    private LoadingPager loadingPager;
    private FrameLayout frame_shouye;
    private RelativeLayout relative_three_xiche, relative_three_meirong, relative_three_yanghu, relative_three_luntai;
    private MainFourView mfv_shouye, mfv_top;
    private int screenHidth; //屏幕的高度
    private IShouyePresenter iShouyePresenter;//首页数据的presenter
    private IShouyeFirstPresenter iShouyeFirstPresenter;//轮播图的presenter
    private IShouyeSecondPresenter shouyeSecondPresenter;//第二块区域的presenter
    private IShouyeThreePresenter shouyeThreePresenter;//分类的presenter
    private IShouyeFourPresenter iShouyeFourPresenter;
    private LunBoTuAdapter lunBoTuAdapter; //轮播图适配器
    private int pressNum =-1;//用来区分 按下的是分类还是筛选

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_shouye2, null);
        linear_shouye = (LinearLayout) view.findViewById(R.id.linear_shouye);
        myscrollview = (ScrollYScrollView) view.findViewById(R.id.myscrollview);
        linear_hide_shouye = (LinearLayout) view.findViewById(R.id.linear_hide_shouye);
        frame_shouye2_three = (FrameLayout) view.findViewById(R.id.frame_shouye2_three);
        rv_shouye = (MyRecycleView) view.findViewById(R.id.rv_shouye);
        linear_location = (LinearLayout) view.findViewById(R.id.linear_location);
        iv_shouye_search = (ImageView) view.findViewById(R.id.iv_shouye_search);
        iv_shouye_more = (ImageView) view.findViewById(R.id.iv_shouye_more);
        view_lunbotu = inflater.inflate(R.layout.main_first, null);
        iv_main_first = (ImageView) view_lunbotu.findViewById(R.id.iv_main_first);
        vpager_lunbotu = (ViewPager) view_lunbotu.findViewById(R.id.vpager_lunbotu);
        ll_points = (LinearLayout) view_lunbotu.findViewById(R.id.ll_points);
        mfv_shouye = (MainFourView) view.findViewById(R.id.mfv_shouye);
        mfv_top = (MainFourView) view.findViewById(R.id.mfv_top);
        view_three = new MainFourView(getmActivity());
        view_second = inflater.inflate(R.layout.main_second, null);
        relative_tjq = (RelativeLayout) view_second.findViewById(R.id.relative_tjq);
        relative_shouye2_title = (RelativeLayout)view.findViewById(R.id.relative_shouye2_title);
        relative_zpjy = (RelativeLayout) view_second.findViewById(R.id.relative_zpjy);
        relative_qcjy = (RelativeLayout) view_second.findViewById(R.id.relative_qcjy);
        relative_xiuc = (RelativeLayout) view_second.findViewById(R.id.relative_xiuc);
        relative_jyz = (RelativeLayout) view_second.findViewById(R.id.relative_jyz);
        relative_xic = (RelativeLayout) view_second.findViewById(R.id.relative_xic);
        relative_xhfw = (RelativeLayout) view_second.findViewById(R.id.relative_xhfw);
        relative_dj = (RelativeLayout) view_second.findViewById(R.id.relative_dj);
        view_four = inflater.inflate(R.layout.main_three, null);
        iv_jbdb = (ImageView) view_four.findViewById(R.id.iv_jbdb);
        iv_ykggl = (ImageView) view_four.findViewById(R.id.iv_ykggl);
        iv_gcwy = (ImageView) view_four.findViewById(R.id.iv_gcwy);
        iv_mrtm = (ImageView) view_four.findViewById(R.id.iv_mrtm);
        relative_three_xiche = (RelativeLayout) view_four.findViewById(R.id.relative_three_xiche);
        relative_three_meirong = (RelativeLayout) view_four.findViewById(R.id.relative_three_meirong);
        relative_three_yanghu = (RelativeLayout) view_four.findViewById(R.id.relative_three_yanghu);
        relative_three_luntai = (RelativeLayout) view_four.findViewById(R.id.relative_three_luntai);
        tv_shouye_city = (TextView) view.findViewById(R.id.tv_shouye_city);
        loadingPager = (LoadingPager) view.findViewById(R.id.loadpager_shouye);
        frame_shouye = (FrameLayout) view.findViewById(R.id.frame_shouye);

        return view;
    }

    @Override
    public void initListenr() {
        super.initListenr();

        view_three.setOnFirstClick(this);
        view_three.setOnSecondClick(this);
        view_three.setOnThreeClick(this);
        mfv_shouye.setOnFirstClick(this);
        mfv_shouye.setOnSecondClick(this);
        mfv_shouye.setOnThreeClick(this);
        linear_location.setOnClickListener(this);
        iv_shouye_search.setOnClickListener(this);
        iv_shouye_more.setOnClickListener(this);
        relative_tjq.setOnClickListener(this);
        relative_zpjy.setOnClickListener(this);
        relative_qcjy.setOnClickListener(this);
        relative_xiuc.setOnClickListener(this);
        relative_jyz.setOnClickListener(this);
        relative_xic.setOnClickListener(this);
        relative_xhfw.setOnClickListener(this);
        relative_dj.setOnClickListener(this);
        iv_jbdb.setOnClickListener(this);
        iv_ykggl.setOnClickListener(this);
        iv_gcwy.setOnClickListener(this);
        iv_mrtm.setOnClickListener(this);
        relative_three_xiche.setOnClickListener(this);
        relative_three_meirong.setOnClickListener(this);
        relative_three_yanghu.setOnClickListener(this);
        relative_three_luntai.setOnClickListener(this);
        myscrollview.setOnScrollListener(this);
        relative_shouye2_title.setOnClickListener(this);

    }

    @Override
    public void initData() {
        super.initData();

        //重新设置loadingpager的高度
        setRecycleviewHeight();
        loadingPager.setLoadVisable();

        iShouyePresenter = new IShouyePresenterImpl(this);
        iShouyeFirstPresenter = new IShouyeFirstPresenterImpl(this, this);
        shouyeSecondPresenter = new IShouyeSecondPresenterImpl(this);
        shouyeThreePresenter = new IShouyeThreePresenterImpl(this);
        iShouyeFourPresenter = new IShouyeFourPresenterImpl(this);

        //开始加载数据
        iShouyeFirstPresenter.start();
        shouyeThreePresenter.start();

        //设置分类的标题
        shouyeThreePresenter.setTile();

        //将view添加到listview的头中
        linear_hide_shouye.addView(view_lunbotu);
        linear_hide_shouye.addView(view_second);
        linear_hide_shouye.addView(view_four);
        frame_shouye2_three.addView(view_three);

        //将2个分类 重叠
        //当布局的状态或者控件的可见性发生改变回调的接口
        linear_shouye.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                int mBuyLayout2ParentTop = Math.max(myscrollview.getScrollY(), myscrollview.getMHeight());

                //只要滑动的时候就不断的重新画顶部的布局的位置,当scrollY小于gettop时,就2个一直重合,当scrollY大于top就一直让它卡在顶部
                mfv_shouye.layout(0, mBuyLayout2ParentTop, view_three.getWidth(),
                        mBuyLayout2ParentTop + view_three.getHeight());

            }
        });

        myscrollview.post(new Runnable() {
            //让scrollview跳转到顶部，必须放在runnable()方法中
            @Override
            public void run() {
                myscrollview.scrollTo(0, 0);
            }
        });
        //互相关联
        myscrollview.setRecyclerView(rv_shouye);
        rv_shouye.setScrollYScrollView(myscrollview);

    }

    /**
     * 重新计算loadpager高度,让它刚好是屏幕的高度,这样当没数据的时候,scrollview也可以滑动
     */
    private void setRecycleviewHeight() {
        //获取屏幕高度
        screenHidth = UtilMethod.getHeight(getmActivity());

        //获取状态栏高度
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        int expandSpec = getResources().getDisplayMetrics().heightPixels - UtilMethod.dip2px(getmActivity(), Constants.IDV_HEIGHT) - result - UtilMethod.dip2px(getmActivity(), Constants.SHOUYE_TITLE)
                - UtilMethod.dip2px(getmActivity(), Constants.SHOUYE_FN);

        ViewGroup.LayoutParams layoutParams = loadingPager.getLayoutParams();
        layoutParams.height = expandSpec;
        loadingPager.setLayoutParams(layoutParams);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mfiv_fist:

                select(0);
                break;
            case R.id.mfiv_second:

                select(1);
                break;
            case R.id.mfiv_three:

                select(2);
                break;
            case R.id.relative_shouye2_title:
                closePopupWindow();
                break;
            case R.id.linear_location://地图
                // UtilMethod.startIntent(getmActivity(), MyMapActivity.class);
                break;
            case R.id.iv_shouye_search://搜索
                if (close()) {

                } else {
                    UtilMethod.startIntent(getmActivity(), SearchActivity.class);
                }
                break;
            case R.id.iv_shouye_more: //消息
                UtilMethod.isLogin(getmActivity(), MessageActivity.class);

                break;
            case R.id.relative_dj: //生活缴费
                shouyeSecondPresenter.luntai();
                break;
            case R.id.relative_jyz:
                shouyeSecondPresenter.jyz();
                break;
            case R.id.relative_qcjy:
                shouyeSecondPresenter.qcmr();
                break;
            case R.id.relative_tjq:
                shouyeSecondPresenter.tjq();
                break;
            case R.id.relative_xhfw:
                shouyeSecondPresenter.yg();
                break;
            case R.id.relative_xic:
                shouyeSecondPresenter.xiche();
                break;
            case R.id.relative_xiuc:
                shouyeSecondPresenter.baoyang();
                break;
            case R.id.relative_zpjy:
                shouyeSecondPresenter.jiyou();
                break;
            case R.id.iv_jbdb: //金币夺宝
                ((HomeActivity) getmActivity()).changeShangcheng();
                break;
            case R.id.iv_ykggl://金币兑换

                ((HomeActivity) getmActivity()).changeJinbi();
                break;
            case R.id.iv_gcwy: //无忧
                //  UtilMethod.Toast(getmActivity(), "无忧");
                break;
            case R.id.iv_mrtm://每日特卖
                UtilMethod.startIntent(getmActivity(), SaleActivity.class);
                break;
            case R.id.frame_empty: //加载为空
                UtilMethod.Toast(getmActivity(), "重新加载");
                break;
            case R.id.frame_error: //加载错误
                UtilMethod.Toast(getmActivity(), "重新加载");
                break;
            case R.id.relative_three_luntai: //轮胎
                shouyeSecondPresenter.luntaiservice();
                break;
            case R.id.relative_three_meirong: //美容
                shouyeSecondPresenter.meirongservice();
                break;
            case R.id.relative_three_yanghu: //养护
                shouyeSecondPresenter.yanghuservice();
                break;
            case R.id.relative_three_xiche: //洗车
                shouyeSecondPresenter.xicheservice();

                break;

        }
    }

    /**
     * 分类的选中事件
     *
     * @param
     * @param first
     */
    public void select(int first) {
        //将布局滑动到指定位置

        if (myscrollview != null) {
            myscrollview.smoothto();
        }
        if (pressNum == first) {
            if (close()) {
                return;
            }
        } else {
            closePopupWindow();
        }


        if (0 == first) {
            pressNum = 0;
            View view = View.inflate(getmActivity(), R.layout.pp_main_sort, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closePopupWindow();
                }
            });
            mainlist = (ListView) view.findViewById(R.id.classify_mainlist);
            morelist = (ListView) view.findViewById(R.id.classify_morelist);
            //开启popouwindow
            setUpPopupWindow(view, mfv_top, first, screenHidth);
            view_three.setFirstSelect(!view_three.isFirstSelected());
            mfv_shouye.setFirstSelect(!mfv_shouye.isFirstSelected());
            //这个必须放到select之后，因为控件是在select中才初始化
            shouyeThreePresenter.clickFirst();

        } else if (1 == first) {
            pressNum = 1;
            View view = View.inflate(getmActivity(), R.layout.pp_main_saixun, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closePopupWindow();
                }
            });
            recyclerView = (RecyclerView) view.findViewById(R.id.pp_main_sauxun_rv);
            //开启popouwindow
            setUpPopupWindow(view, mfv_top, first, screenHidth);
            view_three.setSecondSelect(!view_three.isSecondSelected());
            mfv_shouye.setSecondSelect(!mfv_shouye.isSecondSelected());
            shouyeThreePresenter.clickSecond();
        } else if (2 == first) {
            pressNum = 2;
            View view = View.inflate(getmActivity(), R.layout.pp_main_saixun, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closePopupWindow();
                }
            });
            rv_shaixuan = (RecyclerView) view.findViewById(R.id.pp_main_sauxun_rv);
            //开启popouwindow
            setUpPopupWindow(view, mfv_top, first, screenHidth);
            view_three.setThreeSelect(!view_three.isThreeSelected());
            mfv_shouye.setThreeSelect(!mfv_shouye.isThreeSelected());
            shouyeThreePresenter.clickThree();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        closePopupWindow();
        if (iShouyePresenter != null) {
            iShouyeFirstPresenter.onDestroy();
        }
        if (shouyeSecondPresenter != null) {
            shouyeSecondPresenter.onDestroy();
        }
        if (shouyeThreePresenter != null) {
            shouyeThreePresenter.onDestroy();
        }
    }


    @Override
    public void dismiss() {
        super.dismiss();
        view_three.setFirstSelect(false);
        view_three.setSecondSelect(false);
        view_three.setThreeSelect(false);
        mfv_shouye.setFirstSelect(false);
        mfv_shouye.setSecondSelect(false);
        mfv_shouye.setThreeSelect(false);

    }


    @Override
    public LinearLayout getLpoint() {
        return ll_points;
    }

    @Override
    public ViewPager getViewPager() {
        return vpager_lunbotu;
    }


    @Override
    public MainFourView getViewThree() {
        return view_three;
    }

    @Override
    public TextView getTextViewCity() {
        return tv_shouye_city;
    }

    @Override
    public RecyclerView getRecyclyView() {
        return recyclerView;
    }

    @Override
    public ListView getMainListview() {
        return mainlist;
    }

    @Override
    public ListView getMoreListview() {
        return morelist;
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadingPager;
    }

    @Override
    public FrameLayout getFrameShouye() {
        return frame_shouye;
    }

    @Override
    public MyRecycleView getRecyclyViewShouye() {
        return rv_shouye;
    }

    @Override
    public void setLunBoAdapter() {
        if (null != iShouyeFirstPresenter) {

            lunBoTuAdapter = new LunBoTuAdapter(iShouyeFirstPresenter);
            //设置轮播图的适配器
            if (vpager_lunbotu != null) {

                vpager_lunbotu.setAdapter(lunBoTuAdapter);
            }
        }

    }


    @Override
    public ImageView getImageviewMianFirst() {
        return iv_main_first;
    }

    @Override
    public MainFourView getMfvShouye() {
        return mfv_shouye;
    }

    @Override
    public RecyclerView getRecyclyView2() {
        return rv_shaixuan;
    }


    @Override
    public void onScroll(int scrollY) {

        int mBuyLayout2ParentTop = Math.max(scrollY, myscrollview.getMHeight());
        //判断Scroll是否置顶
        if (scrollY >= myscrollview.getMHeight()) {
            shouyeThreePresenter.setTop(true);
            mfv_shouye.setTop(true);
        } else {
            mfv_shouye.setTop(false);
            shouyeThreePresenter.setTop(false);

        }

        //只要滑动的时候就不断的重新画顶部的布局的位置,当scrollY小于gettop时,就2个一直重合,当scrollY大于top就一直让它卡在顶部
        mfv_shouye.layout(0, mBuyLayout2ParentTop, view_three.getWidth(),
                mBuyLayout2ParentTop + view_three.getHeight());


    }

    /**
     * 用来判断popupwindow是否关闭
     * @return
     */
    public boolean close() {
        if (getPopupWindow() != null && getPopupWindow().isShowing()) {
            closePopupWindow();
            return true;
        }
        return false;
    }


}
