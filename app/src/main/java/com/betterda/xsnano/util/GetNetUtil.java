package com.betterda.xsnano.util;

import android.text.TextUtils;
import android.view.View;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 封装网络请求
 * Created by Administrator on 2016/5/12.
 */
public class GetNetUtil {
    public static String POST = "post";
    public static String GET = "get";

    public static void getData(String httpmethod, RequestParams params, final GetDataCallBack callback) {
        if (!TextUtils.isEmpty(httpmethod)) {
            if ("post".equalsIgnoreCase(httpmethod)) {
                x.http().post(params, new Callback.CacheCallback<String>() {
                    @Override
                    public boolean onCache(String s) {
                        return false;
                    }

                    @Override
                    public void onSuccess(String s) {
                        callback.onSuccess(s);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        callback.onError(throwable,b);
                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            } else if ("get".equalsIgnoreCase(httpmethod)) {
                x.http().get(params, new Callback.CacheCallback<String>() {
                    @Override
                    public boolean onCache(String s) {
                        return false;
                    }

                    @Override
                    public void onSuccess(String s) {
                        callback.onSuccess(s);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        callback.onError(throwable,b);
                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }
    }


   public interface GetDataCallBack {
        void onSuccess(String s);

        void onError(Throwable throwable, boolean b);
    }

}
