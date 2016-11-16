package com.betterda.xsnano.location;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.dialog.CallDialog;
import com.betterda.xsnano.javabean.CommonAddress;
import com.betterda.xsnano.location.model.Address;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.squareup.haha.trove.TIntHash;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择地点的界面
 * Created by Administrator on 2016/11/9.
 */

public class LocationActivity extends BaseActivity implements OnGetSuggestionResultListener, View.OnClickListener {
    // SuggestionSearch建议查询类
    private SuggestionSearch mSuggestionSearch;

    private RelativeLayout relative_location_title, relative_location_delete, relative_commonaddress;
    private LinearLayout linearLayout, linearCurrent, linear_search, linearTrash;
    private TextView tv_address, tv_dingwei, tv_search_cancel, tvTrash, tvComplete;
    private EditText et_search;
    private int currententValue;//记录当前的值
    private boolean isFinishAnim;//是否完成过动画
    private String city;//记录当前定位城市
    private LoadingPager loadingPager, loadingPagerLocation;
    private RecyclerView rvPpchose, rvLocation;
    private List<Address> list;
    private List<Address> listLocation;//常用地址的容器
    private CommonAdapter<Address> adapter;
    private CommonAdapter<Address> adapterLocation;
    private ImageView iv_delete, ivTrash;//删除图标
    private FrameLayout frame_location_rv;//常用地址层
    private FrameLayout frame_location_show, frame_pp_choseaddress; //搜索的阴影层和搜藏层
    /**
     * 定位功能
     */
    public LocationClient mLocationClient; //定位的类
    public BDLocationListener myListener = new MyLocationListener();
    private int page = 1;
    private double longitude;//经度
    private double dimension;//纬度


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_location);
        tv_address = (TextView) findViewById(R.id.tv_loaction_address);
        tv_dingwei = (TextView) findViewById(R.id.tv_loaction_dingwei);
        tv_search_cancel = (TextView) findViewById(R.id.tv__search_cancel);
        linearLayout = (LinearLayout) findViewById(R.id.linear_111);
        linearCurrent = (LinearLayout) findViewById(R.id.linear_location_current);
        linear_search = (LinearLayout) findViewById(R.id.linear_search);
        relative_location_title = (RelativeLayout) findViewById(R.id.relative_location_title);
        relative_location_delete = (RelativeLayout) findViewById(R.id.relative_location_delete);
        relative_commonaddress = (RelativeLayout) findViewById(R.id.relative_commonaddress);
        loadingPagerLocation = (LoadingPager) findViewById(R.id.loadpager_location);
        rvLocation = (RecyclerView) findViewById(R.id.rv_location);
        frame_location_show = (FrameLayout) findViewById(R.id.frame_location_show);
        frame_pp_choseaddress = (FrameLayout) findViewById(R.id.frame_pp_choseaddress);
        frame_location_rv = (FrameLayout) findViewById(R.id.frame_location_rv);
        et_search = (EditText) findViewById(R.id.et_search);
        rvPpchose = (RecyclerView) findViewById(R.id.rv_pp_choseaddress);
        loadingPager = (LoadingPager) findViewById(R.id.loadpager_pp_choseaddress);
        iv_delete = (ImageView) findViewById(R.id.iv_layout_search_delete);

        linearTrash = (LinearLayout) findViewById(R.id.linear_location_trash);
        tvTrash = (TextView) findViewById(R.id.tv_location_qingkong);
        tvComplete = (TextView) findViewById(R.id.tv_location_complete);
        ivTrash = (ImageView) findViewById(R.id.iv_location_trash);
    }


    @Override
    public void init() {
        super.init();
        // 实例化建议查询类
        mSuggestionSearch = SuggestionSearch.newInstance();
        // 注册建议查询事件监听
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        /**
         * 定位相关
         */
        mLocationClient = new LocationClient(this);//声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        UtilMethod.initLocation(mLocationClient);
        //开启定位
        mLocationClient.start();

        list = new ArrayList<>();
        listLocation = new ArrayList<>();

        setPpRecycleview();
        setLocationRecycleview();


        addTextChange(et_search, iv_delete, frame_pp_choseaddress);

        getData();

    }

    @Override
    public void initListener() {
        super.initListener();
        et_search.setOnClickListener(this);
        relative_location_delete.setOnClickListener(this);
        tv_dingwei.setOnClickListener(this);
        linearCurrent.setOnClickListener(this);
        tv_search_cancel.setOnClickListener(this);
        frame_location_show.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        tvTrash.setOnClickListener(this);
        tvComplete.setOnClickListener(this);
        ivTrash.setOnClickListener(this);


    }

    private void setLocationRecycleview() {
        adapterLocation = new CommonAdapter<Address>(this, R.layout.item_pp_main_sort2, listLocation) {


            @Override
            public void convert(ViewHolder holder, final Address address) {
                if (null != address) {

                    holder.setText(R.id.sort2_txt, address.getAddress());
                    holder.setOnClickListener(R.id.sort2_layout, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                longitude = Double.parseDouble(address.getLongitude());
                                dimension = Double.parseDouble(address.getLatitude());
                            } catch (Exception e) {

                            }

                            close(address.getKey());
                        }
                    });

                    if (address.isDelte()) {
                        holder.setVisible(R.id.sort2_img_delete, true);
                    } else {
                        holder.setVisible(R.id.sort2_img_delete, false);
                    }

                    holder.setOnClickListener(R.id.sort2_img_delete, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO 删除
                          //   delete();
                        }
                    });
                }
            }
        };

        rvLocation.setLayoutManager(new LinearLayoutManager(this));
        rvLocation.setAdapter(adapterLocation);
    }

    private void setPpRecycleview() {
        adapter = new CommonAdapter<Address>(this, R.layout.item_pp_choseaddress, list) {
            @Override
            public void convert(ViewHolder holder, final Address address) {
                if (address != null) {
                    holder.setText(R.id.tv_item_pp_choseaddress2, address.getAddress());
                    holder.setText(R.id.tv_item_pp_choseaddress, address.getKey());
                    holder.setOnClickListener(R.id.linear_pp_choseaddress_title, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                longitude = Double.parseDouble(address.getLongitude());
                                dimension = Double.parseDouble(address.getLatitude());
                            } catch (Exception e) {

                            }
                            close(address.getKey());
                            uploadAddress(address.getLongitude(), address.getLatitude(), address.getKey(), address.getAddress());
                        }
                    });
                }

            }
        };

        rvPpchose.setLayoutManager(new LinearLayoutManager(LocationActivity.this));
        rvPpchose.setAdapter(adapter);
    }

    /**
     * 获取常用地址
     */
    private void getData() {
        boolean islogin = CacheUtils.getBoolean(this, "islogin", false);
        if (!islogin) {
            relative_commonaddress.setVisibility(View.GONE);
            frame_location_rv.setVisibility(View.GONE);
            return;
        }
        loadingPagerLocation.setLoadVisable();
        RequestParams params = new RequestParams(Constants.URL_QUERY_CHANGYONG_ADDRESS);
        params.addBodyParameter("account", CacheUtils.getString(this, "number", ""));
        params.addBodyParameter("page", page + "");
        params.addBodyParameter("rows", Constants.PAGE_SIZE + "");
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println("s:"+s);
                List<CommonAddress> listCommonAddress = GsonParse.getListCommonAddress(UtilMethod.getString(s));
                if (listCommonAddress != null) {
                    if (listLocation != null && page == 1) {
                        listLocation.clear();
                    }
                    for (int i = 0; i < listCommonAddress.size(); i++) {
                        CommonAddress commonAddress = listCommonAddress.get(i);
                        if (commonAddress != null) {
                            Address address = new Address();
                            address.setAddress(commonAddress.getAddress());
                            address.setKey(commonAddress.getDistrict());
                            address.setLongitude(commonAddress.getLongitude());
                            address.setLatitude(commonAddress.getLatitude());
                            listLocation.add(address);
                        }
                    }
                }

                if (adapterLocation != null) {
                    adapterLocation.notifyDataSetChanged();
                }

                if (listLocation != null) {
                    if (listLocation.size() > 0) {
                        loadingPagerLocation.hide();
                    } else {
                        loadingPagerLocation.setEmptyVisable();
                    }
                } else {
                    loadingPagerLocation.setEmptyVisable();
                }


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (loadingPagerLocation != null) {
                    loadingPagerLocation.setErrorVisable();
                }
            }
        });
    }

    /**
     * 添加地址
     *
     * @param longitude
     * @param latitude
     * @param district
     * @param address
     */
    private void uploadAddress(String longitude, String latitude, String district, String address) {
        boolean islogin = CacheUtils.getBoolean(this, "islogin", false);
        if (!islogin) {
            return;
        }

        RequestParams params = new RequestParams(Constants.URL_ADD_CHANGYONG_ADDRESS);
        params.addBodyParameter("account", CacheUtils.getString(this, "number", ""));
        params.addBodyParameter("longitude", longitude);
        params.addBodyParameter("latitude", latitude);
        params.addBodyParameter("district", district);
        params.addBodyParameter("address", address);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println("s:" + s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }
        });
    }


    /**
     * 点击输入框
     */
    private void click() {
        //设置不可以点击
        linear_search.setClickable(false);
        if (!isFinishAnim) {
            //没有开启过动画
            tv_search_cancel.setVisibility(View.VISIBLE);
            showAmin();

        }
    }

    /**
     * 关闭选择地址界面
     */
    public void close(String city) {
        if (TextUtils.isEmpty(city)) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("city", city);
        intent.putExtra("longitude",longitude);
        intent.putExtra("dimension",dimension);
        setResult(0, intent);
        finish();
    }

    /**
     * 开启动画
     */
    private void showAmin() {
        //设置开启过动画
        isFinishAnim = true;
        openInput();
        frame_location_show.setVisibility(View.VISIBLE);
        startAnim();

    }

    /**
     * 开始动画
     */

    private void startAnim() {
        currententValue = relative_location_title.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currententValue, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                //让界面移动
                if (linearLayout != null) {
                    linearLayout.scrollBy(0, currententValue - animatedValue);
                }
                currententValue = animatedValue;
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                linear_search.setClickable(true);
                //获取屏幕的高度
                int height = UtilMethod.getHeight(LocationActivity.this);
                setHeight(height + relative_location_title.getHeight());

                //重新设置搜索时的高度
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.height = height - linear_search.getHeight() - UtilMethod.statusHeight(LocationActivity.this) - UtilMethod.getNavigationBarHeight(LocationActivity.this);
                frame_pp_choseaddress.setLayoutParams(layoutParams);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        valueAnimator.setDuration(200).start();
    }

    /**
     * 重新设置整个布局的高度
     */
    private void setHeight(int height) {

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (height > 0) {
            layoutParams.height = height;
        }
        linearLayout.setLayoutParams(layoutParams);
    }


    /**
     * 给editview设置监听
     *
     * @param etPpchoseAddress
     * @param imageView
     * @param view
     */
    private void addTextChange(EditText etPpchoseAddress, final ImageView imageView, final View view) {
        etPpchoseAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() <= 0) {
                    if (imageView != null) {
                        imageView.setVisibility(View.GONE);
                    }
                    if (view != null) {
                        view.setVisibility(View.GONE);
                    }

                    return;
                }
                if (imageView != null) {
                    imageView.setVisibility(View.VISIBLE);
                }
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }

                if (loadingPager != null) {
                    loadingPager.setLoadVisable();
                }
                //开启建议查询
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(s.toString()).city(""));


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /**
     * 打开软键盘
     */
    private void openInput() {
        //打开软键盘
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    private void closeInput() {
        //打开软键盘
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }


    /**
     * 关闭动画
     */
    private void closeAnim() {
        linear_search.setClickable(false);
        et_search.setText("");
        tv_search_cancel.setVisibility(View.GONE);
        setHeight(0);
        closeInput();
        if (relative_location_title == null) {
            return;
        }
        currententValue = -relative_location_title.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currententValue, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                if (currententValue - animatedValue < 0) {
                    if (linearLayout != null) {

                        linearLayout.scrollBy(0, currententValue - animatedValue);
                    }
                }
                currententValue = animatedValue;
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                linear_search.setClickable(true);
                isFinishAnim = false;
                frame_location_show.setVisibility(View.INVISIBLE);
                frame_pp_choseaddress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(200).start();


    }


    //建议查询事件监听所要实现的方法
    @Override
    public void onGetSuggestionResult(SuggestionResult res) {

        if (res == null || res.getAllSuggestions() == null) {
            if (loadingPager != null) {
                loadingPager.hide();
            }
            return;
        }

        list.clear();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                if (info.pt != null) {
                    Address address = new Address();
                    address.setKey(info.city);
                    address.setAddress(info.city + info.district + info.key);
                    list.add(address);
                }


            }
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        if (loadingPager != null) {
            loadingPager.hide();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_location_qingkong://清空
                showComfirmDialog();
                break;
            case R.id.tv_location_complete://完成
                linearTrash.setVisibility(View.GONE);
                ivTrash.setVisibility(View.VISIBLE);
                showDelete(false);
                break;
            case R.id.iv_location_trash://垃圾桶
                linearTrash.setVisibility(View.VISIBLE);
                ivTrash.setVisibility(View.INVISIBLE);
                showDelete(true);
                break;
            case R.id.linear_location_current:
                close(city);
                break;
            case R.id.tv__search_cancel://取消
                closeAnim();
                break;
            case R.id.frame_location_show://阴影层的点击事件
                tv_search_cancel.setVisibility(View.GONE);

                closeAnim();
                break;
            case R.id.iv_layout_search_delete://删除图标
                if (et_search != null) {
                    et_search.setText("");
                }
                break;
            case R.id.et_search: //输入框
                click();
                break;
            case R.id.relative_location_delete://定位框
                LocationActivity.this.finish();
                break;
            case R.id.tv_loaction_dingwei://重新定位
                if (tv_dingwei != null) {
                    tv_dingwei.setSelected(true);
                }
                if (mLocationClient != null) {
                    mLocationClient.start();
                }
                break;
        }

    }

    /**
     * 显示确认对话框
     */
    private void showComfirmDialog() {

        CallDialog callDialog = new CallDialog(this, new CallDialog.onConfirmListener() {
            @Override
            public void comfirm() {
                    //  TODO 清空
            }

            @Override
            public void cancel() {

            }
        },"确定要清空?","确定");

        callDialog.show();
    }

    /**
     * 显示删除图标
     */
    private void showDelete(boolean isDelete) {

        if (listLocation != null) {
            for (Address address : listLocation) {
                address.setDelte(isDelete);
            }
        }
        if (adapterLocation != null) {
            adapterLocation.notifyDataSetChanged();
        }
    }


    /**
     * 定位的回调方法
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (null != location) {

                if (tv_address != null && location.getCity() != null) {
                    tv_address.setText(location.getProvince() + location.getCity() + location.getDistrict() + location.getStreet() + location.getStreetNumber());
                    city = location.getCity();

                }

            }

            if (tv_dingwei != null) {
                tv_dingwei.setSelected(false);
            }

            //停止定位
            if (mLocationClient != null) {

                mLocationClient.stop();
            }

        }
    }

    @Override
    public void onBackPressed() {

        if (isFinishAnim) {
            closeAnim();
        } else {
            super.onBackPressed();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
    }


}
