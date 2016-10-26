package com.betterda.xsnano.acitivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.betterda.xsnano.R;
import com.betterda.xsnano.view.SurfaceViewTempalte;


/**
 * 使用自定义的surfaceview实现抽奖,比较卡
 * Created by Administrator on 2016/4/22.
 */
public class LuckPanActivity extends Activity implements View.OnClickListener, SurfaceViewTempalte.OnChangedListener {
    private SurfaceViewTempalte sf_luckpan;
    private ImageView iv_luckpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }



    public void initView() {

        setContentView(R.layout.activity_luckpan);
        sf_luckpan = (SurfaceViewTempalte) findViewById(R.id.sf_luckPan);
        iv_luckpan = (ImageView) findViewById(R.id.iv_luckpan);
    }


    public void initListener() {

        iv_luckpan.setOnClickListener(this);
        sf_luckpan.setListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_luckpan:
                if (!sf_luckpan.isStart()) {
                    sf_luckpan.luckStart(1);
                    iv_luckpan.setImageResource(R.mipmap.stop);
                } else {
                    if (!sf_luckpan.isShouldEnd()) {
                        sf_luckpan.luckEnd();

                    }

                }

                break;
        }
    }

    /**
     * 子方法在子线程中
     */
    @Override
    public void changed() {
         runOnUiThread(new Runnable() {
             @Override
             public void run() {

                 iv_luckpan.setImageResource(R.mipmap.start);
             }
         });
    }
}
