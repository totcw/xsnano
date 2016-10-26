package com.betterda.xsnano.comment.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.comment.view.ICommentView;
import com.betterda.xsnano.goods.model.Comment;
import com.betterda.xsnano.javabean.CommentP;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.RecyclerViewStateUtils;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.EndlessRecyclerOnScrollListener;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingFooter;
import com.betterda.xsnano.view.LoadingPager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class ICommentPresenterImpl implements ICommentPresenter, View.OnClickListener {
    private ICommentView commentView;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<Comment> list;
    private String productId;
    private RecyclerView recycleview;
    private int page =1;

    public ICommentPresenterImpl(ICommentView commentView) {
        this.commentView = commentView;
    }

    @Override
    public void start() {
        list = new ArrayList<>();
        Intent intent = commentView.getmActivity().getIntent();
        if (null != intent) {
            productId =  intent.getStringExtra("id");
        }

        recycleview = commentView.getRecycleview();
        recycleview.setLayoutManager(new LinearLayoutManager(commentView.getmActivity()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Comment>(commentView.getmActivity(), R.layout.item_recycleview_comment, list) {

            @Override
            public void convert(ViewHolder viewHolder, Comment comment) {
                if (comment != null) {
                    viewHolder.setText(R.id.tv_comment_content, comment.getContent());
                    viewHolder.setText(R.id.tv_comment_name, comment.getName());
                    viewHolder.setText(R.id.tv_comment_time, comment.getTime());
                    SimpleDraweeView simpleDraweeView = viewHolder.getView(R.id.sv_comment);
                    if (simpleDraweeView != null) {
                        if (!TextUtils.isEmpty(comment.getUrl())) {
                            simpleDraweeView.setImageURI(Uri.parse(comment.getUrl()));
                        }
                    }
                }
            }
        });
        recycleview.setAdapter(adapter);
        recycleview.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                //滑到了最后,如果是正常状态才开始加载
                if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(recycleview)) {

                    RecyclerViewStateUtils.setFooterViewState(commentView.getmActivity(), recycleview, Constants.PAGE_SIZE, LoadingFooter.State.Loading, null);

                    page++;
                    getData();
                }
            }


        });

        getData();
        if (commentView != null && commentView.getLoadPager() != null) {
            commentView.getLoadPager().setonEmptyClickListener(this);
            commentView.getLoadPager().setonErrorClickListener(this);
        }
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
    private void getData() {
        RequestParams params = new RequestParams(Constants.URL_COMMENT_GET);
        params.addBodyParameter("page", page+"");
        params.addBodyParameter("rows", Constants.PAGE_SIZE+"");
        params.addBodyParameter("productId", productId);
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
                        if (commentP != null) {
                            CommentP.CommentEntityBean commentEntity = commentP.getCommentEntity();
                            if (commentEntity != null) {
                                Comment comment = new Comment();
                                comment.setContent(commentEntity.getContent());
                                comment.setUrl(UtilMethod.url(commentEntity.getUser_img()));
                                comment.setName(commentEntity.getNick_name());
                                comment.setTime(commentEntity.getCreate_date());
                                if (list != null) {
                                    list.add(comment);
                                }
                            }
                        }
                    }
                }

                RecyclerViewStateUtils.setLoad(listCommentP, recycleview, commentView.getmActivity());
                //上面的解析是在for中所以等解析完 了在刷新
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                LoadingPager loadPager = commentView.getLoadPager();
                if (loadPager != null) {
                    if (list != null) {
                        if (list.size() == 0) {
                            loadPager.setEmptyVisable();
                        } else {
                            loadPager.hide();
                        }
                    } else {
                        loadPager.setEmptyVisable();
                    }
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                LoadingPager loadPager = commentView.getLoadPager();
                //如果是上拉加载错误显示加载错误
                if (page == 1) {
                    if (loadPager != null) {
                        loadPager.setErrorVisable();
                    }
                } else {
                    RecyclerViewStateUtils.setFooterViewState(commentView.getmActivity(), recycleview,
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
}
