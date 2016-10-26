package com.betterda.xsnano.acitivity;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.betterda.xsnano.BuildConfig;
import com.betterda.xsnano.R;
import com.betterda.xsnano.util.UtilMethod;

/**
 * 二维码activity
 * Created by Administrator on 2016/6/27.
 */
public class EweiMaActivity extends BaseActivity {
    private ImageView iv;
    private Bitmap bitmap;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_eweima);
        iv = (ImageView) findViewById(R.id.iv_eweima);
    }

    @Override
    public void init() {
        super.init();
        String bianhao = getIntent().getStringExtra("bianhao");
        if (TextUtils.isEmpty(bianhao)) {
            bitmap = UtilMethod.generateQRCode("1", this);
        } else {
            bitmap = UtilMethod.generateQRCode(bianhao, this);
        }

        iv.setImageBitmap(bitmap);
    }
}
