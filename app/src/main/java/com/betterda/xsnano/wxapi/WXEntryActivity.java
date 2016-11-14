package com.betterda.xsnano.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.betterda.xsnano.R;
import com.betterda.xsnano.information.InformationActivity;
import com.betterda.xsnano.information.NameActivity;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.javabean.Login;
import com.betterda.xsnano.javabean.WxInfomation;
import com.betterda.xsnano.javabean.WxLogin;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.LoginUitls;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.ShapeLoadingDialog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 微信登录,分享的回调activity
 * Created by Administrator on 2016/4/28.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private String code;
    private ShapeLoadingDialog dialog;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_wxentry);
        api = WXAPIFactory.createWXAPI(getApplicationContext(), Constants.WeiXin.APP_ID, true);
        api.handleIntent(getIntent(), this);//调用这行代码,授权完后才来这个页面
    }

    /**
     * 当微信发送请求到你的应用，将通过IWXAPIEventHandler接口的onReq方法进行回调
     *
     * @param baseReq
     */
    @Override
    public void onReq(BaseReq baseReq) {


    }

    /**
     * 发送到微信请求的响应结果将回调到onResp方法
     *
     * @param baseResp
     */
    @Override
    public void onResp(BaseResp baseResp) {

        try {
            SendAuth.Resp resp = (SendAuth.Resp) baseResp;
            if (resp != null) {
                code = resp.code;
                int err = resp.errCode;
                if (err == 0 && code != null) {
                    //授权成功,获取token
                    getData2();

                } else {
                    UtilMethod.Toast(getApplicationContext(), "微信授权失败");
                    finish();
                }
            } else {

                finish();
            }

        } catch (Exception e) {

            finish();
        }


    }

    /**
     * 通过code获取openid和token
     */
    public void getData2() {

        if (dialog == null) {
            dialog = UtilMethod.createDialog(this, "正在登录...");
        }
        if (!this.isFinishing()) {
            dialog.show();
        }

        RequestParams params = new RequestParams("https://api.weixin.qq.com/sns/oauth2/access_token");
        params.addBodyParameter("appid", Constants.WeiXin.APP_ID);
        params.addBodyParameter("secret", Constants.WeiXin.secret);
        params.addBodyParameter("code", code);
        params.addBodyParameter("grant_type", "authorization_code");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                if (!TextUtils.isEmpty(s)) {
                    WxLogin object = GsonParse.getObject(s, WxLogin.class);
                    if (object != null) {
                       LoginUitls.threeLogin(WXEntryActivity.this, object.getOpenid(), object.getAccess_token(), new LoginUitls.LoginUtilsListener() {
                           @Override
                           public void onError() {
                               dismissAndClose(dialog, WXEntryActivity.this, "登录失败");
                           }

                           @Override
                           public void getInformation(String openid,String token,String account) {
                               getInformation2(openid, token, account);
                           }

                           @Override
                           public void setLoginState(String nickname,String url,String account) {
                                 //设置登录状态
                                LoginUitls.saveLoginState(WXEntryActivity.this,nickname,url,account,false);
                                UtilMethod.dissmissDialog(WXEntryActivity.this, dialog);
                                WXEntryActivity.this.finish();
                                //发送广播
                                sendBrodcastToLogin();
                           }
                       });
                    } else {
                        dismissAndClose(dialog, WXEntryActivity.this, "微信授权失败");
                    }
                } else {
                    dismissAndClose(dialog, WXEntryActivity.this, "微信授权失败");
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                dismissAndClose(dialog, getApplicationContext(), "微信授权失败");

            }

            @Override
            public void onCancelled(Callback.CancelledException e) {
                dismissAndClose(dialog, getApplicationContext(), "微信授权取消");
            }

            @Override
            public void onFinished() {

            }
        });
    }



    /**
     * 从微信获取头像和昵称
     */
    private void getInformation2(String opendid, String token, final String number) {
        RequestParams params = new RequestParams("https://api.weixin.qq.com/sns/userinfo");
        params.addBodyParameter("openId", opendid);
        params.addBodyParameter("access_token", token);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                if (!TextUtils.isEmpty(s)) {
                    WxInfomation object = GsonParse.getObject(s, WxInfomation.class);
                    if (object != null) {
                        String nickname = object.getNickname();
                        String headimgurl = object.getHeadimgurl();
                        //设置登录状态
                        LoginUitls.saveLoginState(WXEntryActivity.this,nickname, headimgurl, number,true);
                        UtilMethod.dissmissDialog(WXEntryActivity.this, dialog);
                        WXEntryActivity.this.finish();
                        sendBrodcastToLogin();

                        return;
                    }
                }

                dismissAndClose(dialog, WXEntryActivity.this, "登录失败");

            }


            @Override
            public void onError(Throwable throwable, boolean b) {
                dismissAndClose(dialog, WXEntryActivity.this, "登录失败");
            }
        });
    }

    /**
     * 发送广播到登录界面
     */
    private void sendBrodcastToLogin() {
        Intent intent = new Intent();
        intent.setAction("com.betterda.xsnano.wxlogin");
        sendBroadcast(intent);
    }




    /**
     * 关闭对话框和activity
     *
     * @param dialog
     * @param applicationContext
     * @param message
     */
    private void dismissAndClose(ShapeLoadingDialog dialog, Context applicationContext, String message) {

        UtilMethod.dissmissDialog(WXEntryActivity.this, dialog);
        UtilMethod.Toast(applicationContext, message);
        if (!WXEntryActivity.this.isFinishing()) {
            WXEntryActivity.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (api != null) {
            api.unregisterApp();
            api = null;
        }
    }
}
