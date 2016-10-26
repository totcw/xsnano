package com.betterda.xsnano.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.betterda.xsnano.R;
import com.betterda.xsnano.view.LoadingView;

/**
 * Created by Administrator on 2016/5/19.
 */
public class DeleteDialog {
    private Context mContext;
    private Dialog mDialog;
    private View mDialogContentView;
    private onConfirmListener listener;

    public DeleteDialog(Context context,onConfirmListener listener) {
        this.mContext = context;
        this.listener = listener;
        init();
    }
    private void init() {
        mDialog = new Dialog(mContext, R.style.custom_dialog2);

        mDialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_delete,null);
        Button btn_quxiao = (Button) mDialogContentView.findViewById(R.id.btn_dialog_delete_quxiao);
        Button btn_comfirm = (Button) mDialogContentView.findViewById(R.id.btn_dialog_delete_comfrim);
        btn_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.comfirm();
                }
                dismiss();
            }
        });

        mDialog.setCancelable(false);//不能点外面取消,也不 能点back取消
        mDialog.setContentView(mDialogContentView);
    }

    public void show(){
        mDialog.show();

    }

    public void dismiss(){
        mDialog.dismiss();
    }

    public interface onConfirmListener{
        void comfirm();

    }
}
