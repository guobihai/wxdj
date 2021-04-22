package com.smt.wxdj.swxdj.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smt.wxdj.swxdj.R;

/**
 * Created by gbh on 16/11/2.
 * 退出对话框
 */

public class IAlertDialog extends Dialog implements View.OnClickListener {
    private Button btnCancle;
    private Button btnOk;
    private Context mContext;
    private TextView message;

    private String btnOkText;
    private String btnCancleText;
    private String contentText;

    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;

    public IAlertDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public IAlertDialog setConfirmClickListener(OnSweetClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
        return this;
    }

    public IAlertDialog setCancelClickListener(OnSweetClickListener mCancelClickListener) {
        this.mCancelClickListener = mCancelClickListener;
        return this;
    }

    public IAlertDialog setComfirmBtnText(String text) {
        this.btnOkText = text;
        if (null != btnOk)
            btnOk.setText(this.btnOkText);
        return this;
    }

    public IAlertDialog setCancleBtnText(String text) {
        this.btnCancleText = text;
        if (null != btnCancle)
            btnCancle.setText(this.btnCancleText);
        return this;
    }

    public IAlertDialog setContentText(String text) {
        this.contentText = text;
        if (null != message)
            message.setText(this.contentText);
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.alert_view);
        setTitle(mContext.getResources().getString(R.string.confimToast));
        message = (TextView) findViewById(R.id.message);
        btnCancle = (Button) findViewById(R.id.btnCancle);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnCancle.setOnClickListener(this);

        setContentText(contentText);
        setComfirmBtnText(btnOkText);
        setCancleBtnText(btnCancleText);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                if (null != mConfirmClickListener) {
                    mConfirmClickListener.onClick(this);
                } else
                    dismiss();
                break;
            case R.id.btnCancle:
                if (null != mCancelClickListener)
                    mCancelClickListener.onClick(this);
                else
                    dismiss();
                break;
        }
    }

    public interface OnSweetClickListener {
        void onClick(Dialog dialog);
    }
}
