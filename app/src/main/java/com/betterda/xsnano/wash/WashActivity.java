package com.betterda.xsnano.wash;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.tianjiaji.view.TwoToolBarView;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.MainFourView;
import com.betterda.xsnano.view.NormalTopBar;
import com.betterda.xsnano.wash.presenter.IWashPresenter;
import com.betterda.xsnano.wash.presenter.IWashPresenterImpl;
import com.betterda.xsnano.wash.view.IWashView;

/**
 * 洗车界面
 * Created by Administrator on 2016/5/24.
 */
public class WashActivity extends BaseActivity implements IWashView, View.OnClickListener {
    private NormalTopBar topBar;
    private TwoToolBarView twotoolWash;
    private LoadingPager loadpager_xiche;
    private RecyclerView rv_xiche;
    private IWashPresenter iWashPresenter;
    private LinearLayout linear_xiche;
    private RecyclerView rv_tianjiaji_sort;
    private TextView tv_fenlei_kuaixiudian, tv_fenlei_mr, tv_fenlei_ssd;
    private RelativeLayout relative_fenlei_reset, relative_fenlei_comfirm;
    private RecyclerView rv_tianjiaji_shaixuan;
    private int pressNum = -1;

    @Override
    public void initView() {
        setContentView(R.layout.activity_wash);
        topBar = (NormalTopBar) findViewById(R.id.topbar_wash);
        twotoolWash = (TwoToolBarView) findViewById(R.id.twotool_wash);
        loadpager_xiche = (LoadingPager) findViewById(R.id.loadpager_xiche);
        rv_xiche = (RecyclerView) findViewById(R.id.rv_xiche);
        linear_xiche = (LinearLayout) findViewById(R.id.linear_xiche);


    }

    @Override
    public void initListener() {
        topBar.setOnBackListener(this);
        twotoolWash.setOnFirstClick(this);
        twotoolWash.setOnSecondClick(this);
        topBar.setOnClickListener(this);

    }

    @Override
    public void init() {


        loadpager_xiche.setLoadVisable();
        iWashPresenter = new IWashPresenterImpl(this);
        iWashPresenter.start();

    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_xiche;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                if (close()) {

                } else {
                    backActivity();

                }
                break;
            case R.id.mfiv_twotool_fist:
                if (pressNum == 0) {
                    if (close()) {
                        return;
                    }
                } else {
                    close();
                }
                pressNum = 0;
                twotoolWash.setFirstSelect(!twotoolWash.isFirstSelected());
                View pp_tianjiaji_sort = View.inflate(this, R.layout.pp_tianjiaji_sort, null);
                pp_tianjiaji_sort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closePopupWindow();
                    }
                });
                rv_tianjiaji_sort = (RecyclerView) pp_tianjiaji_sort.findViewById(R.id.rv_tianjiaji_sort);
                setUpPopupWindow(pp_tianjiaji_sort, twotoolWash, 1, UtilMethod.getHeight(this));
                iWashPresenter.clickFirst();

                break;
            case R.id.mfiv_twotool_second:
                if (pressNum == 1) {
                    if (close()) {
                        return;
                    }
                } else {
                    close();
                }
                pressNum = 1;
                twotoolWash.setSecondSelect(!twotoolWash.isSecondSelected());
                View sv_wash = View.inflate(this, R.layout.pp_tianjiaji_sort, null);
                sv_wash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closePopupWindow();
                    }
                });

                rv_tianjiaji_shaixuan = (RecyclerView) sv_wash.findViewById(R.id.rv_tianjiaji_sort);

                setUpPopupWindow(sv_wash, twotoolWash, 2, UtilMethod.getHeight(this));
                iWashPresenter.clickSecond();
                break;
            case R.id.topbar_wash:
               closePopupWindow();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closePopupWindow();
        iWashPresenter.ondestroy();
    }


    @Override
    public void dismiss() {
        super.dismiss();
        twotoolWash.setFirstSelect(false);
        twotoolWash.setSecondSelect(false);
    }

    @Override
    public RecyclerView getRecycleView() {
        return rv_xiche;
    }

    @Override
    public LinearLayout getLinearLayout() {
        return linear_xiche;
    }

    @Override
    public NormalTopBar getNorTopBar() {
        return topBar;
    }

    @Override
    public RecyclerView getRecyclyView() {
        return rv_tianjiaji_sort;
    }

    @Override
    public RecyclerView getRecyclyView2() {
        return rv_tianjiaji_shaixuan;
    }

    @Override
    public TwoToolBarView getTwoTool() {
        return twotoolWash;
    }

    @Override
    public TextView getTextViewK() {
        return tv_fenlei_kuaixiudian;
    }

    @Override
    public TextView getTextViewS() {
        return tv_fenlei_ssd;
    }

    @Override
    public TextView getTextViewW() {
        return tv_fenlei_mr;
    }

    @Override
    public void onBackPressed() {


        if (getPopupWindow() != null && getPopupWindow().isShowing()) {
            getPopupWindow().dismiss();

        } else {

            super.onBackPressed();
        }
    }

    /**
     * 用来判断popupwindow是否关闭
     *
     * @return
     */
    public boolean close() {
        if (getPopupWindow() != null && getPopupWindow().isShowing()) {
            closePopupWindow();
            return true;
        }
        return false;
    }

}
