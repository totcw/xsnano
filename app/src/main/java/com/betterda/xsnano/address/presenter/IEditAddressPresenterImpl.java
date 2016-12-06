package com.betterda.xsnano.address.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.betterda.xsnano.address.view.IEditAddressView;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;

import org.xutils.http.RequestParams;

import kankan.wheel.widget.WheelDialog;

/**
 * Created by Administrator on 2016/5/20.
 */
public class IEditAddressPresenterImpl implements  IEditAddressPresenter {
    private IEditAddressView iEditAddressView;
    private String id;
    private String name;
    private String number;
    private String address;
    private String address2;
    private StringBuilder stringBuilder;

    public IEditAddressPresenterImpl(IEditAddressView iEditAddressView) {
        this.iEditAddressView = iEditAddressView;
    }

    @Override
    public void start() {
        Intent intent = iEditAddressView.getmActivity().getIntent();
        name = intent.getStringExtra("name");
        number = intent.getStringExtra("number");
        address = intent.getStringExtra("address");
        address2 = intent.getStringExtra("address2");
        id = intent.getStringExtra("id");
        iEditAddressView.getEditViewName().setText(name);
        iEditAddressView.getEditViewNumber().setText(number);
        iEditAddressView.setText(address);
        iEditAddressView.getEditViewAdress2().setText(address2);


    }

    private void getString() {
        name = iEditAddressView.getEditViewName().getText().toString();
        number = iEditAddressView.getEditViewNumber().getText().toString();
        address2 = iEditAddressView.getEditViewAdress2().getText().toString();
    }


    public void getData(boolean isMoren) {
        getString();
        if (TextUtils.isEmpty(name)) {
            UtilMethod.Toast(iEditAddressView.getmActivity(), "收货人姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(number)) {
            UtilMethod.Toast(iEditAddressView.getmActivity(), "手机号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            UtilMethod.Toast(iEditAddressView.getmActivity(), "省市区不能为空");
            return;
        }
        if (TextUtils.isEmpty(address2)) {
            UtilMethod.Toast(iEditAddressView.getmActivity(), "详细地址不能为空");
            return;
        }




        RequestParams params = new RequestParams(Constants.URL_ADDRESS_UPDATE);
        //账户
        String number = CacheUtils.getString(iEditAddressView.getmActivity(), "number", "");
        params.addBodyParameter("account", number);
        //地址id
        params.addBodyParameter("id", id);
        //收货人姓名
        params.addBodyParameter("name", name);
        //收货人手机号
        params.addBodyParameter("mobilePhone", number);
        //收货人省市区
        params.addBodyParameter("pcadetail",address);
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
                            UtilMethod.setOverdepengingOut(iEditAddressView.getmActivity());
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

    @Override
    public void showProvince() {
        WheelDialog wheelDialog = new WheelDialog(iEditAddressView.getmActivity());
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
                iEditAddressView.setText(address);
            }
        });
        wheelDialog.show();
    }
}
