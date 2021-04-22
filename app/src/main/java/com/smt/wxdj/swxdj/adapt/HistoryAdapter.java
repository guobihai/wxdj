package com.smt.wxdj.swxdj.adapt;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smt.wxdj.swxdj.R;


public class HistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HistoryAdapter() {
        super(R.layout.module_recycle_item_history, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (!item.contains(",")) return;
        String[] temp = item.split(",");
        if (temp.length != 7) return;
        helper.setText(R.id.tvMachine, temp[0]);
        helper.setText(R.id.tvWork, temp[1]);
        helper.setText(R.id.tvWorkNo, temp[2]);
        helper.setText(R.id.tvDriver, temp[3]);
        helper.setText(R.id.tvDt, temp[4]);
        helper.setText(R.id.tvSmallBox, temp[5]);
        helper.setText(R.id.tvBigBox, temp[6]);
    }
}
