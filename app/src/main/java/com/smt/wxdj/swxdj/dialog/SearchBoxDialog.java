package com.smt.wxdj.swxdj.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smt.wxdj.swxdj.R;

/**
 * Created by gbh on 16/11/2.
 * 模糊搜索箱子
 */

public class SearchBoxDialog extends Dialog implements View.OnClickListener {
    private EditText message;
    private Button btnCancle;
    private Button btnOk;
    private Context mContext;

    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;

    public SearchBoxDialog(Context context) {
        super(context);
        this.mContext = context;
    }


    public SearchBoxDialog setConfirmClickListener(OnSweetClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
        return this;
    }

    public SearchBoxDialog setCancelClickListener(OnSweetClickListener mCancelClickListener) {
        this.mCancelClickListener = mCancelClickListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.inputbox_alert_view);
        setTitle(mContext.getResources().getString(R.string.inputBox));
        message = (EditText) findViewById(R.id.search_input);
        btnCancle = (Button) findViewById(R.id.btnCancle);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnCancle.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                if (null != mConfirmClickListener) {
                    String value = message.getText().toString();
                    if (value.length() < 4) {
                        message.setError(mContext.getResources().getString(R.string.input_err_toast));
                        return;
                    }
                    mConfirmClickListener.onClick(this, value);
                } else
                    dismiss();
                break;
            case R.id.btnCancle:
                if (null != mCancelClickListener)
                    mCancelClickListener.onClick(this, message.getText().toString());
                else
                    dismiss();
                break;
        }
    }

    public interface OnSweetClickListener {
        void onClick(Dialog dialog, String value);
    }
}
