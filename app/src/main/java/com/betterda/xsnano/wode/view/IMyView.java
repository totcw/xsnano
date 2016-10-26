package com.betterda.xsnano.wode.view;

import android.widget.TextView;

import com.betterda.xsnano.BaseView;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016/5/26.
 */
public interface IMyView extends BaseView {

    SimpleDraweeView getSimpleDraweeView();

    TextView getTextViewNumber();
}
