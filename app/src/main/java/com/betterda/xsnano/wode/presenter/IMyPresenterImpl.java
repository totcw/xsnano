package com.betterda.xsnano.wode.presenter;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.betterda.xsnano.R;
import com.betterda.xsnano.address.AddressActivity;
import com.betterda.xsnano.bus.BusActivity;
import com.betterda.xsnano.information.InformationActivity;
import com.betterda.xsnano.login.LoginActivity;
import com.betterda.xsnano.orderall.AfterSaleActivity;
import com.betterda.xsnano.orderall.OrderActivity;
import com.betterda.xsnano.setting.SettingActivity;
import com.betterda.xsnano.shop.ChangeRecordActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.wallet.WalletActivity;
import com.betterda.xsnano.wode.view.IMyView;
import com.betterda.xsnano.youhui.YouHuiActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;

/**
 * Created by Administrator on 2016/5/26.
 */
public class IMyPresenterImpl implements IMyPresenter {
    private IMyView iMyView;

    public IMyPresenterImpl(IMyView iMyView) {
        this.iMyView = iMyView;
    }

    @Override
    public void start() {
        //如果登录就从本地获取头像和帐号信息
        if (UtilMethod.isLogin(iMyView.getmActivity())) {
            String number = CacheUtils.getString(iMyView.getmActivity(), "number", "");
            if (!TextUtils.isEmpty(number)) {
                if (iMyView.getTextViewNumber() != null) {
                    //显示用户的昵称
                    String nickname = CacheUtils.getString(iMyView.getmActivity(), number + "name", "昵称");
                    iMyView.getTextViewNumber().setText(nickname);
                }
                String touxiang = CacheUtils.getString(iMyView.getmActivity(), number + "touxiang", "");

                if (!TextUtils.isEmpty(touxiang)) {
                    if (iMyView.getSimpleDraweeView() != null) {
                        ImagePipeline imagePipeline = Fresco.getImagePipeline();
                        //删除fresco缓存中的一条uri,这样图片才会更新
                        imagePipeline.evictFromMemoryCache(Uri.parse(touxiang));
                        imagePipeline.evictFromDiskCache(Uri.parse(touxiang));
                        iMyView.getSimpleDraweeView().setImageURI(Uri.parse(touxiang));
                    }

                }
            }


        } else {
            //没有登录就设置为默认的
            if (iMyView.getTextViewNumber() != null) {
                iMyView.getTextViewNumber().setText("用户登录");
            }
            if (iMyView.getSimpleDraweeView() != null) {
                iMyView.getSimpleDraweeView().setImageURI(Uri.parse("res://com.betterda.xsnano/" + R.mipmap.viewpager_zwt));
            }
        }
    }

    @Override
    public void setting() {
        UtilMethod.isLogin(iMyView.getmActivity(), SettingActivity.class);
    }

    @Override
    public void tuikuan() {
        UtilMethod.isLogin(iMyView.getmActivity(), AfterSaleActivity.class);
    }

    @Override
    public void touxiang() {
            UtilMethod.isLogin(iMyView.getmActivity(),InformationActivity.class);
    }

    @Override
    public void jiayouka() {
        UtilMethod.isLogin(iMyView.getmActivity(),ChangeRecordActivity.class);
    }

    @Override
    public void bus() {
        UtilMethod.isLogin(iMyView.getmActivity(),BusActivity.class);
    }

    @Override
    public void address() {
        boolean islogin = UtilMethod.isLogin(iMyView.getmActivity());
        if (islogin) {

            UtilMethod.startIntentParams(iMyView.getmActivity(), AddressActivity.class,"wode","wode");
        } else {
            UtilMethod.startIntent(iMyView.getmActivity(), LoginActivity.class);
        }

    }

    @Override
    public void all() {
        if (UtilMethod.isLogin(iMyView.getmActivity())) {
            Intent intent = new Intent(iMyView.getmActivity(), OrderActivity.class);
            intent.putExtra("item", 0);
            iMyView.getmActivity().startActivity(intent);
        }else {
            UtilMethod.startIntent(iMyView.getmActivity(), LoginActivity.class);
        }

    }

    @Override
    public void kefu() {

    }

    @Override
    public void fankui() {

    }

    @Override
    public void youhui() {
        UtilMethod.isLogin(iMyView.getmActivity(), YouHuiActivity.class);
    }

    @Override
    public void car() {
        UtilMethod.isLogin(iMyView.getmActivity(), WalletActivity.class);
    }

    @Override
    public void pay() {

        if (UtilMethod.isLogin(iMyView.getmActivity())) {
            Intent intent = new Intent(iMyView.getmActivity(), OrderActivity.class);
            intent.putExtra("item", 1);
            iMyView.getmActivity().startActivity(intent);
        }else {
            UtilMethod.startIntent(iMyView.getmActivity(), LoginActivity.class);
        }
    }

    @Override
    public void send() {

        if (UtilMethod.isLogin(iMyView.getmActivity())) {
            Intent intent = new Intent(iMyView.getmActivity(), OrderActivity.class);
            intent.putExtra("item", 2);
            iMyView.getmActivity().startActivity(intent);
        }else {
            UtilMethod.startIntent(iMyView.getmActivity(), LoginActivity.class);
        }
    }

    @Override
    public void bring() {
        if (UtilMethod.isLogin(iMyView.getmActivity())) {
            Intent intent = new Intent(iMyView.getmActivity(), OrderActivity.class);
            intent.putExtra("item", 3);
            iMyView.getmActivity().startActivity(intent);
        }else {
            UtilMethod.startIntent(iMyView.getmActivity(), LoginActivity.class);
        }
    }

    @Override
    public void take() {
        if (UtilMethod.isLogin(iMyView.getmActivity())) {
            Intent intent = new Intent(iMyView.getmActivity(), OrderActivity.class);
            intent.putExtra("item", 4);
            iMyView.getmActivity().startActivity(intent);
        }else {
            UtilMethod.startIntent(iMyView.getmActivity(), LoginActivity.class);
        }
    }

    @Override
    public void comment() {
        if (UtilMethod.isLogin(iMyView.getmActivity())) {
            Intent intent = new Intent(iMyView.getmActivity(), OrderActivity.class);
            intent.putExtra("item", 5);
            iMyView.getmActivity().startActivity(intent);
        }else {
            UtilMethod.startIntent(iMyView.getmActivity(), LoginActivity.class);
        }
    }
}
