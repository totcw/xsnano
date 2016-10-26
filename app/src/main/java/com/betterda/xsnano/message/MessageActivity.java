package com.betterda.xsnano.message;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.message.model.Message;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.youhui.model.YouHui;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息
 * Created by lyf
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back, iv_more;
    private RecyclerView rv_messag;
    private LoadingPager loadpager_message;
    private List<Message> list;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private PopupWindow popupWindow;

    @Override
    public void initView() {
        setContentView(R.layout.activity_message);
        iv_back = (ImageView) findViewById(R.id.iv_message_back);
        iv_more = (ImageView) findViewById(R.id.iv_message_more);
        rv_messag = (RecyclerView) findViewById(R.id.rv_message);
        loadpager_message = (LoadingPager) findViewById(R.id.loadpager_message);
    }

    @Override
    public void initListener() {
        iv_back.setOnClickListener(this);
        iv_more.setOnClickListener(this);
    }

    @Override
    public void init() {
        list = new ArrayList<>();
        loadpager_message.setLoadVisable();
        rv_messag.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Message>(this, R.layout.item_recycleview_message, list) {


            @Override
            public void convert(ViewHolder viewHolder, Message message) {
                if (null != message) {
                    viewHolder.setText(R.id.tv_message_name, message.getName());
                    viewHolder.setText(R.id.tv_message_content, message.getContent());
                }
            }
        });
        rv_messag.setAdapter(adapter);
        RequestParams params = new RequestParams("http://www.baidu.com");
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                if (loadpager_message != null) {
                    loadpager_message.hide();
                }

                for (int i = 0; i < 5; i++) {
                    Message message = new Message();
                    message.setContent("消息内容" + i);
                    message.setName("消息名" + i);
                    list.add(message);

                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                if (rv_messag != null) {
                    rv_messag.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(rv_messag);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_message_back:
                finish();
                break;
            case R.id.iv_message_more:
                View view = View.inflate(this, R.layout.pp_message, null);
                LinearLayout linear_read = (LinearLayout) view.findViewById(R.id.linear_message_read);
                LinearLayout linear_delete = (LinearLayout) view.findViewById(R.id.linear_message_delete);
                linear_delete.setOnClickListener(this);
                linear_read.setOnClickListener(this);
                showPopupWindow(view);
                break;
            case R.id.linear_message_read:
                close();
                break;
            case R.id.linear_message_delete:
                if (list != null) {
                    list.clear();
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                close();
                break;
        }
    }

    public void showPopupWindow(View view) {

        // 如果activity不在运行 就返回
        if (this.isFinishing()) {
            return;
        }
        popupWindow = new PopupWindow(view, -2, -2);
        // 设置点到外面可以取消,下面这2句要一起
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.update();
        //设置为true 会拦截事件,pop外部的控件无法获取到事件
        popupWindow.setFocusable(true);
        //设置可以触摸
        popupWindow.setTouchable(true);
        UtilMethod.backgroundAlpha(0.7f, this);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        if (popupWindow != null) {
            if (!popupWindow.isShowing()) {
                popupWindow.showAsDropDown(iv_more);
            }

        }
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                UtilMethod.backgroundAlpha(1.0f, MessageActivity.this);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        close();
    }

    private void close() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            popupWindow = null;
        }
    }


    //0则不执行拖动或者滑动
    ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        /**
         * @param recyclerView
         * @param viewHolder 拖动的ViewHolder
         * @param target 目标位置的ViewHolder
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            return false;
        }
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                list.remove(position);
                adapter.notifyItemRemoved(position);

        }
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                //滑动时改变Item的透明度
                final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
                viewHolder.itemView.setAlpha(alpha);
                viewHolder.itemView.setTranslationX(dX);
            }
        }

    };



}
