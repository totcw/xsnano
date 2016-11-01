package com.betterda.xsnano.findpwd.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;

import com.betterda.xsnano.findpwd.view.IFindPwdView;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.login.LoginActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.CountDown;
import com.betterda.xsnano.view.ShapeLoadingDialog;

import org.xutils.http.RequestParams;

import java.io.File;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/5/26.
 */
public class IFindPwdPrensenterImpl implements IFindPwdPresenter {
    private IFindPwdView iFindPwdView;
    private String number, pwd, yzm;
    private ShapeLoadingDialog dialog;
    private EventHandler eh;
    private boolean isCommit;//是否提交验证码
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    commit();
                    break;
                case 1:
                    UtilMethod.Toast(iFindPwdView.getmActivity(), "验证码错误");
                    break;
            }
        }
    };

    public IFindPwdPrensenterImpl(IFindPwdView iFindPwdView) {
        this.iFindPwdView = iFindPwdView;
    }

    @Override
    public void start() {
        //注册短信验证码监听
        eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Message message = Message.obtain();
                        message.what = 0;
                        handler.sendMessage(message);
                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {

                        isCommit = true;
                        //获取验证码成功
                        //获取到了验证码，此时要提交验证码
                        // SMSSDK.submitVerificationCode();
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表


                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    if (isCommit) {
                        //提交过验证码有错误的话就是验证码不对
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    }

                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }


    private void commit() {
        if (iFindPwdView.getBtnRegister().isSelected()) {
            if (TextUtils.isEmpty(pwd)) {
                return;
            }

            if (dialog == null) {

                dialog = UtilMethod.createDialog(iFindPwdView.getContext(), "正在修改...");
            }
            if (!iFindPwdView.getmActivity().isFinishing()) {
                dialog.show();
            }


            RequestParams params = new RequestParams(Constants.URL_INFORMATION_UPDATE);
            params.addBodyParameter("account", number);
            params.addBodyParameter("password", pwd);
            GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
                @Override
                public void onSuccess(String s) {

                    String subString = UtilMethod.getString(s);
                    GsonParse.parser(subString, new ParserGsonInterface() {
                        @Override
                        public void onSuccess(String result, String resultMsg) {

                            if ("true".equals(result)) {

                                //修改密码就将登录状态设置为false并且跳动登录界面
                                //  CacheUtils.putString(iFindPwdView.getmActivity(),"number","");
                                //  CacheUtils.putBoolean(iFindPwdView.getContext(), "islogin", false);
                                //UtilMethod.startIntent(iFindPwdView.getContext(), LoginActivity.class);
                                iFindPwdView.getmActivity().finish();
                            }
                            UtilMethod.Toast(iFindPwdView.getmActivity(), resultMsg);

                        }
                    });
                    if (!iFindPwdView.getmActivity().isFinishing()) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }


                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    if (!iFindPwdView.getmActivity().isFinishing()) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                    UtilMethod.Toast(iFindPwdView.getmActivity(), "修改失败");
                }
            });
        }
    }

    @Override
    public void findpwd() {

        if (iFindPwdView.getBtnRegister().isSelected()) {
            //提交验证码
            SMSSDK.submitVerificationCode("86", number, yzm);
        }

    }

    @Override
    public void number(Editable s) {
        number = s.toString();
        isRigister();
        isGetYzm();
    }

    @Override
    public void yzm(Editable s) {
        yzm = s.toString();
        isRigister();
    }

    @Override
    public void pwd(Editable s) {
        pwd = s.toString();
        isRigister();
    }


    @Override
    public void countDown() {
        //处理验证码
        if (iFindPwdView.getCountDown().isSelected()) {
            //用正则判断是否是手机号码
            if (!TextUtils.isEmpty(number)) {
                boolean ismobile = number.matches("^1(3[0-9]|4[57]|5[0-9]|8[0-9]|7[0678])\\d{8}$");
                if (ismobile) {
                    SMSSDK.getVerificationCode("86", number);
                    //显示倒计时
                    iFindPwdView.getCountDown().showCountDown();
                } else {
                    UtilMethod.Toast(iFindPwdView.getContext(), "请输入正确的手机号码");
                }
            }

        }


    }

    @Override
    public void setSelect(CountDown countDown) {
        //倒计时完了要判断手机号是否为空
        if (TextUtils.isEmpty(number)) {
            countDown.setSelected(false);
        } else {
            countDown.setSelected(true);
        }
    }

    @Override
    public void ondestroy() {
        if (eh != null) {
            SMSSDK.unregisterEventHandler(eh);
            eh = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

    }

    private void isRigister() {
        if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(pwd) &&
                !TextUtils.isEmpty(yzm)) {
            iFindPwdView.getBtnRegister().setSelected(true);
        } else {
            iFindPwdView.getBtnRegister().setSelected(false);
        }
    }

    /**
     * 是否可以获取验证码
     */
    private void isGetYzm() {
        //如果是不可以点击的状态,就一直设置为不选中
        if (iFindPwdView.getCountDown().isClickable()) {
            if (!TextUtils.isEmpty(number)) {
                iFindPwdView.getCountDown().setSelected(true);
            } else {
                iFindPwdView.getCountDown().setSelected(false);
            }
        } else {
            iFindPwdView.getCountDown().setSelected(false);
        }


    }
}
