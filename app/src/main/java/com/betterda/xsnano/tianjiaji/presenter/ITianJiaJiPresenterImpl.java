package com.betterda.xsnano.tianjiaji.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.goods.GoodsDetail;
import com.betterda.xsnano.tianjiaji.adapter.TianJiaJiAdapter;
import com.betterda.xsnano.tianjiaji.model.TianJiaJi;
import com.betterda.xsnano.tianjiaji.view.ITianJiaJiView;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.RecyclerViewStateUtils;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.EndlessRecyclerOnScrollListener;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingFooter;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class ITianJiaJiPresenterImpl implements ITianJiaJiPresenter {

    private ITianJiaJiView iTianJiaJiView;
    private List<TianJiaJi> list;
    private RecyclerView recycleView;

    private List<String> sortList, pinpaiList;
    private int selectSort, selectpinpai;
    private int color = 0xFF909090;
    private HeaderAndFooterRecyclerViewAdapter fadapter;
    private ArrayList<String> arrayList;


    public ITianJiaJiPresenterImpl(ITianJiaJiView iTianJiaJiView) {
        this.iTianJiaJiView = iTianJiaJiView;
    }

    @Override
    public void start() {
        Intent intent = iTianJiaJiView.getmActivity().getIntent();
        if (null != intent) {
            String title = intent.getStringExtra("title");
            arrayList = intent.getStringArrayListExtra("pinpai");
            iTianJiaJiView.getTopBar().setTitle(title);
        }


        list = new ArrayList<>();
        recycleView = iTianJiaJiView.getRecycleView();
        recycleView.setLayoutManager(new LinearLayoutManager(iTianJiaJiView.getContext()));
        TianJiaJiAdapter adapter = new TianJiaJiAdapter(this, iTianJiaJiView.getContext());
        fadapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recycleView.setAdapter(fadapter);
        recycleView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                //滑到了最后,如果是正常状态才开始加载
                if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(recycleView)) {

                    RecyclerViewStateUtils.setFooterViewState((Activity) iTianJiaJiView.getContext(), recycleView, Constants.PAGE_SIZE, LoadingFooter.State.Loading, null);
                    //getData();
                }
            }
        });

        getData();
    }

    /**
     *
     */
    private void getData() {
        RequestParams params = new RequestParams("");
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                if (iTianJiaJiView.getLoadPager() != null) {
                    iTianJiaJiView.getLoadPager().hide();
                }
                if (iTianJiaJiView.getLinearLayout() != null) {
                    for (int i = 0; i < 20; i++) {
                        TianJiaJi tianJiaJi = new TianJiaJi();
                        tianJiaJi.setAmount(i + "");
                        tianJiaJi.setComment(i + "");
                        tianJiaJi.setMoney(i + "");
                        tianJiaJi.setName("商品" + i);
                        list.add(tianJiaJi);

                    }
                    fadapter.notifyDataSetChanged();
                    iTianJiaJiView.getLinearLayout().setVisibility(View.VISIBLE);
                }


            }
        });
    }

    @Override
    public List<TianJiaJi> getList() {
        return list;
    }


    @Override
    public void onBindViewHolder(TianJiaJiAdapter.TianJiaJiHolder holder, int position) {

        if (list != null) {
            TianJiaJi tianJiaJi = list.get(position);
            holder.tv_name.setText(tianJiaJi.getName());
            holder.tv_money.setText("￥ " + tianJiaJi.getMoney());
            holder.tv_amount.setText("已售" + tianJiaJi.getAmount() + "件");
            holder.tv_comment.setText(tianJiaJi.getComment() + "条评论");
            if (!TextUtils.isEmpty(tianJiaJi.getUrl())) {
                holder.simpleDraweeView.setImageURI(Uri.parse(tianJiaJi.getUrl()));
            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UtilMethod.startIntent(iTianJiaJiView.getContext(), GoodsDetail.class);

                }
            });
        }


    }

    @Override
    public void clickFirst() {
        if (null == sortList) {
            sortList = new ArrayList<>();
            sortList.add("默认排序");
            sortList.add("销售从高到低");
            sortList.add("价格从高到低");
            sortList.add("价格从低到高");
        }
        //使用鸿洋的万能适配器
        iTianJiaJiView.getRecyclyView().setAdapter(new CommonAdapter<String>(iTianJiaJiView.getContext(), R.layout.item_pp_main_sort2, sortList) {


            @Override
            public void convert(final com.zhy.base.adapter.ViewHolder viewHolder, String s) {
                viewHolder.setText(R.id.sort2_txt, s);
                if (viewHolder.getPosition() == selectSort) {
                    viewHolder.setVisible(R.id.sort2_img, true);
                    viewHolder.setBackgroundColor(R.id.sort2_layout, iTianJiaJiView.getContext().getResources()
                            .getColor(R.color.bg_gray));
                    viewHolder.setTextColor(R.id.sort2_txt, iTianJiaJiView.getContext().getResources()
                            .getColor(R.color.shouye_fenlei_red));
                } else {
                    viewHolder.setVisible(R.id.sort2_img, false);
                    viewHolder.setBackgroundColor(R.id.sort2_layout, 0xFFFFFFFF);
                    viewHolder.setTextColor(R.id.sort2_txt, color);
                }
                viewHolder.setOnClickListener(R.id.sort2_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectSort = viewHolder.getPosition();
                        iTianJiaJiView.getTwoTool().setFirstTitle(sortList.get(selectSort));
                        iTianJiaJiView.closePopupWindow();
                    }
                });
            }
        });
        iTianJiaJiView.getRecyclyView().setLayoutManager(new LinearLayoutManager(iTianJiaJiView.getContext()));

    }

    @Override
    public void clickSecond() {
        if (null == pinpaiList) {
            pinpaiList = new ArrayList<>();
            pinpaiList.add("全部品牌");
            if (null != arrayList) {
                for (String s : arrayList) {
                    pinpaiList.add(s);
                }
            }
        }


        //使用鸿洋的万能适配器
        iTianJiaJiView.getRecyclyView().setAdapter(new CommonAdapter<String>(iTianJiaJiView.getContext(), R.layout.item_pp_main_sort2, pinpaiList) {


            @Override
            public void convert(final com.zhy.base.adapter.ViewHolder viewHolder, String s) {
                viewHolder.setText(R.id.sort2_txt, s);
                if (viewHolder.getPosition() == selectpinpai) {
                    viewHolder.setVisible(R.id.sort2_img, true);
                    viewHolder.setBackgroundColor(R.id.sort2_layout, iTianJiaJiView.getContext().getResources()
                            .getColor(R.color.bg_gray));
                    viewHolder.setTextColor(R.id.sort2_txt, iTianJiaJiView.getContext().getResources()
                            .getColor(R.color.shouye_fenlei_red));
                } else {
                    viewHolder.setVisible(R.id.sort2_img, false);
                    viewHolder.setBackgroundColor(R.id.sort2_layout, 0xFFFFFFFF);
                    viewHolder.setTextColor(R.id.sort2_txt, color);
                }
                viewHolder.setOnClickListener(R.id.sort2_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectpinpai = viewHolder.getPosition();
                        iTianJiaJiView.getTwoTool().setSecondTitle(pinpaiList.get(selectpinpai));
                        iTianJiaJiView.closePopupWindow();
                    }
                });
            }
        });
        iTianJiaJiView.getRecyclyView().setLayoutManager(new LinearLayoutManager(iTianJiaJiView.getContext()));


    }


}
