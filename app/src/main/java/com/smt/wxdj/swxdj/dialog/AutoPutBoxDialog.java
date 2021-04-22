package com.smt.wxdj.swxdj.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smt.wxdj.swxdj.R;

/**
 * Created by gbh on 16/11/2.
 * 确认箱子换位置的对话框
 */

public class AutoPutBoxDialog extends Dialog implements View.OnClickListener {
    private TextView message;
    private Button btnCancle;
    private Button btnOk;
    private Context mContext;
    private String msg;

    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;

    public AutoPutBoxDialog(Context context, String msg) {
        super(context);
        this.mContext = context;
        this.msg = msg;
    }


    public AutoPutBoxDialog setConfirmClickListener(OnSweetClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
        return this;
    }

    public AutoPutBoxDialog setCancelClickListener(OnSweetClickListener mCancelClickListener) {
        this.mCancelClickListener = mCancelClickListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.alert_view2);
//        setTitle(mContext.getResources().getString(R.string.confimToast));
        message = (TextView) findViewById(R.id.message);
        btnCancle = (Button) findViewById(R.id.btnCancle);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnCancle.setOnClickListener(this);

        message.setText(msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                if (null != mConfirmClickListener)
                    mConfirmClickListener.onClick(this, view);
                else
                    dismiss();
                break;
            case R.id.btnCancle:
                if (null != mCancelClickListener)
                    mCancelClickListener.onClick(this, view);
                else
                    dismiss();
                break;
        }
    }

    public interface OnSweetClickListener {
        void onClick(Dialog dialog, View view);
    }
}
