package com.smt.wxdj.swxdj.adapt;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smt.wxdj.swxdj.BR;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.Dock;
import com.smt.wxdj.swxdj.dao.HostSettingInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置数据适配器(mvvm)
 * Created by gbh on 16/8/9.
 *
 * @author gbh
 */
public class HostSettingAdapt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private boolean mShowFooter = true;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NOTHING = 2;

    private List<HostSettingInfo> mData;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HostSettingAdapt() {
        mData = new ArrayList<>();
    }

    public void setData(List<HostSettingInfo> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }


    public void clearData() {
        if (null != mData)
            mData.clear();
        notifyDataSetChanged();
    }




    protected HostSettingInfo getItem(int positon) {
        return mData.get(positon);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.setting_host_item_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myHolder = (MyViewHolder) holder;
            mData.get(position).setIndex(position);
            myHolder.getBinding().setVariable(BR.dock,mData.get(position));
            myHolder.getBinding().executePendingBindings();
        }

    }

    /**
     * 选中状态
     *
     * @param position
     */
    public void setSelect(int position) {
        for (HostSettingInfo bean : mData)
            bean.setSelect(false);
        mData.get(position).setSelect(true);
        notifyDataSetChanged();
    }

    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    /**
     * 监听事件
     */
    public interface OnItemClickListener {
        void onItemClick(View v, HostSettingInfo taskBean, int positon);
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewDataBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        @Override
        public void onClick(View view) {
            if (null != onItemClickListener)
                onItemClickListener.onItemClick(view, getItem(getPosition()), getPosition());
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

}
