package com.betterda.xsnano.util;

import android.text.TextUtils;

import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.javabean.CommentP;
import com.betterda.xsnano.javabean.CommonAddress;
import com.betterda.xsnano.javabean.GoldChangeBean;
import com.betterda.xsnano.javabean.GoldGet;
import com.betterda.xsnano.javabean.KaQuan;
import com.betterda.xsnano.javabean.OrderList;
import com.betterda.xsnano.javabean.SaleDay;
import com.betterda.xsnano.javabean.ShopChange;
import com.betterda.xsnano.javabean.StoreList;
import com.betterda.xsnano.javabean.Wallet;
import com.betterda.xsnano.javabean.WalletList;
import com.betterda.xsnano.javabean.Zhongj;
import com.betterda.xsnano.javabean.ZiTiMa;
import com.betterda.xsnano.shop.model.GoldChange;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * json解析
 *
 * @author Administrator
 */
public class GsonParse {

    private static Gson gson = null;

    static {
        if (null == gson) {
            gson = new GsonBuilder().create();
        }
    }

    /**
     * 使用Gson解析String
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T getObject(String jsonString, Class<T> cls) {
        T t = null;
        if (null != jsonString) {
            t = gson.fromJson(jsonString, cls);
        }

        return t;
    }

    /**
     * 使用Gson解析String
     *
     * @param
     * @param cls
     * @return
     */
    public static <T> T getObject(JsonElement jsonElement, Class<T> cls) {
        T t = null;
        t = gson.fromJson(jsonElement, cls);

        return t;
    }

    /**
     * 使用Gson解析list封装String
     *
     * @param jsonString
     * @param
     * @return
     */

