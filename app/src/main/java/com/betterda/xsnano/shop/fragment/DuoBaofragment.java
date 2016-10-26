package com.betterda.xsnano.shop.fragment;

import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.baidu.mapapi.common.SysOSUtil;
import com.betterda.xsnano.R;
import com.betterda.xsnano.dialog.CallDialog;
import com.betterda.xsnano.dialog.EditDialog;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.interfac.ComfirmDuiHuanInterface;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.javabean.GoldGet;
import com.betterda.xsnano.javabean.Wallet;
import com.betterda.xsnano.login.LoginActivity;
import com.betterda.xsnano.shop.model.GoldDuoBao;
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

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 金币夺宝的fragment
 * Created by Administrator on 2016/5/27.
 */
public class DuoBaofragment extends BaseFragment implements View.OnClickListener {
    private LoadingPager loadpager_all;
    private RecyclerView rv_all;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<GoldDuoBao> list;
    private EditDialog dialog;
    private ComfirmDuiHuanInterface comfirmDuiHuanInterface;
    private int count = 1;//投注的金币数量
    private int goldCount; //用户金币个数
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


        list = new ArrayList<>();
        if (null == dialog) {
            dialog = new EditDialog(getmActivity(), new EditDialog.onConfirmListener() {
                @Override
                public void comfirm(String content) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if (comfirmDuiHuanInterface != null) {
                        comfirmDuiHuanInterface.comfirm(content);
                    }
                }

                @Override
                public void cancel() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            }, "确定");

        }

        rv_all.setLayoutManager(new GridLayoutManager(getmActivity(), 2));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<GoldDuoBao>(getmActivity(), R.layout.item_recycleview_duobao, list) {

            @Override
            public void convert(final ViewHolder viewHolder, final GoldDuoBao wallet) {

                if (wallet != null) {
                    viewHolder.setText(R.id.tv_item_duobao_name, wallet.getName());
                    viewHolder.setText(R.id.tv_item_duobao_jindu, (int) (wallet.getAlreadGold() * 100.0f / wallet.getNeedGold()) + "%");
                    SimpleDraweeView view = viewHolder.getView(R.id.sv_item_duobao);
                    ProgressBar progressBar = viewHolder.getView(R.id.pb_duobao);
                    progressBar.setMax(wallet.getNeedGold());
                    progressBar.setProgress(wallet.getAlreadGold());

                    if (view != null) {
                        if (!TextUtils.isEmpty(wallet.getUrl())) {

                            view.setImageURI(Uri.parse(wallet.getUrl()));

                        }
                    }

                }


                viewHolder.setOnClickListener(R.id.tv_item_duobao_buy, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!getmActivity().isFinishing()) {
                            comfirmDuiHuanInterface = new ComfirmDuiHuanInterface() {
                                @Override
                                public void comfirm(String content) {
                                    if (UtilMethod.isLogin(getmActivity())) {

                                        getData3(wallet.getProductid(), content);
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
    public void onStart() {
        super.onStart();

        getData();
    }

    private void getData3(final String productId, final String content) {
        RequestParams params = new RequestParams(Constants.URL__WALLET);
        params.addBodyParameter("account", CacheUtils.getString(getmActivity(), "number", ""));
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                Wallet object = GsonParse.getObject(UtilMethod.getString(s), Wallet.class);
                if (object != null) {
                    goldCount = object.getGolden();
                    getData2(productId, content);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                UtilMethod.Toast(getmActivity(), "投注失败");
            }
        });
    }


    /**
     * 投注
     */
    private void getData2(String productId, String content) {

        try {
            count = Integer.parseInt(content);
        } catch (Exception e) {

        }
        if (count > goldCount) {
            UtilMethod.Toast(getmActivity(), "您只有" + goldCount + "个金币");
            return;
        }
        if (shapeLoadingDialog == null) {
            shapeLoadingDialog = UtilMethod.createDialog(getmActivity(), "");
        }

        RequestParams params = new RequestParams(Constants.URL_JINBI_DUOBAO_TOU);
        params.addBodyParameter("account", CacheUtils.getString(getmActivity(), "number", ""));
        params.addBodyParameter("productId", productId);
        params.addBodyParameter("goldenCount", count + "");
        UtilMethod.showDialog(getmActivity(), shapeLoadingDialog);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(final String s) {

                GsonParse.parser(UtilMethod.getString(s), new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {
                        UtilMethod.dissmissDialog(getmActivity(), shapeLoadingDialog);
                        if ("true".equals(result)) {

                            getData();
                        }

                        UtilMethod.Toast(getmActivity(), resultMsg);

                    }
                });

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                UtilMethod.Toast(getmActivity(), "投注失败");
                UtilMethod.dissmissDialog(getmActivity(), shapeLoadingDialog);
            }
        });
    }

    private void getData() {

        if (loadpager_all != null) {
            loadpager_all.setLoadVisable();
        }
        RequestParams params = new RequestParams(Constants.URL_JINBI_DUOBAO);
        params.addBodyParameter("regionId", Constants.regiondId);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                List<GoldGet> listGoldGet = GsonParse.getListGoldGet(UtilMethod.getString(s));
                if (list != null) {
                    list.clear();
                }
                if (null != listGoldGet) {
                    for (GoldGet goldGet : listGoldGet) {
                        if (goldGet != null) {
                            GoldDuoBao goldDuoBao = new GoldDuoBao();
                            goldDuoBao.setUrl(UtilMethod.url(goldGet.getBigPicture()));
                            goldDuoBao.setName(goldGet.getProductName());
                            goldDuoBao.setProductid(goldGet.getProductId());
                            goldDuoBao.setAlreadGold(goldGet.getAlreadyGold());
                            goldDuoBao.setNeedGold(goldGet.getNeedGold());
                            if (list != null) {
                                list.add(goldDuoBao);
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
                if (null != rv_all) {

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
