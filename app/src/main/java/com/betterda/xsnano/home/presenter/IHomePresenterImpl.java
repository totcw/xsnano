package com.betterda.xsnano.home.presenter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.PopupWindow;

import com.betterda.xsnano.shouye.ShouYeFragment2;
import com.betterda.xsnano.wode.MyFragment;
import com.betterda.xsnano.shop.ShangChengFragment;


import java.util.ArrayList;
import java.util.List;

/**
 * 负责于home的view交互和model的交互
 * Created by Administrator on 2016/4/26.
 */
public class IHomePresenterImpl implements IHomePresenter {
    private List<Fragment> fragmentList;
    private Fragment shouyeFragment;
    private Fragment shangchengFragment;
    private Fragment myFragment;

    public IHomePresenterImpl() {

         crateFragment();
    }

    @Override
    public void crateFragment() {
        if (null == fragmentList) {
            fragmentList = new ArrayList<>();
        }
        shouyeFragment = new ShouYeFragment2();
        shangchengFragment = new ShangChengFragment();
        myFragment = new MyFragment();
        fragmentList.add(shouyeFragment);
        fragmentList.add(shangchengFragment);
        fragmentList.add(myFragment);

    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    @Override
    public boolean onBackPressed() {
        if (shouyeFragment != null) {
            PopupWindow popupWindow = ((ShouYeFragment2) shouyeFragment).getPopupWindow();
            if (popupWindow != null&&popupWindow.isShowing()) {
                popupWindow.dismiss();
                return true;
            }
        }
        return false;
    }

}
