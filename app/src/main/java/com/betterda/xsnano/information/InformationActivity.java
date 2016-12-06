package com.betterda.xsnano.information;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.ImageTools;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.NormalTopBar;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;

import org.xutils.http.RequestParams;

import java.io.File;

/**
 * 个人资料
 * Created by lyf
 */
public class InformationActivity extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topBar;
    private RelativeLayout relative_touxiang;
    private LinearLayout linear_name, linear_sex;
    private SimpleDraweeView simpleDraweeView;
    private TextView tv_number, tv_name, tv_sex;
    private String goosimg; //保存照片地址
    private Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.png")); //保存照片的uri

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_information);
        topBar = (NormalTopBar) findViewById(R.id.topbar_information);
        relative_touxiang = (RelativeLayout) findViewById(R.id.relative_information_touxiang);
        linear_name = (LinearLayout) findViewById(R.id.linear_information_name);
        linear_sex = (LinearLayout) findViewById(R.id.linear_information_sex);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.sv_information_touxinag);
        tv_name = (TextView) findViewById(R.id.tv_information_name);
        tv_number = (TextView) findViewById(R.id.tv_information_number);
        tv_sex = (TextView) findViewById(R.id.tv_information_sex);

    }

    @Override
    public void initListener() {
        super.initListener();
        topBar.setOnBackListener(this);
        relative_touxiang.setOnClickListener(this);
        linear_sex.setOnClickListener(this);
        linear_name.setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        topBar.setTitle("个人资料");
        getData();
    }

    private void getData() {


    }

    @Override
    public void onStart() {
        super.onStart();
        //先判断是否登录
        boolean islogin = CacheUtils.getBoolean(getmActivity(), "islogin", false);
        if (islogin) {
            String number = CacheUtils.getString(getmActivity(), "number", "");

            if (!TextUtils.isEmpty(number)) {
                if (null != tv_number) {

                    tv_number.setText(number);
                }
                //为了做到换号登录，数据还在，所以头像，昵称 要跟帐号绑定
                String touxiang = CacheUtils.getString(getmActivity(), number + "touxiang", "");
                if (!TextUtils.isEmpty(touxiang)) {
                    if (null != simpleDraweeView) {

                        simpleDraweeView.setImageURI(Uri.parse(touxiang));
                    }

                }
                String name = CacheUtils.getString(getmActivity(), number + "name", "");
                if (!TextUtils.isEmpty(name)) {
                    if (null != tv_name) {

                        tv_name.setText(name);
                    }
                }
            }
        } else {
            if (null != tv_number) {

                tv_number.setText("");
            }
            if (null != simpleDraweeView) {

                simpleDraweeView.setImageURI(Uri.parse("res://com.betterda.xsnano/" + R.mipmap.viewpager_zwt));
            }
            if (null != tv_name) {

                tv_name.setText("昵称");
            }
        }


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                backActivity();
                break;
            case R.id.relative_information_touxiang:
                showPopupWindowPhoto();
                break;
            case R.id.linear_information_name:
                UtilMethod.startIntent(this, NameActivity.class);
                break;
            case R.id.linear_information_sex:

            case R.id.tv_photo_cameral://拍照
                paizhao();
                closePopupWindow();
                break;
            case R.id.tv_photo_photo:
                choosePicture();
                closePopupWindow();
            case R.id.tv_photo_cancell:
                closePopupWindow();
                break;
        }
    }

    /**
     * 拍照
     */
    private void paizhao() {
        try {
            if (!ImageTools.checkSDCardAvailable()) {
                UtilMethod.Toast(this, "内存卡错误,请检查您的内存卡");
                return;
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 这样就将文件的存储方式和uri指定到了Camera应用中 指定了这个 data一般为空了
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, Constants.PHOTOHRAPH);
        } catch (Exception e) {
            UtilMethod.Toast(this, "拍照失败");
        }

    }

    /**
     * 选取图片
     */
    private void choosePicture() {
        try {
            if (!ImageTools.checkSDCardAvailable()) {
                UtilMethod.Toast(this, "内存卡错误,请检查您的内存卡");
                return;
            }
            Intent intentphoto = new Intent(Intent.ACTION_PICK);
            intentphoto.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    Constants.IMAGE_UNSPECIFIED);
            startActivityForResult(intentphoto, Constants.PHOTOZOOM);
        } catch (Exception e) {
            UtilMethod.Toast(this, "选取照片失败");
        }

    }

    /**
     * 选择照片
     */
    private void showPopupWindowPhoto() {

        View view = LayoutInflater.from(this).inflate(R.layout.pp_choose_photo,
                null);
        TextView tv_cameral = (TextView) view.findViewById(R.id.tv_photo_cameral);
        TextView tv_photo = (TextView) view.findViewById(R.id.tv_photo_photo);
        TextView tv_cancell = (TextView) view.findViewById(R.id.tv_photo_cancell);
        tv_cameral.setOnClickListener(this);
        tv_photo.setOnClickListener(this);
        tv_cancell.setOnClickListener(this);
        setUpPopupWindow(view, null, 1, UtilMethod.getHeight(this));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 拍照
        if (requestCode == Constants.PHOTOHRAPH) {
            if (resultCode == RESULT_OK) {// 返回成功的时候
                cropImg(imageUri);
            } else if (resultCode == RESULT_CANCELED) {// 取消的时候
                UtilMethod.Toast(this, "取消拍照");
            } else {
                // 失败的时候
                UtilMethod.Toast(this, "拍照失败");
            }

        }

        // 读取相册缩放图片
        if (requestCode == Constants.PHOTOZOOM && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                cropImg(uri);
                //pathphoto = ImageTools.changeUriToPath(uri, this);
            } else {
                UtilMethod.Toast(this, "图片选取失败");
            }
        }


        if (requestCode == 3 && resultCode == -1) { //-1表示裁剪成功
            // 防止内存溢出
            String path = Environment.getExternalStorageDirectory()
                    + "/image.png";
            Bitmap pic = ImageTools.scacleToBitmap(path, this);
            if (pic != null) {// 这个ImageView是拍照完成后显示图片

                setPhoto(pic);
            }
        }


    }

    /**
     * 裁剪
     *
     * @param uri 图片的uri
     */
    private void cropImg(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); //X方向上的比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 128); //裁剪区的宽
        intent.putExtra("outputY", 128);
        intent.putExtra("scale", true); //是否保留比例
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 3);
    }

    private void setPhoto(Bitmap pic) {

        //清除fresco的缓存
        // imagePipeline.clearCaches();

        // 将bitmap保存到本地
        ImageTools.savePhotoToSDCard(pic, Constants.PHOTOPATH,
                Constants.PHOTONAME);
        // 保存照片地址
        goosimg = "file://" + Constants.PHOTOPATH + Constants.PHOTONAME
                + ".png";


        RequestParams params = new RequestParams(Constants.URL__UPLOAD_TOUXIANG);
        params.addBodyParameter("account", CacheUtils.getString(this, "number", ""));
        try {
            params.addBodyParameter("file", new File(Constants.PHOTOPATH + Constants.PHOTONAME
                    + ".png"));
        } catch (Exception e) {

        }

        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                GsonParse.parser(UtilMethod.getString(s), new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {
                        if ("true".equals(result)) {

                            String number = CacheUtils.getString(InformationActivity.this, "number", "");
                            if (!TextUtils.isEmpty(number)) {
                                CacheUtils.putString(InformationActivity.this, number + "touxiang", goosimg);
                            }

                            ImagePipeline imagePipeline = Fresco.getImagePipeline();
                            //删除fresco缓存中的一条uri,这样图片才会更新
                            imagePipeline.evictFromMemoryCache(Uri.parse(goosimg));
                            imagePipeline.evictFromDiskCache(Uri.parse(goosimg));
                            if (simpleDraweeView != null) {

                                simpleDraweeView.setImageURI(Uri.parse(goosimg));
                            }
                        } else {
                            UtilMethod.Toast(InformationActivity.this, resultMsg);
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                UtilMethod.Toast(InformationActivity.this, "修改头像失败");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closePopupWindow();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        UtilMethod.backgroundAlpha(1.0f, this);
    }
}
