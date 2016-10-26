package com.betterda.xsnano.address.presenter;

import com.betterda.xsnano.address.model.Address;
import com.betterda.xsnano.address.view.IAddAddView;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;

import org.xutils.http.RequestParams;

/**
 * Created by Administrator on 2016/5/17.
 */
public class IAddAddPresenterImpl implements IAddAddPresenter {
    private IAddAddView addAddView;
    private String name;
    private String number;
    private String address;
    private String address2;

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
    private void getString() {
        name = addAddView.getEditViewName().getText().toString();
        number = addAddView.getEditViewNumber().getText().toString();
        address = addAddView.getEditViewAdress().getText().toString();
        address2 = addAddView.getEditViewAdress2().getText().toString();
    }



    public void getData(boolean isMoren) {






        RequestParams params = new RequestParams(Constants.URL_ADDRESS_ADD);
        //账户
        String number = CacheUtils.getString(addAddView.getmActivity(), "number", "");
        params.addBodyParameter("account", number);
        //收货人姓名
        params.addBodyParameter("name", addAddView.getEditViewName().getText().toString().trim());
        //收货人手机号
        params.addBodyParameter("mobilePhone", addAddView.getEditViewNumber().getText().toString().trim());
        //收货人省市区
        params.addBodyParameter("pcadetail", addAddView.getEditViewAdress().getText().toString().trim());
        //收货人详细地址
        params.addBodyParameter("adress", addAddView.getEditViewAdress2().getText().toString().trim());
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
                            UtilMethod.Toast(addAddView.getmActivity(),"添加失败");
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
