package com.betterda.xsnano.address.presenter;

import android.content.Intent;

import com.betterda.xsnano.address.view.IEditAddressView;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/5/20.
 */
public class IEditAddressPresenterImpl implements  IEditAddressPresenter {
    private IEditAddressView iEditAddressView;
    private String id;
    public IEditAddressPresenterImpl(IEditAddressView iEditAddressView) {
        this.iEditAddressView = iEditAddressView;
    }

    @Override
    public void start() {
        Intent intent = iEditAddressView.getmActivity().getIntent();
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");
        String address = intent.getStringExtra("address");
        String address2 = intent.getStringExtra("address2");
        id = intent.getStringExtra("id");
        iEditAddressView.getEditViewName().setText(name);
        iEditAddressView.getEditViewNumber().setText(number);
        iEditAddressView.getEditViewAdress().setText(address);
        iEditAddressView.getEditViewAdress2().setText(address2);

    }


    public void getData(boolean isMoren) {
        RequestParams params = new RequestParams(Constants.URL_ADDRESS_UPDATE);
        //账户
        String number = CacheUtils.getString(iEditAddressView.getmActivity(), "number", "");
        params.addBodyParameter("account", number);
        //地址id
        params.addBodyParameter("id", id);
        //收货人姓名
        params.addBodyParameter("name", iEditAddressView.getEditViewName().getText().toString());
        //收货人手机号
        params.addBodyParameter("mobilePhone", iEditAddressView.getEditViewNumber().getText().toString());
        //收货人省市区
        params.addBodyParameter("pcadetail", iEditAddressView.getEditViewAdress().getText().toString());
        //收货人详细地址
        params.addBodyParameter("adress", iEditAddressView.getEditViewAdress2().getText().toString());
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
                            iEditAddressView.getmActivity().finish();
                        } else {
                            UtilMethod.Toast(iEditAddressView.getmActivity(),"修改失败");
                        }
                    }
                });

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                UtilMethod.Toast(iEditAddressView.getmActivity(), "修改失败");
            }
        });
    }

    @Override
    public void save() {
        getData(false);
    }

    @Override
    public void delete() {

        RequestParams params = new RequestParams(Constants.URL_ADDRESS_DELETE);
        //地址id
        params.addBodyParameter("id", id);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                GsonParse.parser(UtilMethod.getString(s), new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {
                        if ("true".equals(result)) {
                            iEditAddressView.getmActivity().finish();
                        } else {
                            UtilMethod.Toast(iEditAddressView.getmActivity(),"删除失败");
                        }
                    }
                });

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                UtilMethod.Toast(iEditAddressView.getmActivity(), "删除失败");
            }
        });

    }

    @Override
    public void saveMore() {
        getData(true);
    }
}
