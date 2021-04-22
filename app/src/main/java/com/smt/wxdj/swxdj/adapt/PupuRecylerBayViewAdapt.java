package com.smt.wxdj.swxdj.adapt;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.Bay;

import java.util.List;

/**
 * Created by gbh on 16/6/24.
 */
public class PupuRecylerBayViewAdapt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Bay> mList;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PupuRecylerBayViewAdapt(List<Bay> list) {
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribe_popu_category_item, parent, false);
        return new TitleViewHold(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleViewHold) {
            Bay bay = mList.get(position);
            ((TitleViewHold) holder).value = bay;
            ((TitleViewHold) holder).mTitle.setText(bay.getBay());

        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public interface OnItemClickListener {
         void onItemClick(View view, Object obj);
    }

    public class TitleViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle;
        public Bay value;

        public TitleViewHold(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.text_item);
            mTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (null != onItemClickListener)
                onItemClickListener.onItemClick(view, value);
        }
    }
}
