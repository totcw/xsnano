package com.betterda.xsnano.acitivity;

import com.betterda.xsnano.home.HomeActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.UtilMethod;

import org.xutils.http.RequestParams;

/**
 * 欢迎界面
 * Created by Administrator on 2016/5/25.
 */
public class WelcomeActivity extends BaseActivity {


    @Override
    public void init() {
        getData();
    }


    private void getData() {
        RequestParams params = new RequestParams(Constants.URL_CACHE);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                    CacheUtils.putString(getmActivity(),"cache",UtilMethod.getString(s));
                UtilMethod.startIntent(WelcomeActivity.this, HomeActivity.class);
                finish();

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                UtilMethod.startIntent(WelcomeActivity.this, HomeActivity.class);
                finish();
            }
        });
    }

}
