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
import com.betterda.xsnano.location.model.Address;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
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

public class LocationActivity extends BaseActivity implements OnGetSuggestionResultListener {
    // SuggestionSearch建议查询类
    private SuggestionSearch mSuggestionSearch;
    private RelativeLayout relative_search, relative_location_title,relative_location_delete;
    private LinearLayout linearLayout,linearCurrent;
    private TextView tv_address,tv_dingwei;
    private int currententValue;//记录当前的值
    private boolean isFinishAnim;//动画是否完成
    private boolean isDismiss; //popupwindow是否消失
    private String city;//记录当前定位城市
    private LoadingPager loadingPager,loadingPagerLocation;
    private RecyclerView rvPpchose,rvLocation;
    private List<Address> list;
    private List<String>listLocation;
    private CommonAdapter<Address> adapter;
    private CommonAdapter<String> adapterLocation;
    /**
     * 定位功能
     */
    public LocationClient mLocationClient; //定位的类
    public BDLocationListener myListener = new MyLocationListener() ;
    private int page =1;


    private FrameLayout frame_location_show;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_location);
        tv_address = (TextView) findViewById(R.id.tv_loaction_address);
        tv_dingwei = (TextView) findViewById(R.id.tv_loaction_dingwei);
        linearLayout = (LinearLayout) findViewById(R.id.linear_111);
        linearCurrent = (LinearLayout) findViewById(R.id.linear_location_current);
        relative_search = (RelativeLayout) findViewById(R.id.relative_search);
        relative_location_title = (RelativeLayout) findViewById(R.id.relative_location_title);
        relative_location_delete = (RelativeLayout) findViewById(R.id.relative_location_delete);
        loadingPagerLocation = (LoadingPager) findViewById(R.id.loadpager_location);
        rvLocation = (RecyclerView) findViewById(R.id.rv_location);
        frame_location_show = (FrameLayout) findViewById(R.id.frame_location_show);
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

        adapter = new CommonAdapter<Address>(this,R.layout.item_pp_choseaddress,list) {
            @Override
            public void convert(ViewHolder holder, final Address address) {
                if (address != null) {
                    holder.setText(R.id.tv_item_pp_choseaddress2, address.getAddress());
                    holder.setText(R.id.tv_item_pp_choseaddress, address.getKey());
                    holder.setOnClickListener(R.id.linear_pp_choseaddress_title, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            close(address.getKey());
                        }
                    });
                }

            }
        };

        adapterLocation = new CommonAdapter<String>(this,R.layout.item_pp_main_sort2,listLocation) {

            @Override
            public void convert(ViewHolder holder, final String s) {
                if (!TextUtils.isEmpty(s)) {

                    holder.setText(R.id.sort2_txt, s);
                    holder.setOnClickListener(R.id.sort2_layout, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            close(s);
                        }
                    });
                }
            }
        };

        rvLocation.setLayoutManager(new LinearLayoutManager(this));
        rvLocation.setAdapter(adapterLocation);

         getData();

    }

    /**
     * 获取常用地址
     */
    private void getData() {
        loadingPagerLocation.setLoadVisable();
        RequestParams params = new RequestParams(Constants.URL_QUERY_CHANGYONG_ADDRESS);
        params.addBodyParameter("account", CacheUtils.getString(this,"number",""));
        params.addBodyParameter("page", page+"");
        params.addBodyParameter("rows", Constants.PAGE_SIZE+"");
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println("常用地址:"+UtilMethod.getString(s));
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (loadingPagerLocation != null) {
                    loadingPagerLocation.setErrorVisable();
                }
            }
        });
    }


    @Override
    public void initListener() {
        super.initListener();
        relative_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFinishAnim) {
                    //如果动画还没结束就什么都不做
                } else {
                    showAmin();
                }


            }
        });
        relative_location_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationActivity.this.finish();
            }
        });

        tv_dingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_dingwei != null) {
                    tv_dingwei.setSelected(true);
                }
                if (mLocationClient != null) {
                    mLocationClient.start();
                }
            }
        });

        linearCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close(city);
            }
        });
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
        setResult(0,intent);
        finish();
    }

    /**
     * 开启popupwindow
     */
    private void showAmin() {
        isFinishAnim = true;
        View view = View.inflate(LocationActivity.this, R.layout.pp_choseaddress, null);
        View frame_pp_choseaddress = view.findViewById(R.id.frame_pp_choseaddress);
        View frame_pp_choseaddress2 = view.findViewById(R.id.frame_pp_choseaddress2);
        View tv_ppchoseaddress = view.findViewById(R.id.tv_pp_choseaddress);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_pp_choseaddress_delete);
        EditText etPpchoseAddress = (EditText) view.findViewById(R.id.et_pp_choseaddress);
        rvPpchose = (RecyclerView) view.findViewById(R.id.rv_pp_choseaddress);
        loadingPager = (LoadingPager) view.findViewById(R.id.loadpager_pp_choseaddress);
        rvPpchose.setLayoutManager(new LinearLayoutManager(LocationActivity.this));
        rvPpchose.setAdapter(adapter);
        setClick(frame_pp_choseaddress, tv_ppchoseaddress, iv, etPpchoseAddress, frame_pp_choseaddress2);
        addTextChange(etPpchoseAddress, iv, frame_pp_choseaddress2);
        openInput();
//显示popupwindow
      //  setUpPopupWindow(view, relative_search, 100, 0);
        frame_location_show.setVisibility(View.VISIBLE);
        startAnim(view);

    }

    /**
     * 开始动画
     */

    private void startAnim(final View view) {
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

                //当动画结束时
                isFinishAnim = false;
                if (linearLayout != null) { //如果popupwindow关闭了且动画没还原
                    if (isDismiss && linearLayout.getScrollY() != 0) {
                        closeAnim();
                    }
                }
                frame_location_show.requestLayout();

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

    private void setClick(View frame_pp_choseaddress, View tv_ppchoseaddress, ImageView imageView, final EditText editText, View view) {
        frame_pp_choseaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopupWindow();
            }
        });
        tv_ppchoseaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopupWindow();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText != null) {
                    editText.setText("");
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //什么都不做拦截 底层view的点击事件
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


    @Override
    public void dismiss() {
        super.dismiss();

        UtilMethod.backgroundAlpha(1.0f, this);
        closeAnim();
        closeInput();

    }

    /**
     * 关闭动画
     */
    private void closeAnim() {
        if (isFinishAnim) {
            isDismiss = true;
        } else {
            isDismiss = false;
            isFinishAnim = true;
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
                    isFinishAnim = false;

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
                    address.setKey(info.key);
                    address.setAddress(info.city+info.district);
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



    /**
     * 定位的回调方法
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (null != location) {

                if (tv_address != null&&location.getCity()!=null) {
                    tv_address.setText(location.getProvince()+location.getCity()+location.getDistrict()+location.getStreet()+location.getStreetNumber());
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
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
    }


}
