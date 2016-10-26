package com.betterda.xsnano.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.betterda.xsnano.R;
import com.betterda.xsnano.javabean.Login;
import com.betterda.xsnano.javabean.WxLogin;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.ShapeLoadingDialog;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.WeiXin.APP_ID, true);
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
                    finish();
                } else {
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
     * 通过code获取token
     */
    public void getData2() {
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
                        threeLogin(object.getOpenid());
                    }
                }
                WXEntryActivity.this.finish();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {


            }

            @Override
            public void onCancelled(Callback.CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * 三方登录
     */
    public void threeLogin(String opendid) {

        if (dialog == null) {
            dialog = UtilMethod.createDialog(this, "正在登录...");
        }
        if (!this.isFinishing()) {
            dialog.show();
        }

        RequestParams params = new RequestParams(Constants.URL_OAUTH_LOGIN);
        params.addBodyParameter("openId",opendid);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                parser(s);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                UtilMethod.Toast(WXEntryActivity.this, "登录失败");
            }
        });

    }

    /**
     * 解析登录成功的数据
     * @param s
     */
    private void parser(String s) {
        Login login = GsonParse.getObject(UtilMethod.getString(s), Login.class);
        if (login != null) {
            if ("true".equals(login.isResult())) {
                Login.UserInfoBean userInfo = login.getUserInfo();
                if (null != userInfo) {
                    //设置为登录状态
                    CacheUtils.putBoolean(this, "islogin", true);
                    //缓存手机号
                    CacheUtils.putString(this, "number", userInfo.getAccount());
                    //缓存昵称
                    CacheUtils.putString(this, userInfo.getAccount() + "name", userInfo.getNickName());
                    //缓存头像
                    CacheUtils.putString(this, userInfo.getAccount() + "touxiang", UtilMethod.url(userInfo.getPhoto()));
                    //缓存金币
                    CacheUtils.putInt(this, userInfo.getAccount() + "gold", userInfo.getGolden());
                    //缓存银币
                    CacheUtils.putInt(this, userInfo.getAccount() + "icon", userInfo.getIcon());

                    //跳到主页
                    // UtilMethod.startIntent(this, HomeActivity.class);
                    this.finish();
                } else {
                    UtilMethod.Toast(this, "登录失败");
                }

            } else {
                UtilMethod.Toast(this, "登录失败");
            }
        }
    }


}
