package com.betterda.xsnano.test;

import android.test.InstrumentationTestCase;

import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/6/19.
 */
public class EexampleTest extends InstrumentationTestCase {

    public void test() throws Exception {

        RequestParams params = new RequestParams(Constants.URL_COMMENT_GET);
        params.addBodyParameter("page", "1");
        params.addBodyParameter("rows", "10");
        params.addBodyParameter("productId", "402881f055437ba70155437cd9450001");
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println("s2:" + s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                System.out.println("s2错误:" + throwable);

            }
        });
    }

    public void comment_get() {
        RequestParams params = new RequestParams(Constants.URL_COMMENT_GET);
        params.addBodyParameter("page", "0");
        params.addBodyParameter("rows", "10");
        params.addBodyParameter("productId", "402881f055437ba70155437cd9450001");
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println("s2:" + s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                System.out.println("s2错误:" + throwable);

            }
        });
    }



    public void bus_get() {
        RequestParams params = new RequestParams(Constants.URL_BUS_GET);
        params.addBodyParameter("account","0");
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println("s2:" + s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                System.out.println("s2错误:" + throwable);

            }
        });
    }
    public void bus_add() {
        RequestParams params = new RequestParams(Constants.URL_BUS_GET);
        params.addBodyParameter("account","0");
        params.addBodyParameter("productId","0");
        params.addBodyParameter("count","0");

        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println("s2:" + s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                System.out.println("s2错误:" + throwable);

            }
        });
    }
    public void bus_update() {
        RequestParams params = new RequestParams(Constants.URL_BUS_GET);
        params.addBodyParameter("account","0");

        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println("s2:" + s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                System.out.println("s2错误:" + throwable);

            }
        });
    }
    public void bus_delete() {
        RequestParams params = new RequestParams(Constants.URL_BUS_GET);
        params.addBodyParameter("account","0");

        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println("s2:" + s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                System.out.println("s2错误:" + throwable);

            }
        });
    }


}
