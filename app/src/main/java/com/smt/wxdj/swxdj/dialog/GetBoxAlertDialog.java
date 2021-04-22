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
import com.smt.wxdj.swxdj.bean.BoxDetalBean;

/**
 * Created by gbh on 16/11/2.
 * 提箱对话框
 */

public class GetBoxAlertDialog extends Dialog implements View.OnClickListener {
    private TextView message;
    private Button btnCancle;
    private Button btnOk;
    private BoxDetalBean bean;
    private Context mContext;

    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;

    public GetBoxAlertDialog(Context context, BoxDetalBean bean) {
        super(context);
        this.mContext = context;
        this.bean = bean;
    }


    public GetBoxAlertDialog setConfirmClickListener(OnSweetClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
        return this;
    }

    public GetBoxAlertDialog setCancelClickListener(OnSweetClickListener mCancelClickListener) {
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


        if (null == bean) return;
        String msg = mContext.getString(R.string.make_sure_you_want_the_box) + bean.getCntr() + mContext.getString(R.string.take_it_away);
        message.setText(msg);
        SpannableStringBuilder builder = new SpannableStringBuilder(message.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, 7, bean.getCntr().length() + 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        message.setText(builder);
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
        void onClick(Dialog getBoxAlertDialog, View view);
    }
}
