package com.smt.wxdj.swxdj.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smt.wxdj.swxdj.R;

/**
 * @author gbh
 * @date 16/11/2
 * 是否执行抄场功能
 */

public class ClearBoxDialog extends Dialog implements View.OnClickListener {
    private TextView message;
    private Button btnCancle;
    private Button btnOk;
    private Context mContext;
    private String msg;

    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;

    public ClearBoxDialog(Context context) {
        super(context);
        this.mContext = context;
        msg = mContext.getString(R.string.cc_clear_cd_error);
    }

    public ClearBoxDialog(Context context, String msg) {
        super(context);
        this.mContext = context;
        this.msg = msg;
    }

    public ClearBoxDialog setConfirmClickListener(OnSweetClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
        return this;
    }

    public ClearBoxDialog setCancelClickListener(OnSweetClickListener mCancelClickListener) {
        this.mCancelClickListener = mCancelClickListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.alert_view);
        setTitle(mContext.getResources().getString(R.string.alert));
        message = (TextView) findViewById(R.id.message);
        btnCancle = (Button) findViewById(R.id.btnCancle);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
        if (!TextUtils.isEmpty(msg)) {
            message.setText(msg);
        }
        message.setTextColor(Color.RED);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                if (null != mConfirmClickListener) {
                    mConfirmClickListener.onClick(this, view);
                } else {
                    dismiss();
                }
                break;
            case R.id.btnCancle:
                if (null != mCancelClickListener) {
                    mCancelClickListener.onClick(this, view);
                } else {
                    dismiss();
                }
                break;
            default:
                break;
        }
    }

    public interface OnSweetClickListener {
        void onClick(Dialog dialog, View view);
    }
}
