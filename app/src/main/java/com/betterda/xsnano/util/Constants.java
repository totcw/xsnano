package com.betterda.xsnano.util;

import android.os.Environment;
import android.text.style.ClickableSpan;

public class Constants {

	public static final String CACHE_FILE_NAME = "xsnanao"; //缓存目录
	public static final String PHOTOPATH=Environment.getExternalStorageDirectory().getPath()+"/xsnanao/photo/";//存图片的路径

	public static String PHOTONAME="photo"; //存放照片的名字
	public static final int PHOTOZOOM = 1;// 相机选取
	public static final int PHOTOHRAPH = 2;// 相机拍照
	public static final String IMAGE_UNSPECIFIED = "image/*";

	public static final int TITLE_COUNT = 3;//APP底部的title个数
	public static final int PAGE_SIZE = 10;//一页加载的个数



	//首页底部的高度
	public static  int IDV_HEIGHT = 46;//默认46
	//首页标题的高度
	public static  int SHOUYE_TITLE = 48;//默认48
	//首页分类的高度
	public static  int SHOUYE_FN = 38;//默认38



	public static  String regiondId = "4028810356c5e0550156c5e1505900f3"; //区域id


	public class Cache{
		public static final String longitude = "longitude";//经度
		public static final String dimension = "dimension";
	}

	public class WeiXin{
		public static final String APP_ID = "wxc47819414dd37969";
		public static final String secret = "beca7a75bafaac5cbf56f523a5916576";
	}

	public class QQ{
		public static final String APP_ID = "1105531382";

	}




	public class WeiBo{

		/** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
		public static final String APP_KEY      = "337463077";
		/**
		 * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
		 * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
		 * </p>
		 */
		public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

		public static final String SCOPE ="";
	}





	//public static final String URL = "http://192.168.1.134:8080/xsnano_web/";
	public static final String URL = "http://www.meichebang.com.cn/xsnano_web/";


	//民生获取流水号的接口
	public static final String MS_ORDER_URL = "https://mpay.sinoqy.com:2070/qyapi/v3/mer/gettn";
	public static final String MS_BACK_URL =URL+"appAPI.do?api/CMBC/callback/post";//民生回调URL


