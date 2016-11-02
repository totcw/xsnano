package com.betterda.xsnano.shop.fragment;

import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.dialog.CallDialog;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.interfac.ComfirmDuiHuanInterface;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.interfac.ParserJsonInterface;
import com.betterda.xsnano.javabean.GoldChangeBean;
import com.betterda.xsnano.javabean.Wallet;
import com.betterda.xsnano.login.LoginActivity;
import com.betterda.xsnano.shop.model.GoldChange;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.ShapeLoadingDialog;
import com.betterda.xsnano.youhui.model.YouHui;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.w3c.dom.Text;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 金币兑换的fragment
 * Created by Administrator on 2016/5/27.
 */
public class DuiHuanfragment extends BaseFragment implements View.OnClickListener {
    private LoadingPager loadpager_all;
    private RecyclerView rv_all;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<GoldChange> list;
    private CallDialog dialog;
    private ComfirmDuiHuanInterface comfirmDuiHuanInterface;
    private int goldCount; //用户的金币个数
    private ShapeLoadingDialog shapeLoadingDialog;
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_all, null);
        loadpager_all = (LoadingPager) view.findViewById(R.id.loadpager_fragment_all);
        rv_all = (RecyclerView) view.findViewById(R.id.rv_fragment_all);
        return view;
    }

    @Override
    public void initData() {
        loadpager_all.setLoadVisable();
        list = new ArrayList<>();
        if (null == dialog) {
            dialog = new CallDialog(getmActivity(), new CallDialog.onConfirmListener() {
                @Override
                public void comfirm() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if (comfirmDuiHuanInterface != null) {
                        comfirmDuiHuanInterface.comfirm("");
                    }
                }

                @Override
                public void cancel() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            }, "确定要兑换吗?", "确定");

        }

        rv_all.setLayoutManager(new GridLayoutManager(getmActivity(), 2));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<GoldChange>(getmActivity(), R.layout.item_recycleview_duihuan, list) {

            @Override
            public void convert(final ViewHolder viewHolder, final GoldChange wallet) {

                if (wallet != null) {
                    viewHolder.setText(R.id.tv_item_duihuan_name, wallet.getName());
                    viewHolder.setText(R.id.tv_item_duihuan_price, wallet.getNeedGold()+"");
                    SimpleDraweeView simpleDraweeView = viewHolder.getView(R.id.sv_item_duihuan);
                    if (null != simpleDraweeView) {
                        if (!TextUtils.isEmpty(wallet.getUrl())) {
                            simpleDraweeView.setImageURI(Uri.parse(UtilMethod.url(wallet.getUrl())));
                        }
                    }
                }


                viewHolder.setOnClickListener(R.id.tv_item_duihuan_buy, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!getmActivity().isFinishing()) {
                            comfirmDuiHuanInterface = new ComfirmDuiHuanInterface() {
                                @Override
                                public void comfirm(String content) {
                                    if (UtilMethod.isLogin(getmActivity())) {

                                        getData3(wallet.getProductid(), wallet.getNeedGold());
                                    } else {
                                        UtilMethod.startIntent(getmActivity(), LoginActivity.class);
                                    }
                                }
                            };

                            if (dialog != null) {
                                dialog.show();
                            }
                        }
                    }
                });
            }
        });
        rv_all.setAdapter(adapter);
        if (loadpager_all != null && loadpager_all != null) {
            loadpager_all.setonEmptyClickListener(this);
            loadpager_all.setonErrorClickListener(this);
        }

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) { //可见
            getData();
        }
    }


    public void getDataAndPage() {
        if (loadpager_all != null) {
            loadpager_all.setLoadVisable();
        }
        getData();
    }

    /**
     * 兑换
     */
    private void getData2(String productId, final int count) {

        if (count > goldCount) {
            UtilMethod.Toast(getmActivity(),"您的金币数量不够");
            return;
        }
        if (shapeLoadingDialog == null) {
            shapeLoadingDialog = UtilMethod.createDialog(getmActivity(), "");
        }

        RequestParams params = new RequestParams(Constants.URL__JINBI_CHANGE2);
        params.addBodyParameter("account", CacheUtils.getString(getmActivity(), "number", ""));
        params.addBodyParameter("productId", productId);
        UtilMethod.showDialog(getmActivity(), shapeLoadingDialog);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                UtilMethod.dissmissDialog(getmActivity(),shapeLoadingDialog);
                GsonParse.parser(UtilMethod.getString(s), new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {
                        if ("true".equals(result)) {

                            getData();
                        }
                        UtilMethod.Toast(getmActivity(), resultMsg);
                    }
                });


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                UtilMethod.dissmissDialog(getmActivity(),shapeLoadingDialog);
            }
        });
    }

    private void getData() {


        RequestParams params = new RequestParams(Constants.URL_JINBI_CHANGE);
        params.addBodyParameter("regionId", Constants.regiondId);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                List<GoldChangeBean> listGoldChange = GsonParse.getListGoldChange(UtilMethod.getString(s));
                if (list != null) {
                    list.clear();
                }
                if (null != listGoldChange) {
                    for (GoldChangeBean goldChangeBean : listGoldChange) {
                        if (goldChangeBean != null) {
                            GoldChange goldChange = new GoldChange();
                            goldChange.setName(goldChangeBean.getProductName());
                            goldChange.setProductid(goldChangeBean.getProductId());
                            goldChange.setNeedGold(goldChangeBean.getSaleGold());
                            goldChange.setUrl(goldChangeBean.getSamallPicture());
                            if (list != null) {
                                list.add(goldChange);
                            }
                        }
                    }
                }

                if (null != adapter) {
                    adapter.notifyDataSetChanged();
                }

                if (loadpager_all != null) {

                    if (list != null) {
                        if (list.size() == 0) {
                            loadpager_all.setEmptyVisable();
                        } else {
                            loadpager_all.hide();
                        }
                    } else {
                        loadpager_all.setEmptyVisable();
                    }


                }

                if (rv_all != null) {

                    rv_all.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (loadpager_all != null) {
                    loadpager_all.setErrorVisable();


                }
            }
        });
    }


    private void getData3(final String productId, final int count) {
        RequestParams params = new RequestParams(Constants.URL__WALLET);
        params.addBodyParameter("account", CacheUtils.getString(getmActivity(),"number",""));
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                Wallet object = GsonParse.getObject(UtilMethod.getString(s), Wallet.class);
                if (object != null) {
                    goldCount = object.getGolden();
                    getData2(productId,count);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                UtilMethod.Toast(getmActivity(), "投注失败");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dialog = null;
        comfirmDuiHuanInterface = null;
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