    public static List<String> getListString(String jsonString) {
        List<String> list = new ArrayList<String>();
        list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
        }.getType());
        return list;

    }

    /**
     * 使用Gson解析list封装对象,不能用通配T,获取轮播图
     *
     * @param jsonString
     * @param
     * @return
     */

    public static List<CommentP> getListCommentP(String jsonString) {
        List<CommentP> list = new ArrayList<CommentP>();
        list = gson.fromJson(jsonString, new TypeToken<List<CommentP>>() {
        }.getType());
        return list;
    }

    /**
     * 获取金币夺宝
     */
    public static List<GoldGet> getListGoldGet(String jsonString) {
        List<GoldGet> list = new ArrayList<GoldGet>();
        list = gson.fromJson(jsonString, new TypeToken<List<GoldGet>>() {
        }.getType());
        return list;

    }

    /**
     * 获取金币兑换
     */
    public static List<GoldChangeBean> getListGoldChange(String jsonString) {
        List<GoldChangeBean> list = new ArrayList<GoldChangeBean>();
        list = gson.fromJson(jsonString, new TypeToken<List<GoldChangeBean>>() {
        }.getType());
        return list;

    }

    /**
     * 获取店铺列表
     *
     * @param jsonString
     * @return
     */
    public static List<StoreList> getListStoreList(String jsonString) {
        List<StoreList> list = new ArrayList<StoreList>();
        list = gson.fromJson(jsonString, new TypeToken<List<StoreList>>() {
        }.getType());
        return list;


    }

    /**
     * 获取每日特卖
     *
     * @param jsonString
     * @return
     */
    public static List<SaleDay> getListSaleDay(String jsonString) {
        List<SaleDay> list = new ArrayList<SaleDay>();
        list = gson.fromJson(jsonString, new TypeToken<List<SaleDay>>() {
        }.getType());
        return list;


    }

    public static List<ZiTiMa> getListZiTiMa(String jsonString) {
        List<ZiTiMa> list = new ArrayList<ZiTiMa>();
        list = gson.fromJson(jsonString, new TypeToken<List<ZiTiMa>>() {
        }.getType());
        return list;


    }

    /**
     * 获取订单列表
     *
     * @param jsonString
     * @return
     */
    public static List<OrderList> getListOrderList(String jsonString) {
        List<OrderList> list = new ArrayList<OrderList>();
        list = gson.fromJson(jsonString, new TypeToken<List<OrderList>>() {
        }.getType());
        return list;


    }

    public static List<ShopChange> getListShopChange(String jsonString) {
        List<ShopChange> list = new ArrayList<ShopChange>();
        list = gson.fromJson(jsonString, new TypeToken<List<ShopChange>>() {
        }.getType());
        return list;
    }

    /**
     * 获取卡券
     *
     * @param jsonString
     * @return
     */
    public static List<KaQuan> getListKaQuan(String jsonString) {
        List<KaQuan> list = new ArrayList<KaQuan>();
        list = gson.fromJson(jsonString, new TypeToken<List<KaQuan>>() {
        }.getType());
        return list;


    }

    /**
     * 获取金币明细
     *
     * @param jsonString
     * @return
     */
    public static List<WalletList> getListWallet(String jsonString) {
        List<WalletList> list = new ArrayList<WalletList>();
        list = gson.fromJson(jsonString, new TypeToken<List<WalletList>>() {
        }.getType());
        return list;


    }

    public static List<Zhongj> getListZhongj(String jsonString) {
        List<Zhongj> list = new ArrayList<Zhongj>();
        list = gson.fromJson(jsonString, new TypeToken<List<Zhongj>>() {
        }.getType());
        return list;


    }
    public static List<CommonAddress> getListCommonAddress(String jsonString) {
        List<CommonAddress> list = new ArrayList<CommonAddress>();
        list = gson.fromJson(jsonString, new TypeToken<List<CommonAddress>>() {
        }.getType());
        return list;


    }


    /**
     * 使用Gson解析list封装Map
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> getListMap(String jsonString) {
        List<Map<String, Object>> list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
        return list;
    }

    /**
     * 通过节点解析json数据
     * 根据文档修改 code和result
     *
     * @param json ,ParserGsonInterface parserGsonInterface
     */
    public static void parser(String json, ParserGsonInterface parserGsonInterface) {
        JsonParser parser = new JsonParser();//获得解析者
        if (null != parser) {
            //获得根节点元素
            JsonElement element = parser.parse(json);
            if (null != element) {
                //根据文档判断根节点元素属于什么类型的节点对象
                // 这个是json字符串是用{}包含的表示是一个object,所以用获取object的方法获取节点对象
                JsonObject root = element.getAsJsonObject();
                if (null != root) {
                    //在根据文档获取子节点对象的value值,因为code节点对应的只是一个string,就用普通的方法获取
                    JsonPrimitive codeJson = root.getAsJsonPrimitive("result");
                    String result = codeJson.getAsString();
                    JsonPrimitive returnMsgJson = root.getAsJsonPrimitive("returnMsg");
                    String returnMsg = returnMsgJson.getAsString();
                    //在这里使用parserGsonInterface接口中的方法来实现回调
                    parserGsonInterface.onSuccess(result, returnMsg);
                }


            }
        }
    }


    public static void parser(String json, String key, ParserGsonInterface parserGsonInterface) {
        JsonParser parser = new JsonParser();//获得解析者
        if (null != parser) {
            //获得根节点元素
            JsonElement element = parser.parse(json);
            if (null != element) {
                //根据文档判断根节点元素属于什么类型的节点对象
                // 这个是json字符串是用{}包含的表示是一个object,所以用获取object的方法获取节点对象
                JsonObject root = element.getAsJsonObject();
                if (null != root) {
                    //在根据文档获取子节点对象的value值,因为code节点对应的只是一个string,就用普通的方法获取
                    JsonPrimitive codeJson = root.getAsJsonPrimitive("result");
                    String result = codeJson.getAsString();
                    JsonElement jsonElement = root.get(key);
                    //在这里使用parserGsonInterface接口中的方法来实现回调
                    parserGsonInterface.onSuccess(result, jsonElement.toString());
                }


            }
        }
    }

    public static void main(String[] args) {
//        Date date = new Date(1467907200000l-System.currentTimeMillis());
//
//        SimpleDateFormat format = new SimpleDateFormat("dd天HH时mm分ss秒");
//        System.out.println(format.format(date));
        long start = 1467907200000l;
        long now = System.currentTimeMillis();

        long out = (start - now) / 3600 / 1000;
        System.out.println(out);
    }
}
