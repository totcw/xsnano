package com.betterda.xsnano.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.betterda.xsnano.R;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.comment.AddCommentActivity;
import com.betterda.xsnano.dialog.CallDialog;
import com.betterda.xsnano.interfac.ParserJsonInterface;
import com.betterda.xsnano.login.LoginActivity;
import com.betterda.xsnano.orderall.OrderDetailActivity;
import com.betterda.xsnano.orderall.model.OrderAll;
import com.betterda.xsnano.pay.PayActivity;
import com.betterda.xsnano.store.StoreActivity;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.ShapeLoadingDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lyf
 */
public class UtilMethod {

    /**
     * get mobile device id
     *
     * @return
     */
    public static String getUdId(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return null;
        }
        return tm.getDeviceId();
    }

    /**
     * get app version name and version code
     */
    public static String getAppVersion(Context context) {
        String versionName = "0.0.0";
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName + "-" + versionCode;
    }

    /**
     * get app version name
     */
    public static String getAppVersionName(Context context) {
        String versionName = "0.0.0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * get app version code
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
            return versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }

    /**
     * get mobile model
     */
    public static String getDevice() {
        return Build.MODEL;
    }

    /**
     * get mobile phone number and replace china number
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager phoneMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String tel = phoneMgr.getLine1Number();
        if (tel != null) {
            tel = tel.replace("+86", "").trim();
        }
        return tel;
    }


    /**
     * close the soft keyboard
     *
     * @param context
     */
    public static void closeKeyBox(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        final View v = ((Activity) context).getWindow().peekDecorView();
        imm.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static int getWeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        int screenWidth = dm.widthPixels;
        return screenWidth;

    }

    /**
     * 获取屏幕的高度
     *
     * @param activity
     * @return
     */
    public static int getHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        int screenWidth = dm.heightPixels;
        return screenWidth;

    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    public static int statusHeight(Activity activity) {
        //获取状态栏高度
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
   // 获取是否存在NavigationBar：

    public static boolean checkDeviceHasNavigationBar(Context context) {

        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }


        return hasNavigationBar;
    }

    /**
     * 获取底部导航的高度
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {

        if (!checkDeviceHasNavigationBar(context)) { //如果没显示
            return 0;
        }

        final boolean isMeiZu = Build.MANUFACTURER.equals("Meizu");

        final boolean autoHideSmartBar = Settings.System.getInt(context.getContentResolver(),
                "mz_smartbar_auto_hide", 0) == 1;

        if (isMeiZu) {
            if (autoHideSmartBar) {
                return 0;
            } else {
                try {
                    Class c = Class.forName("com.android.internal.R$dimen");
                    Object obj = c.newInstance();
                    Field field = c.getField("mz_action_button_min_height");
                    int height = Integer.parseInt(field.get(obj).toString());
                    return context.getResources().getDimensionPixelSize(height);
                } catch (Throwable e) { // 不自动隐藏smartbar同时又没有smartbar高度字段供访问，取系统navigationbar的高度
                    return getNormalNavigationBarHeight(context);
                }
            }
        } else {
            return getNormalNavigationBarHeight(context);
        }
    }

    protected static int getNormalNavigationBarHeight(final Context ctx) {
        try {
            final Resources res = ctx.getResources();
            int rid = res.getIdentifier("config_showNavigationBar", "bool", "android");
            if (rid > 0) {
                boolean flag = res.getBoolean(rid);
                if (flag) {
                    int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
                    if (resourceId > 0) {
                        return res.getDimensionPixelSize(resourceId);
                    }
                }
            }
        } catch (Throwable e) {
        }
        return 0;
    }

    /**
     * 跳转activity
     *
     * @param context
     * @param cla
     */
    public static <T> void startIntent(Context context, Class<T> cla) {
        Intent intent = new Intent(context, cla);
        context.startActivity(intent);
    }


    /**
     * 带伸缩动画的跳转
     * @param context
     * @param cla
     * @param view
     * @param key
     * @param value
     * @param <T>
     */
    public static <T> void startIntentparams(Context context, Class<T> cla,View view,String key, String value,String message) {
        Intent intent = new Intent(context, cla);
        intent.putExtra(key, value);

        ActivityOptionsCompat transitionActivityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,view,message
                );

        ActivityCompat.startActivity((Activity) context,
                intent, transitionActivityOptions.toBundle());
    }

    /**
     * 跳转activity带参数
     *
     * @param context
     * @param cla
     */
    public static <T> void startIntentParams(Context context, Class<T> cla, String key, String value) {
        Intent intent = new Intent(context, cla);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }


    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    // 获得圆角图片的方法
    public static Bitmap toRoundBitmap(Context context, Bitmap bitmap) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            left = 0;
            bottom = width;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(4);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);

        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, width / 2, width / 2 - 4 / 2, paint);
        return output;
    }


    /**
     * 02.     * 设置添加popupwindow屏幕的背景透明度
     * 03.     * @param bgAlpha
     * 04.
     */
    public static void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 打印土司
     *
     * @param context
     * @param message
     */
    public static void Toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;

    }

    /**
     * 获取当前的时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrdentTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    /**
     * 获取当前的时间格式 yyyy-MM-dd
     */
    public static String getCurrdentTime2() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        String time = format.format(date);
        return time;
    }

    /**
     * 得到自定义的一个加载对话框
     *
     * @param mContext
     * @param content
     * @return
     */
    public static ShapeLoadingDialog createDialog(Context mContext, String content) {
        ShapeLoadingDialog shapeLoadingDialog = new ShapeLoadingDialog(mContext);
        shapeLoadingDialog.setLoadingText(content);
        shapeLoadingDialog.setCanceledOnTouchOutside(false);

        return shapeLoadingDialog;
    }


    /**
     * 设置定位的参数
     */
    public static void initLocation(LocationClient mLocationClient) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 计算总价
     *
     * @return
     */
    public static float addUp(List<Bus> list) {
        float a = 0;

        if (list != null) {
            for (Bus bus : list) {
                int amount = bus.getAmount();
                float money = bus.getMoney();
                float sum = amount * money;
                a += sum;
            }
        }
        return a;
    }

    /**
     * 计算商品的件数
     *
     * @return
     */
    public static int addAmount(List<Bus> list) {
        int a = 0;

        if (list != null) {
            for (Bus bus : list) {
                int amount = bus.getAmount();
                a += amount;
            }
        }
        return a;
    }

    /**
     * 判断是否登录并且跳转
     *
     * @param context
     * @param clazz   要跳转的界面
     */
    public static void isLogin(Context context, Class<?> clazz) {
        boolean islogin = isLogin(context);
        if (islogin) {
            UtilMethod.startIntent(context, clazz);
        } else {
            UtilMethod.startIntent(context, LoginActivity.class);
        }
    }

    /**
     * 判断是否登录
     */
    public static boolean isLogin(Context context) {
        boolean islogin = CacheUtils.getBoolean(context, "islogin", false);
        return islogin;
    }

    /**
     * 显示付款界面
     *
     * @param viewHolder
     */
    public static void show(ViewHolder viewHolder, boolean isshow, boolean show, boolean show2, boolean show3) {
        viewHolder.setVisible(R.id.relative_order_delete, isshow);
        viewHolder.setVisible(R.id.tv_item_orderall_pay, show);
        viewHolder.setVisible(R.id.tv_item_orderall_delete, show2);
        viewHolder.setVisible(R.id.tv_item_orderall_shouhuo, show3);
    }


    public static void settingView(ViewHolder viewHolder, final OrderAll orderAll, final Context context) {
        if (null != orderAll) {
            //设置时间
            viewHolder.setText(R.id.tv_item_orderall_time, orderAll.getTime());
            //设置交易类型
            viewHolder.setText(R.id.tv_item_orderall_type, orderAll.getType());

            List<Bus> busList = orderAll.getList();
            if (null != busList) {
                final float price = UtilMethod.addUp(busList);
                //设置商品的件数
                viewHolder.setText(R.id.tv_item_orderall_amount2, "共" + UtilMethod.addAmount(busList) + "件商品");
                //设置商品的价格
                viewHolder.setText(R.id.tv_item_orderall_money, "￥" + UtilMethod.addUp(busList));
                RecyclerView rv = viewHolder.getView(R.id.rv_order_item);
                rv.setLayoutManager(new LinearLayoutManager(context));
                rv.setAdapter(new CommonAdapter<Bus>(context, R.layout.item_recycleview_comfirmorder, busList) {

                    @Override
                    public void convert(ViewHolder viewHolder, Bus bus) {
                        if (bus != null) {
                            //设置商品信息
                            viewHolder.setText(R.id.tv_item_order_name, bus.getName());
                            viewHolder.setText(R.id.tv_item_order_price, "￥" + bus.getMoney());
                            viewHolder.setText(R.id.tv_item_order_amount, "X " + bus.getAmount());
                            if (!TextUtils.isEmpty(bus.getUrl())) {
                                SimpleDraweeView simpleDraweeView = viewHolder.getView(R.id.sv_item_order);
                                if (simpleDraweeView != null) {
                                    simpleDraweeView.setImageURI(Uri.parse(bus.getUrl()));
                                }
                            }
                            viewHolder.setOnClickListener(R.id.linear_comfirmorder2, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    UtilMethod.startIntent(context, OrderDetailActivity.class);
                                }
                            });
                            //显示评价按钮
                            if ("待评价".equals(orderAll.getState())) {
                                viewHolder.setVisible(R.id.tv_item_comfirmorder_comment, true);
                            } else {
                                viewHolder.setVisible(R.id.tv_item_comfirmorder_comment, false);
                            }
                            viewHolder.setOnClickListener(R.id.tv_item_comfirmorder_comment, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    UtilMethod.startIntent(context, AddCommentActivity.class);
                                }
                            });
                        }


                    }
                });

                if ("待付款".equals(orderAll.getState())) {
                    //显示待付款的界面
                    UtilMethod.show(viewHolder, true, true, true, false);


                } else if ("待收货".equals(orderAll.getState())) {
                    //显示待付款的界面
                    UtilMethod.show(viewHolder, true, false, false, true);
                } else {
                    //显示待付款的界面
                    UtilMethod.show(viewHolder, false, false, false, false);
                }

                //立即付款
                viewHolder.setOnClickListener(R.id.tv_item_orderall_pay, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, PayActivity.class);
                        intent.putExtra("money", price);
                        intent.putExtra("money3", 0);
                        context.startActivity(intent);
                    }
                });
                //取消订单
                viewHolder.setOnClickListener(R.id.tv_item_orderall_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
                //立即收货
                viewHolder.setOnClickListener(R.id.tv_item_orderall_shouhuo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UtilMethod.Toast(context, "立即收货");
                    }
                });
                //订单详情
                viewHolder.setOnClickListener(R.id.linear_comfirmorder, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UtilMethod.startIntent(context, OrderDetailActivity.class);
                    }
                });
            }


        }
    }

    /**
     * 去掉反斜杠
     *
     * @param s
     */
    public static String deleteString(String s) {
        if (null != s) {

            String replace = s.replace("\\", "");
            return replace;
        }
        return null;
    }

    /**
     * 去除服务器返回的json含有的\和开头结尾的""
     *
     * @param s
     * @return
     */
    public static String getString(String s) {
        String substring = null;
        if (!TextUtils.isEmpty(s)) {
            String s1 = UtilMethod.deleteString(s);
            if (!TextUtils.isEmpty(s1)) {
                if (s1.startsWith("\"") && s1.endsWith("\"")) {
                    substring = s1.substring(1, s1.length() - 1);
                }

            }
        }


        return substring;
    }

    /**
     * 解析json map格式的数据
     *
     * @param s
     * @param parserJsonInterface
     */
    public static void parSerJson(String s, ParserJsonInterface parserJsonInterface) {
        String subString = UtilMethod.getString(s);
        if (!TextUtils.isEmpty(subString)) {

            List<Map<String, Object>> listMap = GsonParse.getListMap(subString);
            if (null != listMap) {
                for (Map<String, Object> map : listMap) {
                    if (null != parserJsonInterface) {
                        parserJsonInterface.parser(map);
                    }
                }
            }
        }
    }

    /**
     * 拼接url
     *
     * @param url
     * @return
     */
    public static String url(String url) {
        return Constants.URL + url;
    }

    /**
     * 生成bitmap
     *
     * @param matrix
     * @return
     */
    public static Bitmap bitMatrix2Bitmap(BitMatrix matrix) {
        int w = matrix.getWidth();
        int h = matrix.getHeight();
        int[] rawData = new int[w * h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int color = Color.WHITE;
                if (matrix.get(i, j)) {
                    color = Color.BLACK;
                }
                rawData[i + (j * w)] = color;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        bitmap.setPixels(rawData, 0, w, 0, 0, w, h);
        return bitmap;
    }

    /**
     * 根据内容生成二维码bitmap
     *
     * @param content
     * @return
     */
    public static Bitmap generateQRCode(String content, Context context) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            // MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, UtilMethod.dip2px(context, 300), UtilMethod.dip2px(context, 300));
            return bitMatrix2Bitmap(matrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 从充值跳转到支付界面
     *
     * @param money2   支付的金额
     * @param activity
     */
    public static void pay(String money2, Activity activity) {
        int max = Integer.parseInt(money2);
        if (max > 50000) {
            UtilMethod.Toast(activity, "最大充值金额不能超过5万");
            return;
        }
        String paymoney = money2 + "00";
        Intent intent = new Intent(activity, PayActivity.class);
        intent.putExtra("showmoney", money2);
        intent.putExtra("paymoney", paymoney);
        activity.startActivity(intent);
    }


    /**
     * float格式化保留2位
     *
     * @param money
     */
    public static String FloatFormat(float money) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(money);//format 返回的是字符串
        return p;
    }

    /**
     * float格式化保留1位
     *
     * @param money
     */
    public static String FloatFormat1(float money) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(money);//format 返回的是字符串
        return p;
    }


    /**
     * 去掉付款金额的小数点转化为分
     *
     * @param money
     * @return
     */
    public static String deletePoint(String money) {

        //去掉点
        int index = money.indexOf(".");
        String money1 = money.substring(0, index);
        String money2 = money.substring(index + 1);

        //先判断整数部分是不是0
        if ("0".equals(money1) || "".equals(money1)) {
            //如果整数部分为0,就判断小数部分是不是0开头
            if (!TextUtils.isEmpty(money2)) {


                if (money2.startsWith("0")) {
                    //如果是以0开头就取后一位数
                    int indexF = money2.indexOf("0");
                    int length = money2.length();
                    money2 = money2.substring(indexF + 1, length);
                }

                //如果不为0且长度是1就补0
                if (!"0".equals(money2) && money2.length() == 1) {
                    money2 = money2 + "0";
                }
            }
            //整数部分为0就取小数部分的值
            money = money2;
        } else {
            //如果整数部分不是0直接拼接即可
            money = money1 + money2;
        }
        return money;
    }

    /**
     * 显示对话框
     */
    public static void showDialog(Activity activity, ShapeLoadingDialog dialog) {
        if (!activity.isFinishing()) {
            if (dialog != null) {
                dialog.show();
            }
        }
    }
    public static void showDialog(Activity activity, Dialog dialog) {
        if (!activity.isFinishing()) {
            if (dialog != null) {
                dialog.show();
            }
        }
    }

    /**
     * 关闭对话框
     */
    public static void dissmissDialog(Activity activity, ShapeLoadingDialog dialog) {
        if (!activity.isFinishing()) {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }
    public static void dissmissDialog(Activity activity, Dialog dialog) {
        if (!activity.isFinishing()) {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }


    /**
     * 判断数据是否为空
     * @param listLocation
     * @param loadingPagerLocation
     */
    public static void hideOrEmpty(List listLocation,LoadingPager loadingPagerLocation) {
        if (listLocation != null&&loadingPagerLocation!=null) {
            if (listLocation.size() > 0) {
                loadingPagerLocation.hide();
            } else {
                loadingPagerLocation.setEmptyVisable();
            }
        } else {
            loadingPagerLocation.setEmptyVisable();
        }
    }

    /**
     * 用于退出 伸缩动画的界面
     */
    public static void back(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity. finishAfterTransition();
        } else {
            activity.finish();
        }
    }

}
