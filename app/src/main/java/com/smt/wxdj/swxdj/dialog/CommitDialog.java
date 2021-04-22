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

public class CommitDialog extends Dialog implements View.OnClickListener {
    private TextView message;
    private Button btnCancle;
    private Button btnOk;
    private Context mContext;
    private String msg;
    private String cntr;
    private String mTwName;
    private int cellX;
    private int cellY;

    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;

    public CommitDialog(Context context, String msg,String cntr, int cellX,  int cellY,String twName) {
        super(context);
        this.mContext = context;
        this.msg = msg;
        this.cntr = cntr;
        this.cellX = cellX;
        this.cellY = cellY;
        this.mTwName = twName;
    }


    public CommitDialog setConfirmClickListener(OnSweetClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
        return this;
    }

    public CommitDialog setCancelClickListener(OnSweetClickListener mCancelClickListener) {
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

        message.setText(msg);
//        SpannableStringBuilder builder = new SpannableStringBuilder(message.getText().toString());
//        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
//        ForegroundColorSpan redSpan1 = new ForegroundColorSpan(Color.RED);
//        builder.setSpan(redSpan, 6, cntr.length() + 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(redSpan1, cntr.length() + 11, cntr.length() + 11 + String.valueOf(cellX).length() +
//                        String.valueOf(cellY).length() + 2 + mTwName.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        message.setText(builder);

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
