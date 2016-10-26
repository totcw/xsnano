package com.betterda.xsnano.store.presenter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;

import com.betterda.xsnano.R;
import com.betterda.xsnano.application.MyApplication;
import com.betterda.xsnano.goods.GoodsDetail;
import com.betterda.xsnano.javabean.StoreAndServices;
import com.betterda.xsnano.pay.PayActivity;
import com.betterda.xsnano.store.model.Business;
import com.betterda.xsnano.store.model.Service;
import com.betterda.xsnano.store.view.IShangPingView;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.StoreRecycleView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/25.
 */
public class IShangPingPresenterImpl implements IShangPingPresenter, View.OnClickListener {
    private IShangPingView iShangPingView;
    private StoreRecycleView rv_shangp1;
    private StoreRecycleView rv_shangp2;
    private List<Service> serviceList; //服务的容器
    private List<Business> businessList; //业务的容器
    private int selectService; //服务的选中
    private int color = 0xFF909090;
    private CommonAdapter adapterBussiness, adapterService;
    private String id;

    public IShangPingPresenterImpl(IShangPingView iShangPingView, String id) {
        this.iShangPingView = iShangPingView;
        this.id = id;
    }

    @Override
    public void start() {

        businessList = new ArrayList<>();
        serviceList = new ArrayList<>();
        rv_shangp1 = iShangPingView.getRecycleView1();
        rv_shangp2 = iShangPingView.getRecycleView2();
        rv_shangp1.setNestedScrollingEnabled(false);
        rv_shangp2.setNestedScrollingEnabled(false);
        rv_shangp1.setLayoutManager(new LinearLayoutManager(iShangPingView.getmActivity()));
        rv_shangp2.setLayoutManager(new LinearLayoutManager(iShangPingView.getmActivity()));
        adapterService = new CommonAdapter<Service>(iShangPingView.getmActivity(), R.layout.item_pp_main_sort, serviceList) {

            @Override
            public void convert(final ViewHolder viewHolder, final Service service) {
                if (service != null) {
                    viewHolder.setText(R.id.mainitem_txt, service.getName());
                    View view = viewHolder.getView(R.id.mainitem_img);
                    View viewLayout = viewHolder.getView(R.id.mainitem_layout);
                    if (viewHolder.getPosition() == selectService) {
                        view.setVisibility(View.VISIBLE);
                        viewHolder.setTextColor(R.id.mainitem_txt, iShangPingView.getmActivity().getResources()
                                .getColor(R.color.shouye_fenlei_red));
                        viewLayout.setSelected(false);
                    } else {
                        view.setVisibility(View.INVISIBLE);
                        viewHolder.setTextColor(R.id.mainitem_txt, color);
                        viewLayout.setSelected(true);
                    }

                    viewHolder.setOnClickListener(R.id.linear_main_sort, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectService = viewHolder.getPosition();

                            //更新业务的recycleview
                            if (businessList == null) {
                                businessList = new ArrayList<Business>();
                            }
                            businessList.clear();
                            if (null != service.getList()) {
                                for (Business business : service.getList()) {
                                    businessList.add(business);
                                }
                            }

                            adapterBussiness.notifyDataSetChanged();
                            notifyDataSetChanged();
                        }
                    });

                }
            }
        };
        rv_shangp1.addItemDecoration(new DividerItemDecoration(iShangPingView.getmActivity(), DividerItemDecoration.VERTICAL_LIST));
        rv_shangp1.setAdapter(adapterService);

        adapterBussiness = new CommonAdapter<Business>(iShangPingView.getmActivity(), R.layout.item_recycleview_business, businessList) {

            @Override
            public void convert(ViewHolder viewHolder, final Business business) {
                if (business != null) {
                    viewHolder.setText(R.id.tv_business_name, business.getName());
                    viewHolder.setText(R.id.tv_business_jianjie, business.getIntroduction());
                    viewHolder.setText(R.id.tv_business_amout, "月销" + business.getAmount() + "单");
                    viewHolder.setText(R.id.tv_business_newprice, "￥" + business.getNewPrice());
                    viewHolder.setText(R.id.tv_business_price, "￥" + business.getPrice());

                    viewHolder.setVisible(R.id.relative_busineww, false);
                    viewHolder.setVisible(R.id.tv_business_amout, true);
                    viewHolder.setVisible(R.id.tv_business_newprice, true);
                    SimpleDraweeView view = viewHolder.getView(R.id.sv_item_business);
                    if (view != null) {
                        view.setImageURI(Uri.parse(business.getUrl()));
                    }

                    viewHolder.setOnClickListener(R.id.linear_business, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(iShangPingView.getmActivity(), GoodsDetail.class);
                         /*   intent.putExtra("id", business.getProductId());
                            intent.putExtra("shopid", id);*/

                            ((MyApplication) (iShangPingView.getmActivity().getApplication())).setProductid(business.getProductId());
                            ((MyApplication) (iShangPingView.getmActivity().getApplication())).setShopid(id);
                            iShangPingView.getmActivity().startActivity(intent);
                        }
                    });

                }
            }
        };

        rv_shangp2.setAdapter(adapterBussiness);

        getData();
        if (iShangPingView != null && iShangPingView.getLoadPager() != null) {
            iShangPingView.getLoadPager().setonEmptyClickListener(this);
            iShangPingView.getLoadPager().setonErrorClickListener(this);
        }
    }

    private void getData() {
        iShangPingView.getLoadPager().setLoadVisable();
        RequestParams params = new RequestParams(Constants.URL_SERVICES);
        params.addBodyParameter("id", id);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                StoreAndServices storeAndServices = GsonParse.getObject(UtilMethod.getString(s), StoreAndServices.class);
                List<StoreAndServices.ParamsBean> params1 = storeAndServices.getParams();
                if (null != params1) {
                    for (StoreAndServices.ParamsBean bean : params1) {
                        if (bean != null) {
                            //该服务下的所有商品
                            List<StoreAndServices.ParamsBean.ProductsBean> products = bean.getProducts();
                            //服务
                            StoreAndServices.ParamsBean.ServiceEntityBean serviceEntity = bean.getServiceEntity();
                            if (null != serviceEntity && products != null) {
                                Service service = new Service();
                                service.setName(serviceEntity.getShopServiceName());
                                List<Business> businessList = new ArrayList<Business>();
                                for (StoreAndServices.ParamsBean.ProductsBean productsBean : products) {
                                    Business business = new Business();
                                    business.setName(productsBean.getName());
                                    business.setAmount(productsBean.getSellCount());
                                    business.setIntroduction(productsBean.getIntroduce());
                                    try {
                                    business.setPrice(Float.parseFloat(productsBean.getPrice())+"");
                                    } catch (Exception e) {

                                    }
                                    try {

                                    business.setNewPrice(Float.parseFloat(productsBean.getSalePrice())+"");
                                    } catch (Exception e) {

                                    }
                                    business.setProductId(productsBean.getId());
                                    business.setUrl(UtilMethod.url(productsBean.getLittlePicture()));
                                    businessList.add(business);
                                }
                                service.setList(businessList);

                                if (serviceList != null) {
                                    serviceList.add(service);
                                }
                            }

                        }
                    }
                }


                //初始化第一条业务的数据
                if (businessList == null) {
                    businessList = new ArrayList<Business>();
                }
                businessList.clear();
                for (Business business : serviceList.get(selectService).getList()) {
                    businessList.add(business);
                }
                adapterBussiness.notifyDataSetChanged();
                adapterService.notifyDataSetChanged();


                if (serviceList != null) {
                    if (serviceList.size() == 0) {
                        if (iShangPingView.getLoadPager() != null) {
                            iShangPingView.getLoadPager().setEmptyVisable();
                        }
                    } else {
                        if (iShangPingView.getLoadPager() != null) {
                            iShangPingView.getLoadPager().hide();
                        }
                    }
                } else {
                    if (iShangPingView.getLoadPager() != null) {
                        iShangPingView.getLoadPager().setEmptyVisable();
                    }
                }

                if (iShangPingView.getLinearLayout() != null) {
                    iShangPingView.getLinearLayout().setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                System.out.println("服务范围错误"+throwable);
                if (iShangPingView.getLoadPager() != null) {
                    iShangPingView.getLoadPager().setErrorVisable();
                }
            }
        });
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
