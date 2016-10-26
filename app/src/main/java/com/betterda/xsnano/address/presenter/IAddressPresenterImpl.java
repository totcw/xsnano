package com.betterda.xsnano.address.presenter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;

import com.betterda.xsnano.R;
import com.betterda.xsnano.address.EditAddressActivity;
import com.betterda.xsnano.address.model.Address;
import com.betterda.xsnano.address.view.IAddressView;
import com.betterda.xsnano.interfac.ParserJsonInterface;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/17.
 */
public class IAddressPresenterImpl implements IAddressPresenter, View.OnClickListener {
    private IAddressView addressView;
    private RecyclerView recyclerView;
    private List<Address> list;
    private CommonAdapter adapter;
    private String wode;

    public IAddressPresenterImpl(IAddressView addressView) {
        this.addressView = addressView;
    }

    @Override
    public void start() {
        list = new ArrayList<>();

        Intent intent = addressView.getmActivity().getIntent();
        if (null != intent) {
            wode = intent.getStringExtra("wode");
        }
        recyclerView = addressView.getRecyclyView();
        recyclerView.setLayoutManager(new LinearLayoutManager(addressView.getContext()));
        adapter = new CommonAdapter<Address>(addressView.getContext(), R.layout.item_recycleview_address, list) {
            @Override
            public void convert(ViewHolder viewHolder, final Address address) {

                if (address!=null) {

                    viewHolder.setText(R.id.tv_address_name, address.getName());
                    viewHolder.setText(R.id.tv_address_number, address.getNumber());
                    viewHolder.setText(R.id.tv_address_address, address.getAddress2()+address.getAddress3());
                    View view = viewHolder.getView(R.id.tv_address_moren);
                    if (view != null) {
                        if (address.isMoren()) {
                            view.setVisibility(View.VISIBLE);
                        } else {
                            view.setVisibility(View.GONE);
                        }
                    }
                    //编辑
                    viewHolder.setOnClickListener(R.id.relative_item_address, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(addressView.getContext(), EditAddressActivity.class);
                            intent.putExtra("name", address.getName());
                            intent.putExtra("number", address.getNumber());
                            intent.putExtra("address", address.getAddress2());
                            intent.putExtra("address2", address.getAddress3());
                            intent.putExtra("id", address.getId());
                            addressView.getContext().startActivity(intent);
                        }
                    });
                    //选中
                    viewHolder.setOnClickListener(R.id.linear_item_address, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ("wode".equals(wode)) {

                            } else {
                                Intent intent = new Intent();
                                intent.putExtra("name", address.getName());
                                intent.putExtra("number", address.getNumber());
                                intent.putExtra("address", address.getAddress2() + address.getAddress3());
                                addressView.getmActivity().setResult(0, intent);
                                addressView.getmActivity().finish();
                            }


                        }
                    });

                }
            }
        };
        recyclerView.setAdapter(adapter);

        getData();
        if (addressView != null && addressView.getLoadPager() != null) {
            addressView.getLoadPager().setonEmptyClickListener(this);
            addressView.getLoadPager().setonErrorClickListener(this);
        }
    }

    private void getData() {
        RequestParams params = new RequestParams(Constants.URL_ADDRESS_GET);
        final String number = CacheUtils.getString(addressView.getmActivity(), "number", "");
        params.addBodyParameter("account", number);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {


                if (list != null) {
                    list.clear();
                }
                UtilMethod.parSerJson(s, new ParserJsonInterface() {
                    @Override
                    public void parser(Map<String, Object> map) {
                        if (null != map) {
                            Address address = new Address();
                            Object name = map.get("name");
                            if (name != null) {
                                address.setName(name.toString());
                            }
                            Object isDefault = map.get("isDefault");
                            if (isDefault != null) {
                                String isMoren = isDefault.toString();
                                if ("Y".equals(isMoren)) {
                                    address.setIsMoren(true);
                                } else {
                                    address.setIsMoren(false);
                                }
                            }
                            if (map.get("mobilePhone") != null) {

                            }
                            address.setNumber(map.get("mobilePhone").toString());
                            if (map.get("pcadetail") != null) {

                                address.setAddress2(map.get("pcadetail").toString());
                            }
                            if (map.get("adress") != null) {

                                address.setAddress3(map.get("adress").toString());
                            }
                            if (map.get("id") != null) {

                                address.setId(map.get("id").toString());
                            }
                            if (list != null) {
                                list.add(address);
                            }
                        }

                    }
                });


                if (adapter != null) {
                    adapter.notifyDataSetChanged();

                }

                LoadingPager loadPager = addressView.getLoadPager();
                if (loadPager != null) {
                    if (null != list) {
                        if (list.size() == 0) {
                            //显示数据为空的界面
                            loadPager.setEmptyVisable();
                        } else {
                            //隐藏加载界面
                            loadPager.hide();
                        }
                    } else {
                        loadPager.setEmptyVisable();
                    }

                }

                if (recyclerView != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                LoadingPager loadPager = addressView.getLoadPager();
                if (loadPager != null) {
                    loadPager.setErrorVisable();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_error:  //加载错误时的点击事件

                getData();

                break;
            case R.id.frame_empty://数据为空的点击事件
                getData();
                break;
        }
    }
}
