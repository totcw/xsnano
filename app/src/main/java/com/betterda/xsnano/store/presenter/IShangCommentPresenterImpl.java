package com.betterda.xsnano.store.presenter;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.goods.model.Comment;
import com.betterda.xsnano.interfac.OnListLoadNextPageListener;
import com.betterda.xsnano.javabean.CommentP;
import com.betterda.xsnano.javabean.StoreAndServices;
import com.betterda.xsnano.store.view.IShangPingView;
import com.betterda.xsnano.store.view.IShangpCommentView;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.RecyclerViewStateUtils;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.EndlessRecyclerOnScrollListener;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingFooter;
import com.betterda.xsnano.view.StoreRecycleView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/25.
 */
public class IShangCommentPresenterImpl implements IShangPCommentPresenter, View.OnClickListener {
    private IShangpCommentView iShangpCommentView;
    private StoreRecycleView recyclerView;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<Comment> list;
    private String id;
    private int page = 1;
    private String rows = "10";
    private double productScore;//商品评分
    private double serviceScore;//服务评分

    public IShangCommentPresenterImpl(IShangpCommentView iShangpCommentView, String id, double productScore, double serviceScore) {
        this.iShangpCommentView = iShangpCommentView;
        this.id = id;
        this.productScore = productScore;
        this.serviceScore = serviceScore;
    }

    @Override
    public void start() {
        list = new ArrayList<>();

        recyclerView = iShangpCommentView.getRecycleView();
        //解决嵌套scrollview 惯性消失问题
       recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(iShangpCommentView.getmActivity()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Comment>(iShangpCommentView.getmActivity(), R.layout.item_recycleview_comment, list) {

            @Override
            public void convert(ViewHolder viewHolder, Comment comment) {
                if (comment != null) {
                    viewHolder.setText(R.id.tv_comment_content, comment.getContent());
                    viewHolder.setText(R.id.tv_comment_name, comment.getName());
                    viewHolder.setText(R.id.tv_comment_time, comment.getTime());
                    SimpleDraweeView simpleDraweeView = viewHolder.getView(R.id.sv_comment);
                    if (!TextUtils.isEmpty(comment.getUrl())) {
                        simpleDraweeView.setImageURI(Uri.parse(comment.getUrl()));
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setonListLoadNextPageListener(new OnListLoadNextPageListener() {
            @Override
            public void onLoadNextPage(View view) {
                //设置加载更多
                if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(recyclerView)) {
                    RecyclerViewStateUtils.setFooterViewState(iShangpCommentView.getmActivity(), recyclerView, Constants.PAGE_SIZE, LoadingFooter.State.Loading, null);
                    page++;
                    getData();

                }
            }
        });

        getData();
        if (iShangpCommentView != null && iShangpCommentView.getLoadPager() != null) {
            iShangpCommentView.getLoadPager().setonEmptyClickListener(this);
            iShangpCommentView.getLoadPager().setonErrorClickListener(this);
        }
    }

    private void getData() {
       // iShangpCommentView.getLoadPager().setLoadVisable();
        RequestParams params = new RequestParams(Constants.URL_STORE_COMMENT);
        params.addBodyParameter("id", id);
        params.addBodyParameter("page", page + "");
        params.addBodyParameter("rows", rows);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                if (list != null) {
                    if (page == 1) {
                        list.clear();
                    }
                }
                List<CommentP> listCommentP = GsonParse.getListCommentP(UtilMethod.getString(s));
                if (null != listCommentP) {
                    for (CommentP commentP : listCommentP) {
                        CommentP.CommentEntityBean commentEntity = commentP.getCommentEntity();
                        if (null != commentEntity) {
                            Comment comment = new Comment();
                            comment.setName(commentEntity.getNick_name());
                            comment.setTime(commentEntity.getCreate_date());
                            comment.setContent(commentEntity.getContent());
                            comment.setUrl(UtilMethod.url(commentEntity.getUser_img()));
                            if (list != null) {
                                list.add(comment);
                            }
                        }
                    }
                }

                RecyclerViewStateUtils.setLoad(listCommentP, recyclerView, iShangpCommentView.getmActivity());
                if (null != adapter) {

                    adapter.notifyDataSetChanged();
                }
                //设置评分
                double score = (productScore + serviceScore) / 2;
                if (iShangpCommentView.getTextViewSum() != null) {
                    iShangpCommentView.getTextViewSum().setText(UtilMethod.FloatFormat1((float) score));
                }
                if (iShangpCommentView.getTextViewService() != null) {
                    iShangpCommentView.getTextViewService().setText(UtilMethod.FloatFormat1((float) serviceScore));
                }
                if (iShangpCommentView.getTextViewyonghu() != null) {
                    iShangpCommentView.getTextViewyonghu().setText(UtilMethod.FloatFormat1((float) productScore));
                }
                try {
                    if (iShangpCommentView.getRatingBarService() != null) {
                        iShangpCommentView.getRatingBarService().setRating(Float.parseFloat(UtilMethod.FloatFormat1((float) serviceScore)));

                    }
                    if (iShangpCommentView.getRatingBaryonghu() != null) {
                        iShangpCommentView.getRatingBaryonghu().setRating(Float.parseFloat(UtilMethod.FloatFormat1((float) productScore)));
                    }
                } catch (Exception e) {

                }


                //判断是否有数据
                if (iShangpCommentView.getLoadPager() != null) {
                    if (list != null) {
                        if (list.size() == 0) {
                            iShangpCommentView.getLoadPager().setEmptyVisable();
                        } else {
                            iShangpCommentView.getLoadPager().hide();
                        }
                    } else {
                        iShangpCommentView.getLoadPager().setEmptyVisable();
                    }
                }


                if (iShangpCommentView.getLinearLayout() != null) {
                    iShangpCommentView.getLinearLayout().setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                //如果是上拉加载错误显示加载错误
                if (page == 1) {
                    if (iShangpCommentView.getLoadPager() != null) {
                        iShangpCommentView.getLoadPager().setErrorVisable();
                    }
                } else {
                    RecyclerViewStateUtils.setFooterViewState(iShangpCommentView.getmActivity(), recyclerView,
                            Constants.PAGE_SIZE, LoadingFooter.State.NetWorkError, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData();
                                }
                            });
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
