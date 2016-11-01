package com.betterda.xsnano.sale.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.application.MyApplication;
import com.betterda.xsnano.goods.GoodsDetail;
import com.betterda.xsnano.interfac.ParserJsonInterface;
import com.betterda.xsnano.javabean.SaleDay;
import com.betterda.xsnano.sale.SaleGoodsActivity;
import com.betterda.xsnano.sale.model.Sale;
import com.betterda.xsnano.sale.model.SaleGoods;
import com.betterda.xsnano.sale.view.ISaleView;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.TimeTool;
import com.betterda.xsnano.util.UtilMethod;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;

/**
 * Created by Administrator on 2016/5/20.
 */
public class ISalePresentereImpl implements ISalePresenter, View.OnClickListener {
    private ISaleView iSaleView;
    private RecyclerView recyclerView;
    private List<Sale> list;
    private CommonAdapter adapter;
    private Thread thread;
    private boolean isEnd; //控制计时线程的结束
   // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd天HH时mm分ss秒");
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                adapter.notifyDataSetChanged();

            }
        }
    };

    public ISalePresentereImpl(ISaleView iSaleView) {
        this.iSaleView = iSaleView;
    }

    @Override
    public void start() {

        if (iSaleView != null && iSaleView.getLoadPager() != null) {
            iSaleView.getLoadPager().setonEmptyClickListener(this);
            iSaleView.getLoadPager().setonErrorClickListener(this);
        }

        list = new ArrayList<>();
        recyclerView = iSaleView.getRecycleView();
        recyclerView.setLayoutManager(new LinearLayoutManager(iSaleView.getContext()));
        adapter = new CommonAdapter<Sale>(iSaleView.getContext(), R.layout.item_recycleview_sale, list) {
            @Override
            public void convert(ViewHolder viewHolder, final Sale sale) {
                if (sale != null) {
                    viewHolder.setText(R.id.tv_sale_price, sale.getPrice()+"元");
                    viewHolder.setText(R.id.tv_sale_name, sale.getName());
                    viewHolder.setText(R.id.tv_sale_day, sale.getTime());

                    if (!TextUtils.isEmpty(sale.getUrl())) {
                        SimpleDraweeView simpleDraweeView = viewHolder.getView(R.id.sv_sale);
                        simpleDraweeView.setImageURI(Uri.parse(sale.getUrl()));
                    }

                    //点击进入商品想起页面
                    viewHolder.setOnClickListener(R.id.linear_sale, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ("已结束".equals(sale.getTime())) {
                                UtilMethod.Toast(iSaleView.getmActivity(), "活动已经结束");
                            } else {
                                ((MyApplication) (iSaleView.getmActivity().getApplication())).setProductid(sale.getProductId());
                                ((MyApplication) (iSaleView.getmActivity().getApplication())).setShopid(sale.getShopId());
                                Intent intent = new Intent(iSaleView.getmActivity(), GoodsDetail.class);
                                iSaleView.getmActivity().startActivity(intent);
                            }
                        }
                    });


                }
            }
        };
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams(Constants.URL_SALE);
        params.addBodyParameter("regionId", Constants.regiondId);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                List<SaleDay> listSaleDay = GsonParse.getListSaleDay(UtilMethod.getString(s));
                for (SaleDay saleDay : listSaleDay) {
                    if (saleDay != null) {
                        Sale sale = new Sale();
                        long time = saleDay.getEnd_date_sec()*1000 ;

                        sale.setTimemillons(TimeTool.getTime(time));

                        sale.setName(saleDay.getName());
                        try {
                            sale.setPrice(Float.parseFloat(saleDay.getPrice())+"");
                        } catch (Exception e) {

                        }

                        sale.setUrl(UtilMethod.url(saleDay.getBig_picture()));
                        sale.setProductId(saleDay.getProduct_id());
                        sale.setTime(TimeTool.getDifference(sale.getTimemillons()));
                        sale.setShopId(saleDay.getShop_id());
                        list.add(sale);
                    }
                }


                if (iSaleView.getLoadPager() != null) {
                    if (list != null) {
                        if (list.size() == 0) {
                            iSaleView.getLoadPager().setEmptyVisable();

                        } else {
                            iSaleView.getLoadPager().hide();
                        }
                    } else {
                        iSaleView.getLoadPager().setEmptyVisable();
                    }
                }

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                //开启倒计时
                startTime();
                if (iSaleView.getRecycleView() != null) {
                    iSaleView.getRecycleView().setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                
                if (iSaleView.getLoadPager() != null) {
                    iSaleView.getLoadPager().setErrorVisable();
                }

            }
        });
    }


    /**
     * 开启计时
     */
    public void startTime() {
        thread = new Thread() {
            public void run() {
                while (!isEnd) {
                    try {
                        if (list == null) {
                            break;
                        }

                        for (Sale person : list) {
                            //先判断是否结束了
                            if (null != person) {

                                if (!"已结束".equals(person.getTime())) {
                                    if (person.getTimemillons() < 1000) {
                                        //时间差小于1表示结束
                                        person.setTime("已结束");

                                    } else {
                                        //每次将时间减少1000 表示1秒
                                        long time = person.getTimemillons() - 1000;
                                        person.setTimemillons(time);
                                        //重新改下time的值

                                        person.setTime(TimeTool.getDifference(person.getTimemillons()));



                                    }
                                }
                            }
                        }
                        //每隔1毫秒更新一次界面，如果只需要精确到秒的倒计时此处改成1000即可
                        handler.sendEmptyMessageDelayed(1, 1000);
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }


    @Override
    public void onDestroy() {
        isEnd = false;
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
