package com.smt.wxdj.swxdj.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.Trk;
import com.smtlibrary.irecyclerview.IRecyclerView;
import com.smtlibrary.irecyclerview.OnRefreshListener;
import com.smtlibrary.irecyclerview.adapter.CommonRecycleViewAdapter;
import com.smtlibrary.irecyclerview.adapter.OnItemClickListener;
import com.smtlibrary.irecyclerview.adapter.ViewHolderHelper;
import com.smtlibrary.irecyclerview.animation.ScaleInAnimation;
import com.smtlibrary.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gbh on 16/11/2.
 * 场地列表
 */

public class TrkDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private Button btnCancle;
    private Button btnOk;
    private IRecyclerView myRecyclerView;
    private CommonRecycleViewAdapter<Trk> adapt;
    private List<Trk> mList;
    private Trk mTrk;

    private OnSweetClickListener mConfirmClickListener;

    public TrkDialog(Context context, List<Trk> list) {
        super(context);
        this.mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public TrkDialog setConfirmClickListener(OnSweetClickListener mConfirmClickListener) {
        this.mConfirmClickListener = mConfirmClickListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.trk_alert_view);
        int width = PreferenceUtils.getInt(mContext, "width", 400);
        int height = PreferenceUtils.getInt(mContext, "height", 400);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = width - width / 2;
        lp.height = height - height / 4;
        dialogWindow.setAttributes(lp);

        btnCancle = (Button) findViewById(R.id.btnCancle);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
        setTitle(mContext.getResources().getString(R.string.select_stack));
        myRecyclerView = (IRecyclerView) findViewById(R.id.recycle_view);

        //新添一个标题
        adapt = new CommonRecycleViewAdapter<Trk>(mContext, R.layout.trk_item_layout, mList) {
            @Override
            public void convert(ViewHolderHelper viewHolderHelper, Trk trk, int i) {
                TextView tvTrk = viewHolderHelper.getView(R.id.tvTrk);
                viewHolderHelper.setText(R.id.tvTrk, trk.getTrk());
                if (trk.isSelect()) {
                    tvTrk.setBackgroundColor(Color.YELLOW);
                } else {
                    tvTrk.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        };
        adapt.openLoadAnimation(new ScaleInAnimation());
        myRecyclerView.setAdapter(adapt);
        adapt.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                for (Trk bean : mList) {
                    bean.setSelect(false);
                }
                adapt.notifyDataSetChanged();
                mTrk = (Trk) o;
                mTrk.setSelect(true);
                adapt.notifyItemChanged(i);
            }

            @Override
            public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
                return false;
            }
        });
        myRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                myRecyclerView.setOnRefresh(false);
            }
        });
        setBefaultSelect();

    }

    /**
     * 选中的场地
     */
    private void setBefaultSelect() {
        for (Trk bean : mList) {
            if (bean.isSelect()) {
                mTrk = bean;
                break;
            }
        }
        if (mList.size() == 0) {
            btnOk.setEnabled(false);
            myRecyclerView.setImageResource(R.drawable.ic_no_data);
            myRecyclerView.setNoDataLayout(mContext.getString(R.string.get_data_failure));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                if (null != mConfirmClickListener)
                    mConfirmClickListener.onClick(this, mTrk);
                else
                    dismiss();
                break;
            case R.id.btnCancle:
                dismiss();
                break;
        }
    }

    public interface OnSweetClickListener {
        void onClick(Dialog dialog, Trk bean);
    }
}
