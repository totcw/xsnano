package com.betterda.xsnano.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.javabean.Login;
import com.betterda.xsnano.wxapi.WXEntryActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * 处理登录的工具类
 * Created by Administrator on 2016/11/14.
 */

public class LoginUitls {


    public interface LoginUtilsListener {
        void onError(); //错误

        void getInformation(String openid,String token,String account); //从第三方获取信息

        void setLoginState(String nickname,String url,String account);//设置登录的状态
    }


    /**
     * 三方登录
     */
    public  static void threeLogin(final Context context, final String opendid, final String token, final LoginUtilsListener listener) {


        RequestParams params = new RequestParams(Constants.URL_OAUTH_LOGIN);
        params.addBodyParameter("openId", opendid);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                parser(context, s, opendid, token, listener);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                //dismissAndClose(dialog, WXEntryActivity.this, "登录失败");
                listener.onError();
            }
        });

    }


    /**
     * 解析登录成功的数据
     *
     * @param s
     */
    public static void parser(Context context, String s, String openid, String token, LoginUtilsListener listener) {

        Login login = GsonParse.getObject(UtilMethod.getString(s), Login.class);
        if (login != null) {
            if ("true".equals(login.isResult())) {
                Login.UserInfoBean userInfo = login.getUserInfo();
                if (null != userInfo) {
                    //缓存手机号
                    CacheUtils.putString(context, "number", userInfo.getAccount());
                    //缓存金币
                    CacheUtils.putInt(context, userInfo.getAccount() + "gold", userInfo.getGolden());
                    //缓存银币
                    CacheUtils.putInt(context, userInfo.getAccount() + "icon", userInfo.getIcon());
                    //如果头像或者昵称为空就表示是第一登录需要从微信获取数据
                    if (TextUtils.isEmpty(userInfo.getNickName()) || TextUtils.isEmpty(userInfo.getPhoto())) {

                        listener.getInformation(openid,token,userInfo.getAccount());
                    } else {

                        listener.setLoginState(userInfo.getNickName(), UtilMethod.url(userInfo.getPhoto()), userInfo.getAccount());
                    }


                } else {

                    listener.onError();
                }

            } else {
                listener.onError();
            }
        } else {
            listener.onError();
        }
    }


    /**
     * 设置登录状态
     *
     * @param context
     * @param nickname
     * @param headimgurl
     * @param number
     * @param isWx
     */
    public static void saveLoginState(Context context, String nickname, String headimgurl, String number, boolean isWx) {
        //设置为登录状态
        CacheUtils.putBoolean(context, "islogin", true);
        //缓存昵称
        CacheUtils.putString(context, number + "name", nickname);
        //缓存头像
        CacheUtils.putString(context, number + "touxiang", headimgurl);
        //上传头像和昵称到自己的服务器
        if (isWx) {
            uploadInformation(nickname, headimgurl, number, context);
        }
    }


    /**
     * 上传头像和昵称到自己的服务器
     *
     * @param nickname
     * @param headimgurl
     * @param number
     */
    public static void uploadInformation(final String nickname, String headimgurl, final String number, Context context) {
        //上传昵称
        uploadNickname(nickname, number);
        //下载头像
        downHeadImage(headimgurl, number, context);

    }

    /**
     * 下载头像
     *
     * @param headimgurl
     * @param number
     */
    private static void downHeadImage(String headimgurl, final String number, final Context context) {
        RequestParams params2 = new RequestParams(headimgurl);

        params2.setSaveFilePath(Constants.PHOTOPATH + Constants.PHOTONAME
                + ".png");
        x.http().get(params2, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                uploadHeadImage(number, context);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

            }
        });
    }

    /**
     * 上传头像
     *
     * @param number
     */
    private static void uploadHeadImage(String number, final Context context) {
        RequestParams params = new RequestParams(Constants.URL__UPLOAD_TOUXIANG);
        params.addBodyParameter("account", number);
        try {
            params.addBodyParameter("file", new File(Constants.PHOTOPATH + Constants.PHOTONAME
                    + ".png"));
        } catch (Exception e) {

        }

        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                if ("true".equals(s)) {
                    String number = CacheUtils.getString(context, "number", "");
                    if (!TextUtils.isEmpty(number)) {
                        CacheUtils.putString(context, number + "touxiang", "file://" + Constants.PHOTOPATH + Constants.PHOTONAME
                                + ".png");
                    }


                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }
        });
    }

    /**
     * 上传昵称
     *
     * @param nickname
     * @param number
     * @return
     */
    @NonNull
    private static void uploadNickname(String nickname, String number) {
        final RequestParams params = new RequestParams(Constants.URL_INFORMATION_UPDATE);
        params.addBodyParameter("account", number);
        params.addBodyParameter("nickName", nickname);

        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                String substring = UtilMethod.getString(s);
                GsonParse.parser(substring, new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {

                    }
                });


            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }
        });

    }
}