	public static final String URL_LOGIN = URL+"appAPI.do?api/account/login";//登录
	public static final String URL_OAUTH_LOGIN = URL+"appAPI.do?api/oauth/login";//三方登录
	public static final String URL_REGISTER = URL+"appAPI.do?api/account/register";//注册
	public static final String URL_LUNBO = URL+"appAPI.do?api/indeximages/get";//轮播广告
	public static final String URL_COMMENT_GET = URL+"appAPI.do?api/comments/get";//评论获取
	public static final String URL_BUS_GET = URL+"appAPI.do?api/shopcart/get";//购物车获取
	public static final String URL_BUS_ADD = URL+"appAPI.do?api/shopcart/post";//购物车添加
	public static final String URL_BUS_UPDATE = URL+"appAPI.do?api/shopcart/update";//购物车更新
	public static final String URL_BUS_DELETE = URL+"appAPI.do?api/shopcart/del";//购物车删除
	public static final String URL_ADDRESS_GET = URL+"appAPI.do?api/address/get";//收货地址获取
	public static final String URL_ADDRESS_ADD = URL+"appAPI.do?api/address/post";//收货地址添加
	public static final String URL_ADDRESS_UPDATE = URL+"appAPI.do?api/address/update";//收货地址修改
	public static final String URL_ADDRESS_DELETE = URL+"appAPI.do?api/address/del";//收货地址删除
	public static final String URL_INFORMATION_GET = URL+"appAPI.do?api/account/get";//个人资料获取
	public static final String URL_INFORMATION_UPDATE = URL+"appAPI.do?api/account/update";//个人资料修改
	public static final String URL_INFORMATION_DELETE = URL+"appAPI.do?api/account/del";//个人资料删除
	public static final String URL_STORE_LIST = URL+"appAPI.do?api/shop/list/get";//店铺列表
	public static final String URL_SALE = URL+"appAPI.do?api/daily/sale/get";//每日特卖
	public static final String URL_SERVICES = URL+"appAPI.do?api/shop/detail/services/get";//服务范围
	public static final String URL_STORE_DETAIL = URL+"appAPI.do?api/shop/detail/get";//店铺详情
	public static final String URL_STORE_COMMENT = URL+"appAPI.do?api/shop/detail/comments/get";//店铺评价
	public static final String URL_GOODS_DETAIL = URL+"appAPI.do?api/product/detailAndComment/get";//商品详细
	public static final String URL_JINBI_DUOBAO = URL+"appAPI.do?api/golden/indiana/get";//金币夺宝
	public static final String URL_JINBI_DUOBAO_TOU = URL+"appAPI.do?api/golden/indiana/post";//金币夺宝投注
	public static final String URL_JINBI_CHANGE = URL+"appAPI.do?api/golden/exchange/get";//金币兑换
	public static final String URL__JINBI_CHANGE2 = URL+"appAPI.do?api/golden/exchange/post";//金币兑换_立即兑换
	public static final String URL__KAQUAN = URL+"appAPI.do?api/cardstock/get";//卡券
	public static final String URL__ZITIMA = URL+"appAPI.do?api/sincecode/get";//自提码
	public static final String URL__WALLET = URL+"appAPI.do?api/mywallet/get";//我的钱包
	public static final String URL__WALLET_JILU = URL+"appAPI.do?api/mywallet/detail/get";//我的钱包记录
	public static final String URL__ORDER_GET = URL+"appAPI.do?api/account/orders/get";//订单列表获取
	public static final String URL__SHOUHUO = URL+"appAPI.do?api/receive/orders/post";//立即收货
	public static final String URL__ORDER_CANCEL = URL+"appAPI.do?api/cancel/orders/post";//取消订单
	public static final String URL__ORDER_PAY = URL+"appAPI.do?api/pay/orders/post";//立即付款
	public static final String URL__ORDER_TUIKUAN = URL+"appAPI.do?api/apply/customer/service/post";//申请售后
	public static final String URL__ORDER_TUIKUAN_GET = URL+"appAPI.do?api/customer/service/get";//售后列表获取
	public static final String URL__ORDER_DETAIL = URL+"appAPI.do?api/order/detail/get";//获取订单详情
	public static final String URL__ORDER_CREAE = URL+"appAPI.do?api/order/post";//订单生成
	public static final String URL__COMMENT_ADD = URL+"appAPI.do?api/comment/post";//添加评论
	public static final String URL__UPLOAD = URL+"appAPI.do?upload";//上传文件
	public static final String URL__UPLOAD_TOUXIANG = URL+"appAPI.do?api/account/img/upload";//头像修改
	public static final String URL__SEARCH = URL+"appAPI.do?api/shop/list/search";//搜索
	public static final String URL_GOLD_CHANGE = URL + "appAPI.do?api/mywallet/gold/exchange/update";//金币兑换
	public static final String URL_ICON_CHANGE = URL + "appAPI.do?api/mywallet/icon/exchange/update";//银币兑换
	public static final String URL_SHOP_CHANGE_JILU = URL + "appAPI.do?api/golden/exchange/products/get";//商品兑换记录
	public static final String URL_SHOP_zhongj = URL + "appAPI.do?api/golden/indiana/win/record/get";//开奖记录
	public static final String URL_CACHE = URL + "appAPI.do?global/context/cache";//缓存
	public static final String URL_ADD_CHANGYONG_ADDRESS = URL + "appAPI.do?api/position/doAdd";//添加常用地址
	public static final String URL_DEL_CHANGYONG_ADDRESS = URL + "appAPI.do?api/position/doDel";//删除常用地址
	public static final String URL_QUERY_CHANGYONG_ADDRESS = URL + "appAPI.do?api/position/doQuery";//查询常用地址


}
