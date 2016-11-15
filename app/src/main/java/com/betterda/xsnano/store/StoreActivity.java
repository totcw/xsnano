package com.betterda.xsnano.store;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.adapter.MyAdapter;
import com.betterda.xsnano.bus.BusActivity;
import com.betterda.xsnano.store.fragment.CommentFragment;
import com.betterda.xsnano.store.fragment.ShangpingFragment;
import com.betterda.xsnano.store.presenter.IStorePresenter;
import com.betterda.xsnano.store.presenter.IStorePresenterImpl;
import com.betterda.xsnano.store.view.IStoreView;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.PermissionUtil;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;
import com.betterda.xsnano.view.ScrollYScrollView2;
import com.betterda.xsnano.view.ViewPagerIndicator;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺的页面
 * Created by Administrator on 2016/4/27.
 */
public class StoreActivity extends BaseActivity implements IStoreView, View.OnClickListener, ScrollYScrollView2.OnScrollListener {
    private static final int REQUECT_CODE_SDCARD = 2;
    private static final String PRODUCT_ID = "402881ec55be27320155be898b5c0036";//总公司店铺id
    private ScrollYScrollView2 store_myscrollview;
    private LinearLayout linear_store, linear_store_indicator;
    private LinearLayout usv_store;
    private ViewPager vp_store;
    private ViewPagerIndicator indicator;
    private List<String> mDatas; //viewpager指示器的数据
    private List<Fragment> fragmentList;
    private ShangpingFragment shangpinFragment;
    private CommentFragment commentFragment;
    private IStorePresenter iStorePresenter;
    private LoadingPager loadpager_store;
    private SimpleDraweeView sv_store;
    private NormalTopBar topBar;
    private RelativeLayout relative_store_call;
    private TextView tv_name, tv_address, tv_type, tv_statue, tv_time;

    private String id;//店铺的id
    private IWXAPI api;//微信接口


    @Override
    public void initView() {

        setContentView(R.layout.activity_store);
        usv_store = (LinearLayout) findViewById(R.id.usv_store);
        store_myscrollview = (ScrollYScrollView2) findViewById(R.id.store_myscrollview);
        vp_store = (ViewPager) findViewById(R.id.vp_store);
        indicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
        loadpager_store = (LoadingPager) findViewById(R.id.loadpager_store);
        sv_store = (SimpleDraweeView) findViewById(R.id.sv_store);
        topBar = (NormalTopBar) findViewById(R.id.topbar_store);
        relative_store_call = (RelativeLayout) findViewById(R.id.relative_store_call);
        tv_name = (TextView) findViewById(R.id.tv_store_name);
        tv_address = (TextView) findViewById(R.id.tv_store_address);
        tv_type = (TextView) findViewById(R.id.tv_store_type);
        tv_statue = (TextView) findViewById(R.id.tv_store_yinye);
        tv_time = (TextView) findViewById(R.id.tv_store_time);
        linear_store = (LinearLayout) findViewById(R.id.linear_store);
        linear_store_indicator = (LinearLayout) findViewById(R.id.linear_store_indicator);

    }

    @Override
    public void initListener() {
        topBar.setOnBackListener(this);
        topBar.setOnBusListener(this);
        relative_store_call.setOnClickListener(this);
        store_myscrollview.setOnScrollListener(this);
        topBar.setonShareListener(this);
    }

