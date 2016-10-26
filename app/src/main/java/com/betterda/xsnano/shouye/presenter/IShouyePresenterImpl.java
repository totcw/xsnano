package com.betterda.xsnano.shouye.presenter;

import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.betterda.xsnano.R;
import com.betterda.xsnano.shouye.model.Store;
import com.betterda.xsnano.shouye.view.IShouyeView;
import com.betterda.xsnano.store.StoreActivity;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.List;

/**
 * 负责与首页进行交互
 * Created by Administrator on 2016/4/26.
 */
public class IShouyePresenterImpl implements IShouyePresenter  {
    private IShouyeView iShouyeView;





    public IShouyePresenterImpl(IShouyeView iShouyeView) {
        this.iShouyeView = iShouyeView;




    }



    @Override
    public void start() {

    }








}
