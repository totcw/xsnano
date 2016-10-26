package com.betterda.xsnano.Buy.presenter;

import android.view.ViewTreeObserver;

import com.betterda.xsnano.Buy.view.IBuyView;


/**
 * Created by Administrator on 2016/4/26.
 */
public class IBuyPresenterImpl implements IBuyPresenter {
    private IBuyView iBuyView;

    public IBuyPresenterImpl(IBuyView iBuyView) {
        this.iBuyView = iBuyView;
    }

    @Override
    public void show() {
        //当布局的状态或者控件的可见性发生改变回调的接口
        iBuyView.getLinearLayout().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                iBuyView.scroll();

            }
        });
    }

    @Override
    public void scroll(int scrollY) {
        int mBuyLayout2ParentTop = Math.max(scrollY, iBuyView.getBuyLayout().getTop());
        System.out.println("top:"+iBuyView.getBuyLayout().getTop());
        System.out.println("scrollY:"+scrollY);
        //只要滑动的时候就不断的重新画顶部的布局的位置,当scrollY小于gettop时,就2个一直重合,当scrollY大于top就一直让它卡在顶部
        iBuyView.getTopBuyLayout().layout(0, mBuyLayout2ParentTop, iBuyView.getTopBuyLayout().getWidth(),
                mBuyLayout2ParentTop + iBuyView.getTopBuyLayout().getHeight());
    }
}