    @Override
    public void init() {
        topBar.setTitle("店铺详情");
        topBar.setBusVisibility(true);

        topBar.setShareVisbility(true);
        setRecycleviewHeight();

        //通过工厂获取实例
        api = WXAPIFactory.createWXAPI(getApplicationContext(), Constants.WeiXin.APP_ID, true);
        //将应用的appid注册到微信
        api.registerApp(Constants.WeiXin.APP_ID);


        Intent intent = getIntent();
        if (null != intent) {
            id = intent.getStringExtra("id");
        }
        if (TextUtils.isEmpty(id)) {
            id = PRODUCT_ID;
        }

        mDatas = new ArrayList<>();
        mDatas.add("业务范围");
        mDatas.add("用户评价");
        //设置Tab上的标题
        indicator.setTabItemTitles(mDatas);
        //先隐藏
        indicator.setVisibility(View.GONE);
        //显示加载界面
        loadpager_store.setLoadVisable();

        iStorePresenter = new IStorePresenterImpl(this, id);
        //当布局的状态或者控件的可见性发生改变回调的接口
        linear_store.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                int mBuyLayout2ParentTop = Math.max(store_myscrollview.getScrollY(), store_myscrollview.getMHeight());

                //只要滑动的时候就不断的重新画顶部的布局的位置,当scrollY小于gettop时,就2个一直重合,当scrollY大于top就一直让它卡在顶部
                indicator.layout(0, mBuyLayout2ParentTop, indicator.getWidth(),
                        mBuyLayout2ParentTop + indicator.getHeight());

            }
        });

        store_myscrollview.setOverScrollMode(View.OVER_SCROLL_NEVER);

    }

    /**
     * 重新计算recycleview高度,让它刚好是屏幕的高度,这样当 数据小于一个屏幕时,scrollview也可以滑动
     */
    private void setRecycleviewHeight() {


        //获取状态栏高度
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        int expandSpec = getResources().getDisplayMetrics().heightPixels - result - UtilMethod.dip2px(getmActivity(), Constants.SHOUYE_TITLE)
                - UtilMethod.dip2px(getmActivity(), 45)-UtilMethod.getNavigationBarHeight(getmActivity());

        ViewGroup.LayoutParams layoutParams = vp_store.getLayoutParams();
        layoutParams.height = expandSpec;
        vp_store.setLayoutParams(layoutParams);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (null != iStorePresenter) {
            iStorePresenter.start();
        }
    }

    /**
     * 设置viewpager
     */
    public void setViewpager(double score, double servicescore) {

        fragmentList = new ArrayList<>();
        if (shangpinFragment == null) {
            shangpinFragment = new ShangpingFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            shangpinFragment.setArguments(bundle);
        }
        if (commentFragment == null) {
            commentFragment = new CommentFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString("id", id);
            bundle2.putDouble("score", score);
            bundle2.putDouble("servicesore", servicescore);
            commentFragment.setArguments(bundle2);
        }

        fragmentList.add(shangpinFragment);
        fragmentList.add(commentFragment);

        if (null != vp_store && indicator != null) {
            indicator.setVisibility(View.VISIBLE);
            vp_store.setAdapter(new MyAdapter(getSupportFragmentManager(), fragmentList));
            //设置关联的ViewPager
            indicator.setViewPager(vp_store, 0);
        }
        store_myscrollview.post(new Runnable() {
            //让scrollview跳转到顶部，必须放在runnable()方法中
            @Override
            public void run() {
                store_myscrollview.scrollTo(0, 0);
            }
        });
    }

    @Override
    public NormalTopBar getNormalTopbar() {
        return topBar;
    }


    @Override
    public LoadingPager getLoadPager() {
        return loadpager_store;
    }

    @Override
    public ScrollYScrollView2 getUpHideScrollView() {
        return store_myscrollview;
    }

    @Override
    public TextView getTextViewName() {
        return tv_name;
    }

    @Override
    public TextView getTextViewtype() {
        return tv_type;
    }

    @Override
    public TextView getTextViewStatus() {
        return tv_statue;
    }

    @Override
    public TextView getTextViewTime() {
        return tv_time;
    }

    @Override
    public TextView getTextViewAddress() {
        return tv_address;
    }

    @Override
    public SimpleDraweeView getSimpleDrawView() {
        return sv_store;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.relative_share: //开启分享的选择
                View view = View.inflate(getmActivity(), R.layout.pp_share, null);
                RelativeLayout rl_wxfriend = (RelativeLayout) view.findViewById(R.id.relative_share_wxfriend);
                RelativeLayout rl_pyquan = (RelativeLayout) view.findViewById(R.id.relative_share_pyquan);
                rl_wxfriend.setOnClickListener(this);
                rl_pyquan.setOnClickListener(this);
                setUpPopupWindow(view, null, -1, -2);

                break;
            case R.id.relative_store_call://呼叫
                iStorePresenter.call();
                break;
            case R.id.bar_relative_bus://购物车
                UtilMethod.isLogin(this, BusActivity.class);
                break;
            case R.id.relative_share_wxfriend://分享到微信好友
                share(false);
                closePopupWindow();
                break;
            case R.id.relative_share_pyquan://分享到朋友圈
                share(true);
                closePopupWindow();
                break;
        }
    }

    private void share(boolean isFriend) {

        if (api.isWXAppInstalled()) { //判断是否安装了微信
            //开启微信分享
            WXWebpageObject wxWebpageObject = new WXWebpageObject();
            wxWebpageObject.webpageUrl = "http://www.meichebang.com.cn/xsnano_web/download.html";//填写网页url
            WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
            wxMediaMessage.title = "美车帮";
            wxMediaMessage.description = "美车帮";
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            wxMediaMessage.thumbData = Bitmap2Bytes(bitmap);
            //构造req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");// transaction字段用于唯一标识一个请求
            req.message = wxMediaMessage;
            req.scene = isFriend ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
            api.sendReq(req);

        } else {
            UtilMethod.Toast(getmActivity(), "请先安装微信");
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        UtilMethod.backgroundAlpha(1.0f, getmActivity());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iStorePresenter.ondestroy();
        if (api != null) {
            api.unregisterApp();
            api = null;
        }

        closePopupWindow();
    }

    /**
     * 申请权限
     */
    public void getPermissions() {


        requestContactsPermissions();
     /*   if (ActivityCompat.1670213342	1670213342	Online	LaunchCompatibility{compatible=YES, reason=null}(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED
                ) {
            System.out.println("2");
            requestContactsPermissions();
        } else {
            System.out.println("3");

            Intent intent = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:" + "15160700380"));
            startActivity(intent);
        }*/


    }


    /**
     * 请求权限
     */
    private void requestContactsPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
            System.out.println("4");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUECT_CODE_SDCARD);
        } else {
            System.out.println("5");
            // 无需向用户界面提示，直接请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUECT_CODE_SDCARD);
        }
    }


    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {

        if (requestCode == REQUECT_CODE_SDCARD) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
                UtilMethod.Toast(this, "打电话");
            } else {
                UtilMethod.Toast(this, "没有权限");
            }
        }
    }

    @Override
    public void onScroll(int scrollY) {
        System.out.println(scrollY);
        //判断Scroll是否置顶
        if (scrollY >= store_myscrollview.getMHeight()) {
            if (shangpinFragment != null && commentFragment != null) {
                System.out.println("==");
                if (shangpinFragment.rv_shangpin1 != null) {
                    shangpinFragment.rv_shangpin1.setTop(true);
                    System.out.println("rv_shangpin1");
                }
                if (shangpinFragment.rv_shangpin2 != null) {
                    shangpinFragment.rv_shangpin2.setTop(true);
                    System.out.println("rv_shangpin2");
                }
                if (commentFragment.rv_fragment_comment != null) {
                    System.out.println("rv_fragment_comment");
                    commentFragment.rv_fragment_comment.setTop(true);
                }
            }


        } else {
            if (shangpinFragment != null && commentFragment != null) {
                if (shangpinFragment.rv_shangpin1 != null) {
                    shangpinFragment.rv_shangpin1.setTop(false);

                }
                if (shangpinFragment.rv_shangpin2 != null) {
                    shangpinFragment.rv_shangpin2.setTop(false);

                }
                if (commentFragment.rv_fragment_comment != null) {

                    commentFragment.rv_fragment_comment.setTop(false);
                }
            }

        }

        int mBuyLayout2ParentTop = Math.max(store_myscrollview.getScrollY(), store_myscrollview.getMHeight());

        //只要滑动的时候就不断的重新画顶部的布局的位置,当scrollY小于gettop时,就2个一直重合,当scrollY大于top就一直让它卡在顶部
        indicator.layout(0, mBuyLayout2ParentTop, indicator.getWidth(),
                mBuyLayout2ParentTop + indicator.getHeight());

    }



}
