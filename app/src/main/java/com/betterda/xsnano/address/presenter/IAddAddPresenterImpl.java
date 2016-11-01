package com.betterda.xsnano.address.presenter;

import android.text.TextUtils;

import com.betterda.xsnano.address.model.Address;
import com.betterda.xsnano.address.view.IAddAddView;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;

import org.xutils.http.RequestParams;

import kankan.wheel.widget.WheelDialog;

/**
 * Created by Administrator on 2016/5/17.
 */
public class IAddAddPresenterImpl implements IAddAddPresenter {
    private IAddAddView addAddView;
    private String name;
    private String number;
    private String address;
    private String address2;
    private StringBuilder stringBuilder;

    public IAddAddPresenterImpl(IAddAddView addAddView) {

        this.addAddView = addAddView;
    }

    @Override
    public void start() {

    }

    @Override
    public void save() {
        getString();
        getData(false);
    }

    @Override
    public void saveMoren() {
        //默认
        getString();
        getData(true);
    }

    @Override
    public void showProvince() {
        WheelDialog wheelDialog = new WheelDialog(addAddView.getmActivity());
        wheelDialog.setOnAddressCListener(new WheelDialog.OnAddressCListener() {
            @Override
            public void onClick(String s, String s1, String s2) {
                stringBuilder = new StringBuilder();
                if (!TextUtils.isEmpty(s)) {
                    stringBuilder.append(s);

                }
                if (!TextUtils.isEmpty(s1)) {
                    stringBuilder.append(s1);

                }
                if (!TextUtils.isEmpty(s2)) {
                    stringBuilder.append(s2);

                }

                address = stringBuilder.toString();
                addAddView.setText(address);
            }
        });
        wheelDialog.show();
    }

    private void getString() {
        name = addAddView.getEditViewName().getText().toString();
        number = addAddView.getEditViewNumber().getText().toString();

        address2 = addAddView.getEditViewAdress2().getText().toString();
    }


    public void getData(boolean isMoren) {
        if (TextUtils.isEmpty(name)) {
            UtilMethod.Toast(addAddView.getmActivity(), "收货人姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(number)) {
            UtilMethod.Toast(addAddView.getmActivity(), "手机号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            UtilMethod.Toast(addAddView.getmActivity(), "省市区不能为空");
            return;
        }
        if (TextUtils.isEmpty(address2)) {
            UtilMethod.Toast(addAddView.getmActivity(), "详细地址不能为空");
            return;
        }


        RequestParams params = new RequestParams(Constants.URL_ADDRESS_ADD);
        //账户
        String number = CacheUtils.getString(addAddView.getmActivity(), "number", "");
        params.addBodyParameter("account", number);
        //收货人姓名
        params.addBodyParameter("name", name);
        //收货人手机号
        params.addBodyParameter("mobilePhone", number);
        //收货人省市区
        params.addBodyParameter("pcadetail", address);
        //收货人详细地址
        params.addBodyParameter("adress", address2);
        //是否默认
        if (isMoren) {

            params.addBodyParameter("isDefault", "Y");
        } else {
            params.addBodyParameter("isDefault", "N");
        }

        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                GsonParse.parser(UtilMethod.getString(s), new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {
                        if ("true".equals(result)) {

                            addAddView.getmActivity().finish();
                        } else {
                            UtilMethod.Toast(addAddView.getmActivity(), "添加失败");
                        }
                    }
                });

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                UtilMethod.Toast(addAddView.getmActivity(), "添加失败");
            }
        });
    }

}
