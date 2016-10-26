package com.betterda.xsnano.pay.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.javabean.RespContent;
import com.betterda.xsnano.javabean.UpTn;
import com.betterda.xsnano.orderall.OrderActivity;
import com.betterda.xsnano.pay.view.IPayView;
import com.betterda.xsnano.util.Base64P;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.GsonTools;
import com.betterda.xsnano.util.MD5;
import com.betterda.xsnano.util.UUIDUtils;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.ShapeLoadingDialog;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/20.
 */
public class IPayPresenterImpl implements IPayPresenter {
    private IPayView iPayView;
    private float sum =10;//实付 民生支付的总金额


    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    private final String mMode = "00";
    private final String key = "Q8dfaa2cyn9JoOADVHtVTTuU17W1x0al";//民生商户密钥
    private final String merId = "805920100000041";  //商户号
    private String reqContent; //民生请求内容
    private String signature; //民生签名

    private ShapeLoadingDialog dialog; //进度对话框
    private String orderId;//订单id

    public IPayPresenterImpl(IPayView iPayView) {
        this.iPayView = iPayView;
    }

    @Override
    public void start() {
        Intent intent = iPayView.getmActivity().getIntent();
        //商品的价格
        float money = intent.getFloatExtra("money", 0);
        //商品的名称
        String name = intent.getStringExtra("name");
        //运费
        float money3 = intent.getFloatExtra("money3", 0);
        //订单id
        orderId = intent.getStringExtra("orderid");
        sum = money  + money3;
        iPayView.getTextViewMoney().setText("￥ "+UtilMethod.FloatFormat(money));
        iPayView.getTextViewMoney3().setText("￥ "+UtilMethod.FloatFormat(money3));
        iPayView.getTextViewMoney4().setText("￥ "+UtilMethod.FloatFormat(sum));

        if (TextUtils.isEmpty(name)) {
            iPayView.getRelativeName().setVisibility(View.GONE);
        } else {
            iPayView.getTextViewMoney2().setText(name);
        }





    }

    @Override
    public void wxpay() {
            hide();
        iPayView.getRelativeWxpay().setSelected(true);
    }

    @Override
    public void zfbpay() {
        hide();
        iPayView.getRelativeZfbpay().setSelected(true);
    }

    @Override
    public void wypay() {
        hide();
        iPayView.getRelativeWypay().setSelected(true);
    }

    @Override
    public void pay() {
        if (iPayView.getRelativeWypay().isSelected()) {

            if (dialog == null) {
                dialog = UtilMethod.createDialog(iPayView.getmActivity(), "正在提交...");
            }
            if (!iPayView.getmActivity().isFinishing()) {
                dialog.show();
            }
            payFromMs();
        } else {
            UtilMethod.Toast(iPayView.getmActivity(),"请选择支付方式");
        }

    }

    public void hide() {
        iPayView.getRelativeWxpay().setSelected(false);
        iPayView.getRelativeWypay().setSelected(false);
        iPayView.getRelativeZfbpay().setSelected(false);
    }


    /**
     * 民生支付
     */
    private void payFromMs() {
        //封装参数
        UpTn upTn = new UpTn();
        upTn.setBackUrl(Constants.MS_BACK_URL);
        upTn.setTxnAmt(UtilMethod.deletePoint(UtilMethod.FloatFormat(sum)));
        upTn.setMerId(merId);
        upTn.setOrderId(orderId);
        Date d = new Date();
        SimpleDateFormat stime = new SimpleDateFormat("yyyyMMddHHmmss");
        upTn.setTxnTime(stime.format(d));
        //将参数封装成json
        String s = GsonTools.getJsonString(upTn);
        //对封装的json做base64处理
        reqContent = Base64P.getBase64(s);
        //通过reqContent和key生成签名
        signature = MD5.MD5Encode(reqContent + key, "utf-8");
        getTn();

    }


    /**
     * 获取民生支付的流水号tn
     */
    private void getTn() {
        RequestParams params = new RequestParams(Constants.MS_ORDER_URL);
        params.addBodyParameter("signature", signature);
        params.addBodyParameter("reqContent", reqContent);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (!iPayView.getmActivity().isFinishing()){
                    dialog.dismiss();
                }
                //获取返回的respContent
                int index = result.indexOf("respContent=");
                String str = result.substring(index + "respContent=".length());
                try {
                    //对respContent出base64解析得到json的字符串
                    String str1 = Base64P.getFromBase64(URLDecoder.decode(str, "utf-8"));
                    RespContent respContent = GsonParse.getObject(str1, RespContent.class);
                    if (null != respContent) {
                        String tn = respContent.getTn();
                        String resCode = respContent.getRespCode();
                        String resMsg = respContent.getRespMsg();
                        if ("00".equals(resCode)) {//获取成功
                            //调用民生的支付控件
                            UPPayAssistEx.startPayByJAR(iPayView.getmActivity(), PayActivity.class, null, null, tn, mMode);

                        } else {
                            UtilMethod.Toast(iPayView.getmActivity(), resMsg);
                        }


                    } else {
                        UtilMethod.Toast(iPayView.getmActivity(),"获取流水号失败!");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!iPayView.getmActivity().isFinishing()){
                    dialog.dismiss();
                }
                UtilMethod.Toast(iPayView.getmActivity(),"获取流水号失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
