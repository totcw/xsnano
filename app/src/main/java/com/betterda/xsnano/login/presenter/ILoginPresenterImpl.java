package com.betterda.xsnano.login.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.betterda.xsnano.R;
import com.betterda.xsnano.findpwd.FindPwdActivity;
import com.betterda.xsnano.home.HomeActivity;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.javabean.Login;
import com.betterda.xsnano.javabean.QqLogin;
import com.betterda.xsnano.login.view.ILoginView;
import com.betterda.xsnano.register.RegisterActivity;
import com.betterda.xsnano.util.AccessTokenKeeper;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.ShapeLoadingDialog;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/5/25.
 */
public class ILoginPresenterImpl implements ILoginPresenter {
    private ILoginView iLoginView;
    private String number, pwd; //手机号,密码
    private ShapeLoadingDialog dialog;


    private IWXAPI api; //与微信通信的接口

    private Tencent mTencent;//QQ接口
    private IUiListener loginListener;//qq的回调接口
    /**
     * 微博需要的类
     */
    private AuthInfo mAuthInfo;

    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;

    /**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    private SsoHandler mSsoHandler;



    public ILoginPresenterImpl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    @Override
    public void start() {

    }

    private void createQQ() {
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(Constants.QQ.APP_ID, iLoginView.getmActivity().getApplicationContext());
        loginListener = new BaseUiListener();
    }


    private void createWeibo() {
        // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
        mAuthInfo = new AuthInfo(iLoginView.getmActivity(), Constants.WeiBo.APP_KEY, Constants.WeiBo.REDIRECT_URL, Constants.WeiBo.SCOPE);
        mSsoHandler = new SsoHandler(iLoginView.getmActivity(), mAuthInfo);

        // 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
        // 第一次启动本应用，AccessToken 不可用
     /*   mAccessToken = AccessTokenKeeper.readAccessToken(iLoginView.getmActivity());
        if (mAccessToken.isSessionValid()) {

        }*/
    }

    /**
     * 微信登录
     */
    @Override
    public void wxLogin() {
        //通过工厂获取实例
        api = WXAPIFactory.createWXAPI(iLoginView.getContext(), Constants.WeiXin.APP_ID, true);
        //将应用的appid注册到微信
        api.registerApp(Constants.WeiXin.APP_ID);
        if (api.isWXAppInstalled()) {
            // 启动授权页面,获取code
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "xsnano_test";
            api.sendReq(req);
        } else {
            UtilMethod.Toast(iLoginView.getContext(), "请先安装微信");
        }

    }

    /**
     * 普通登录
     */
    @Override
    public void login() {
        boolean isSelected = iLoginView.getBtnLogin().isSelected();

        if (isSelected) {
            if (dialog == null) {
                dialog = UtilMethod.createDialog(iLoginView.getContext(), "正在登录...");
            }
            if (!iLoginView.getmActivity().isFinishing()) {
                dialog.show();
            }

            RequestParams params = new RequestParams(Constants.URL_LOGIN);
            params.addBodyParameter("account", iLoginView.getEditLogin().getText().toString().trim());
            params.addBodyParameter("password", iLoginView.getEditLoginPwd().getText().toString().trim());
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
                    UtilMethod.Toast(iLoginView.getmActivity(), "登录失败");
                }
            });
        }

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
                    CacheUtils.putBoolean(iLoginView.getContext(), "islogin", true);
                    //缓存手机号
                    CacheUtils.putString(iLoginView.getContext(), "number", userInfo.getAccount());
                    //缓存昵称
                    CacheUtils.putString(iLoginView.getContext(), userInfo.getAccount() + "name", userInfo.getNickName());
                    //缓存头像
                    CacheUtils.putString(iLoginView.getContext(), userInfo.getAccount() + "touxiang", UtilMethod.url(userInfo.getPhoto()));
                    //缓存金币
                    CacheUtils.putInt(iLoginView.getContext(), userInfo.getAccount() + "gold", userInfo.getGolden());
                    //缓存银币
                    CacheUtils.putInt(iLoginView.getContext(), userInfo.getAccount() + "icon", userInfo.getIcon());

                    //跳到主页
                   // UtilMethod.startIntent(iLoginView.getContext(), HomeActivity.class);
                    iLoginView.getmActivity().finish();
                } else {
                    UtilMethod.Toast(iLoginView.getmActivity(), "登录失败");
                }

            } else {
                UtilMethod.Toast(iLoginView.getmActivity(), "登录失败");
            }
        }
    }

    @Override
    public void editPwd() {
        UtilMethod.startIntent(iLoginView.getContext(), FindPwdActivity.class);
    }

    @Override
    public void register() {
        UtilMethod.startIntent(iLoginView.getContext(), RegisterActivity.class);
    }

    @Override
    public void number(Editable s) {
        number = s.toString();
        isLogin();
    }


    @Override
    public void pwd(Editable s) {
        pwd = s.toString();
        isLogin();
    }

    @Override
    public void onDestroy() {
        if (null != dialog) {
            dialog = null;
        }
    }

    /**
     * 微博登录
     */
    @Override
    public void weiboLogin() {
        // 创建微博实例
        createWeibo();

        // SSO 授权, ALL IN ONE   如果手机安装了微博客户端则使用客户端授权,没有则进行网页授权
        mSsoHandler.authorize(new AuthListener());
    }

    @Override
    public void weiboCallBack(int requestCode, int resultCode, Intent data) {
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * QQ登录
     */
    @Override
    public void QQlogin() {

        //创建qq实例
        createQQ();
        //发起授权
        if (mTencent != null && !mTencent.isSessionValid()) {
            mTencent.login(iLoginView.getmActivity(), "", loginListener);
        }
    }

    /**
     * QQ回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void QqCallBack(int requestCode, int resultCode, Intent data) {
        if (mTencent != null) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
    }

    /**
     * 是否可以登录
     */
    private void isLogin() {
        if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(pwd)) {
            iLoginView.getBtnLogin().setSelected(true);
        } else {
            iLoginView.getBtnLogin().setSelected(false);
        }
    }


    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     * 该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {


        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);

            if (mAccessToken != null) {
                if (mAccessToken.isSessionValid()) {
                    // 保存 Token 到 SharedPreferences
                    AccessTokenKeeper.writeAccessToken(iLoginView.getmActivity(), mAccessToken);
                    //从这里获取用户输入的 uid信息
                    String uid = mAccessToken.getUid();
                    threeLogin(uid);

                } else {
                    // 以下几种情况，您会收到 Code：
                    // 1. 当您未在平台上注册的应用程序的包名与签名时；
                    // 2. 当您注册的应用程序包名与签名不正确时；
                    // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                    String code = values.getString("code");
                    String message = values.getString("授权失败");
                    UtilMethod.Toast(iLoginView.getmActivity(), "微博授权失败");

                }
            } else {
                UtilMethod.Toast(iLoginView.getmActivity(),"微博授权失败");
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {

        }

        @Override
        public void onCancel() {
            UtilMethod.Toast(iLoginView.getmActivity(),"微博授权取消");
        }
    }

    /**
     * QQ的回调接口
     */
    private class BaseUiListener implements IUiListener {


        @Override
        public void onComplete(Object o) {

            if (o != null) {
                String str = o.toString();
                if (!TextUtils.isEmpty(str)) {
                    QqLogin object = GsonParse.getObject(str, QqLogin.class);
                    String openid = object.getOpenid();
                    threeLogin(openid);
                }else
                    UtilMethod.Toast(iLoginView.getmActivity(),"QQ授权失败");
            } else {
                UtilMethod.Toast(iLoginView.getmActivity(),"QQ授权失败");
            }

        }

        @Override

        public void onError(UiError e) {
            UtilMethod.Toast(iLoginView.getmActivity(),"QQ授权失败");
        }

        @Override

        public void onCancel() {
            UtilMethod.Toast(iLoginView.getmActivity(),"QQ登录取消授权");
        }

    }

    /**
     * 三方登录
     */
    public void threeLogin(String opendid) {

        if (dialog == null) {
            dialog = UtilMethod.createDialog(iLoginView.getContext(), "正在登录...");
        }
        if (!iLoginView.getmActivity().isFinishing()) {
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
                UtilMethod.Toast(iLoginView.getmActivity(), "登录失败");
            }
        });

    }

}