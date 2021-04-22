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
 * 箱子放置倒箱位提示确认框
 */

public class CommitDxwDialog extends Dialog implements View.OnClickListener {
    private TextView message;
    private Button btnCancle;
    private Button btnOk;
    private Context mContext;
    private String cntr;

    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;

    public CommitDxwDialog(Context context, String cntr) {
        super(context);
        this.mContext = context;
        this.cntr = cntr;
    }


    public CommitDxwDialog setConfirmClickListener(OnSweetClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
        return this;
    }

    public CommitDxwDialog setCancelClickListener(OnSweetClickListener mCancelClickListener) {
        this.mCancelClickListener = mCancelClickListener;
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


        String msg = mContext.getString(R.string.make_sure_you_want_the_box) + cntr + mContext.getString(R.string.move_to_the_reverse_bin);
        message.setText(msg);
        SpannableStringBuilder builder = new SpannableStringBuilder(message.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, 7, cntr.length() + 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        void onClick(Dialog dialog, View view);
    }
}
