package com.smt.wxdj.swxdj.adapt;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.StackBean;
import com.smtlibrary.irecyclerview.adapter.MultiItemRecycleViewAdapter;
import com.smtlibrary.irecyclerview.adapter.MultiItemTypeSupport;
import com.smtlibrary.irecyclerview.adapter.ViewHolderHelper;

import java.util.List;

/**
 * Created by gbh on 17/1/3.
 * 场地适配器
 */

public class StackAdapt extends MultiItemRecycleViewAdapter<StackBean> {

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_CONTENT = 1;

    public StackAdapt(Context context, List<StackBean> datas) {
        super(context, datas, new MultiItemTypeSupport<StackBean>() {
            @Override
            public int getLayoutId(int type) {
                if (type == TYPE_TITLE)
                    return R.layout.stack_alert_view_title_item;
                else
                    return R.layout.stack_alert_view_item;
            }

            @Override
            public int getItemViewType(int type, StackBean bean) {
                if (TextUtils.isEmpty(bean.getStack())) {
                    return TYPE_TITLE;
                } else
                    return TYPE_CONTENT;
            }
        });
    }


    @Override
    public void convert(ViewHolderHelper holder, StackBean bean, int i) {
        if (holder.getLayoutId() == R.layout.stack_alert_view_item) {
            LinearLayout item = holder.getView(R.id.contentLayout);
            //设置背景颜色
            if (bean.isSelect()) {
                item.setBackgroundColor(Color.YELLOW);
            } else {
                if (!TextUtils.isEmpty(bean.getSTATUS())) {
                    switch (bean.getSTATUS()) {
                        case "A":
                            item.setBackgroundColor(Color.GREEN);
                            break;
                        case "W":
                            item.setBackgroundColor(Color.RED);
                            break;
                        case "P":
                            item.setBackgroundColor(Color.BLUE);
                            break;
                    }
                }
            }
            //设置数据
            holder.setText(R.id.stack_item, bean.getStack());
            holder.setText(R.id.item_count, TextUtils.isEmpty(bean.getWorks()) ? "0" : bean.getWorks());
            holder.setText(R.id.state, bean.getSTATUS());
            holder.setText(R.id.fpBay, bean.getASSIGN_ROW());
            holder.setText(R.id.startBay, bean.getOPR_START_ROWN());
            holder.setText(R.id.endBay, bean.getOPR_END_ROWN());
            holder.setText(R.id.startTime, bean.getSTART_TIME());
            holder.setText(R.id.endTime, bean.getEND_TIME());

        }
    }
}
