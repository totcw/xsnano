package com.betterda.xsnano.store.presenter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.dialog.CallDialog;
import com.betterda.xsnano.javabean.StoreDetail;
import com.betterda.xsnano.store.view.IStoreView;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.ConstantsData;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.PermissionUtil;
import com.betterda.xsnano.util.UtilMethod;
import com.bumptech.glide.Glide;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/5/25.
 */
public class IStorePresenterImpl implements IStorePresenter, CallDialog.onConfirmListener, View.OnClickListener {
    private static final int REQUECT_CODE_SDCARD = 2;
    private CallDialog dialog;
    private String number;
    private IStoreView iStoreView;
    private String id; //店铺id

    public IStorePresenterImpl(IStoreView iStoreView, String id) {
        this.iStoreView = iStoreView;
        this.id = id;
    }

    @Override
    public void start() {
        getData();
        if (iStoreView != null && iStoreView.getLoadPager() != null) {
            iStoreView.getLoadPager().setonEmptyClickListener(this);
            iStoreView.getLoadPager().setonErrorClickListener(this);
        }
    }

    //加载店铺信息
    private void getData() {
        iStoreView.getLoadPager().setLoadVisable();
        RequestParams params = new RequestParams(Constants.URL_STORE_DETAIL);
        params.addBodyParameter("id", id);
        params.addBodyParameter("account", CacheUtils.getString(iStoreView.getmActivity(), "number", ""));
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                StoreDetail storeDetail = GsonParse.getObject(UtilMethod.getString(s), StoreDetail.class);
                if (null != storeDetail) {
                    StoreDetail.ShopcartEntityBean shopcartEntity = storeDetail.getShopcartEntity();
                    StoreDetail.ShopEntityBean shopEntity = storeDetail.getShopEntity();

                    if (shopcartEntity != null) {
                        int totalcount = shopcartEntity.getTotalcount();
                        if (totalcount > 0) {
                            if (totalcount > 99) {
                                totalcount = 99;
                            }
                            //设置购物车的数量
                            if (iStoreView != null) {
                                if (iStoreView.getNormalTopbar() != null) {
                                    iStoreView.getNormalTopbar().setBusText(totalcount + "");
                                    iStoreView.getNormalTopbar().setBusTextVisibility(true);

                                }
                            }

                        } else {
                            if (iStoreView.getNormalTopbar() != null) {
                                iStoreView.getNormalTopbar().setBusTextVisibility(false);

                            }
                        }
                    }


                    if (shopEntity != null) {
                        //地址
                        String address = shopEntity.getAddress();
                        String remark = shopEntity.getRemark();
                        String bigPicture = shopEntity.getBigPicture();
                        String shopName = shopEntity.getShopName();
                        //店铺的类别
                        String shopCatalogId = shopEntity.getShopCatalogId();

                        //评分
                        String score = shopEntity.getScore();
                        //商品评分
                        double productsScore = shopEntity.getProductsScore();
                        //服务评分
                        double servicesScore = shopEntity.getServicesScore();

                        //营业状态
                        String status = shopEntity.getStatus();
                        //营业时间
                        String time = shopEntity.getSaleTime();
                        //电话
                        number = shopEntity.getTelPhone();


                        if (iStoreView != null) {
                            if (iStoreView.getTextViewName() != null) {
                                iStoreView.getTextViewName().setText(shopName);
                            }
                            if (iStoreView.getTextViewTime() != null) {
                                if (!TextUtils.isEmpty(time)) {
                                    iStoreView.getTextViewTime().setText(time);
                                }

                            }
                            //设置商品图片
                            if (iStoreView.getSimpleDrawView() != null) {
                              //  iStoreView.getSimpleDrawView().setImageURI(Uri.parse(UtilMethod.url(bigPicture)));
                                Glide.with(iStoreView.getmActivity()).load(UtilMethod.url(bigPicture)).placeholder(R.mipmap.viewpager_zwt).error(R.mipmap.viewpager_zwt).into(iStoreView.getSimpleDrawView());

                            }

                            if (iStoreView.getTextViewAddress() != null) {
                                iStoreView.getTextViewAddress().setText(remark+address);
                            }
                            if (iStoreView.getTextViewtype() != null) {
                                if (!TextUtils.isEmpty(shopCatalogId)) {
                                    String[] split = shopCatalogId.split(",");
                                    if (split != null && split.length > 0) {
                                        String type = ConstantsData.getMapString(split[0]);
                                        if (!TextUtils.isEmpty(type)) {

                                            iStoreView.getTextViewtype().setText(type);
                                        }
                                    }
                                }


                            }
                            if (iStoreView.getTextViewStatus() != null) {
                                //10 未营业 20营业中 30暂停营业
                                if ("10".equals(status)) {
                                    iStoreView.getTextViewStatus().setText("未营业");
                                } else if ("20".equals(status)) {
                                    iStoreView.getTextViewStatus().setText("营业中");
                                } else if ("30".equals(status)) {
                                    iStoreView.getTextViewStatus().setText("暂停营业");
                                }
                            }
                        }

                        iStoreView.setViewpager(productsScore, servicesScore);

                        if (iStoreView.getLoadPager() != null) {
                            iStoreView.getLoadPager().hide();
                        }
                    } else {
                        if (iStoreView.getLoadPager() != null) {
                            iStoreView.getLoadPager().setEmptyVisable();
                        }
                    }
                }


            /*    if (iStoreView.getUpHideScrollView() != null) {
                    iStoreView.getUpHideScrollView().setVisibility(View.VISIBLE);
                }*/

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (iStoreView.getLoadPager() != null) {
                    iStoreView.getLoadPager().setErrorVisable();
                }

               /* if (iStoreView.getUpHideScrollView() != null) {
                    iStoreView.getUpHideScrollView().setVisibility(View.VISIBLE);
                }*/
            }
        });
    }

    @Override
    public void call() {
        if (TextUtils.isEmpty(number)) {
            UtilMethod.Toast(iStoreView.getContext(), "电话号码有误");
        } else {
            dialog = new CallDialog(iStoreView.getContext(), this, number, "呼叫");
            if (!iStoreView.getmActivity().isFinishing()) {
                dialog.show();
            }
        }
    }

    @Override
    public void ondestroy() {
        //防止dismiss产生异常
        if (dialog != null) {
            dialog = null;
        }
    }

    //呼叫对话框的确认回调
    @Override
    public void comfirm() {
        if (TextUtils.isEmpty(number)) {
            UtilMethod.Toast(iStoreView.getContext(), "电话号码有误");
        } else {
            //开启呼叫界面,这里因为需要权限,且如果没权限 会产生异常所以要try
            // iStoreView.getPermissions();

            callPhone();


        }
    }

    /**
     * 开启打电话界面
     */
    private void callPhone() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:" + number));
            iStoreView.getmActivity().startActivity(intent);
        } catch (Exception e) {
            UtilMethod.Toast(iStoreView.getContext(), "没有拨打电话的权限");
        }
    }


    //呼叫对话框的取消回调
    @Override
    public void cancel() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_error:  //加载错误时的点击事件

                getData();

                break;
            case R.id.frame_empty://数据为空的点击事件
                getData();
                break;
        }
    }
}
