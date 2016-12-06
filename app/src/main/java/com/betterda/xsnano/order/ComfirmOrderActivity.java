package com.betterda.xsnano.order;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.order.presenter.IComfirmOrderPresenter;
import com.betterda.xsnano.order.presenter.IComfirmOrderPresenterImpl;
import com.betterda.xsnano.order.view.IComfirmOrderView;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 订单确认
 * Created by Administrator on 2016/5/19.
 */
public class ComfirmOrderActivity extends BaseActivity implements IComfirmOrderView, View.OnClickListener {
    private LoadingPager loadpger_order;
    private RecyclerView recyclerView;
    private NormalTopBar topBar;

    private FrameLayout frame_address;

    private TextView tv_order_shouhuoren2, tv_order_money, tv_order_address2, tv_order_number;

    private RelativeLayout relative_order_address, relative_order_addaddress, relative_order;
    private ImageView iv_order_pay;

    private IComfirmOrderPresenter comfirmOrderPresenter;
    ;

    @Override
    public void initView() {
        setContentView(R.layout.activity_confirmorder);
        loadpger_order = (LoadingPager) findViewById(R.id.loadpager_order);
        frame_address = (FrameLayout) findViewById(R.id.frame_address);
        tv_order_money = (TextView) findViewById(R.id.tv_order_money);
        tv_order_shouhuoren2 = (TextView) findViewById(R.id.tv_order_shouhuoren2);
        tv_order_address2 = (TextView) findViewById(R.id.tv_order_address2);
        tv_order_number = (TextView) findViewById(R.id.tv_order_number);
        relative_order = (RelativeLayout) findViewById(R.id.relative_order);
        iv_order_pay = (ImageView) findViewById(R.id.iv_order_pay);
        topBar = (NormalTopBar) findViewById(R.id.topbar_oder);
        relative_order_address = (RelativeLayout) findViewById(R.id.relative_order_address);
        relative_order_addaddress = (RelativeLayout) findViewById(R.id.relative_order_addaddress);
        recyclerView = (RecyclerView) findViewById(R.id.rv_confirmorder);
    }

    @Override
    public void initListener() {
        frame_address.setOnClickListener(this);
        iv_order_pay.setOnClickListener(this);
    }

    @Override
    public void init() {
        topBar.setTitle("订单确认");
        topBar.setOnBackListener(this);
        loadpger_order.setLoadVisable();
        comfirmOrderPresenter = new IComfirmOrderPresenterImpl(this);
        comfirmOrderPresenter.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_address:
                comfirmOrderPresenter.address();
                break;
            case R.id.relative_order_fapiao:
              /*  View view = View.inflate(this, R.layout.pp_choose_fapiao, null);
                TextView tv_shi = (TextView) view.findViewById(R.id.tv_fapiao_shi);
                TextView tv_fou = (TextView) view.findViewById(R.id.tv_fapiao_fou);
                tv_shi.setOnClickListener(this);
                tv_fou.setOnClickListener(this);
                setUpPopupWindow(view, null, 1, UtilMethod.getHeight(this));*/
                break;
            case R.id.relative_order_youhuiquan:
          /*      View view2 = View.inflate(this, R.layout.pp_choose_peisong, null);
                TextView tv_ziti = (TextView) view2.findViewById(R.id.tv_peisong_ziti);
                TextView tv_kuaidi = (TextView) view2.findViewById(R.id.tv_peisong_kuaidi);
                tv_kuaidi.setOnClickListener(this);
                tv_ziti.setOnClickListener(this);
                setUpPopupWindow(view2, null, 1, UtilMethod.getHeight(this));*/
                break;
            case R.id.iv_order_pay:
                comfirmOrderPresenter.pay();
                break;
            case R.id.tv_peisong_ziti:
               // comfirmOrderPresenter.ziti();
               // closePopupWindow();
                break;
            case R.id.tv_peisong_kuaidi:
              //  comfirmOrderPresenter.kuaidi();
              //  closePopupWindow();
                break;
            case R.id.tv_fapiao_shi:
               // comfirmOrderPresenter.shi();
               // closePopupWindow();
                break;
            case R.id.tv_fapiao_fou:
              //  comfirmOrderPresenter.fou();
              //  closePopupWindow();
                break;
            case R.id.bar_back:
                backActivity();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (data != null) {
                String name = data.getStringExtra("name");
                String number = data.getStringExtra("number");
                String address = data.getStringExtra("address");
                comfirmOrderPresenter.setName(name);
                comfirmOrderPresenter.setNumber(number);
                comfirmOrderPresenter.setAddress(address);
                if (tv_order_shouhuoren2 != null) {
                    tv_order_shouhuoren2.setText(name);
                }
                if (tv_order_address2 != null) {
                    tv_order_address2.setText(address);

                }
                if (tv_order_number != null) {
                    tv_order_number.setText(number);
                }
                if (relative_order_addaddress != null) {
                    relative_order_addaddress.setVisibility(View.INVISIBLE);
                }
                if (relative_order_address != null) {
                    relative_order_address.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpger_order;
    }

    /* @Override
     public RecyclerView getRecycleView() {
         return rv_order;
     }*/
    @Override
    public RelativeLayout getRelaviteOrder() {
        return relative_order;
    }

    /*
      @Override
      public TextView getTextViewHeji() {
          return tv_order_heji_money;
      }



      @Override
      public ScrollView getScrollView() {
          return scrollView_order;
      }

      @Override
      public TextView getTextViewAmount() {
          return tv_order_amnout;
      }

      @Override
      public TextView getTextViewPeisong() {
          return tv_order_peisong;
      }

      @Override
      public TextView getTextViewFapiao() {
          return tv_order_fapiao;
      }

      @Override
      public TextView getTextviewGold() {
          return tv_order_goldcount;
      }

      @Override
      public EditText getEditView() {
          return et_goldcount;
      }
  */
    @Override
    public TextView getTextViewPay() {
        return tv_order_money;
    }

    @Override
    public TextView getTextViewShouHuoren() {
        return tv_order_shouhuoren2;
    }

    @Override
    public TextView getTextViewShouHuoNumber() {
        return tv_order_number;
    }

    @Override
    public TextView getTextViewShouHuoAddress() {
        return tv_order_address2;
    }

    @Override
    public RecyclerView getRecycleView2() {
        return recyclerView;
    }

    @Override
    public void setUpPopup(View view, View view2, int i, int p) {
        setUpPopupWindow(view, view2, i, p);
    }

    @Override
    public void closePopup() {
        closePopupWindow();
    }

    @Override
    public ImageView getIvPay() {
        return iv_order_pay;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        UtilMethod.backgroundAlpha(1.0f, this);
    }
}
