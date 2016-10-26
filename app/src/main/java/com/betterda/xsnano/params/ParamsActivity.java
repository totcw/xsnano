package com.betterda.xsnano.params;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;

/**
 * 图文详细
 * Created by Administrator on 2016/5/30.
 */
public class ParamsActivity extends BaseActivity {

    private WebView webView;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_params);
        webView = (WebView) findViewById(R.id.slidedetails_behind);
    }

    @Override
    public void init() {
        super.init();

        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            new Object() {
                public void setLoadWithOverviewMode(boolean overview) {
                    settings.setLoadWithOverviewMode(overview);
                }
            }.setLoadWithOverviewMode(true);
        }

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                String url = "http://www.baidu.com";
                webView.loadUrl(url);

            }
        });
    }
}
