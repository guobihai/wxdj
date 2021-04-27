package com.smt.wxdj.swxdj.adapt;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardCntrInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索箱子(mvvm)
 * Created by gbh on 16/8/9.
 *
 * @author gbh
 */
public class SearchResultAdapt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener onItemClickListener;

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_ITEM = 1;

    private List<YardCntrInfo> mData;
    private Context mContext;
    private boolean isGetBox;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SearchResultAdapt(Context context, boolean isGetBox) {
        this.mContext = context;
        this.isGetBox = isGetBox;
        mData = new ArrayList<>();
    }

    public void setData(List<YardCntrInfo> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addBean(YardCntrInfo bean) {
        mData.add(bean);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void resetSelect() {
        if (null == mData) return;
        for (YardCntrInfo bean : mData) {
            bean.setSelectedBox(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TITLE;
        } else {
            return TYPE_ITEM;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recy_title_layout, parent, false);
            return new TitleViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recy_content_layout, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            YardCntrInfo bean = mData.get(position - 1);
            ((MyViewHolder) holder).cntr.setText(bean.getCntr());
            ((MyViewHolder) holder).eqp_type.setText(bean.getEqp_Type());
            ((MyViewHolder) holder).location.setText(bean.getRown() + "-" + bean.getCell() + "-" + bean.getTier());
            ((MyViewHolder) holder).opr.setText(TextUtils.isEmpty(bean.getOpr()) ? "" : bean.getOpr());
            ((MyViewHolder) holder).tvCT.setText(bean.getCell() + "-" + bean.getTier());
            if (isGetBox) {
                ((MyViewHolder) holder).exist.setText(bean.isSWAPFLAG() ? "N" : "Y");
//                ((MyViewHolder) holder).setTextColor(bean.isSWAPFLAG());
            } else
                ((MyViewHolder) holder).exist.setText("Y");
            ((MyViewHolder) holder).setSelectState(bean.isSelectedBox(), position);
        } else if (holder instanceof TitleViewHolder && isGetBox) {
            ((TitleViewHolder) holder).exist.setText("换箱标识");
        }

    }

    /**
     * 选择
     *
     * @param position
     */
    public YardCntrInfo getItem(int position) {
        for (YardCntrInfo bean : mData) {
            bean.setSelectedBox(false);
        }
        YardCntrInfo bean = mData.get(position - 1);
        bean.setSelectedBox(true);
        notifyDataSetChanged();
        return bean;
    }

    /**
     * 监听事件
     */
    public interface OnItemClickListener {
        void onItemClick(View v, YardCntrInfo bean, int positon);
    }

    @Override
    public int getItemCount() {
        int begin = 1;
        if (mData == null || mData.size() == 0) {
            return begin;
        }
        return mData.size() + begin;
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cntr, eqp_type, location, opr, exist, tvCT;
        LinearLayout contentLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            contentLayout = (LinearLayout) itemView.findViewById(R.id.contentLayout);
            cntr = (TextView) itemView.findViewById(R.id.boxNum);
            eqp_type = (TextView) itemView.findViewById(R.id.boxType);
            location = (TextView) itemView.findViewById(R.id.boxLocation);
            opr = (TextView) itemView.findViewById(R.id.opr);
            exist = (TextView) itemView.findViewById(R.id.isExist);
            tvCT = (TextView) itemView.findViewById(R.id.tvCT);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (null != onItemClickListener)
                onItemClickListener.onItemClick(view, getItem(getPosition()), getPosition());
        }


        protected void setTextColor(String wapflag) {
            int color = wapflag.equals("0") ? android.R.color.black : android.R.color.holo_green_dark;
            cntr.setTextColor(mContext.getResources().getColor(color));
            eqp_type.setTextColor(mContext.getResources().getColor(color));
            location.setTextColor(mContext.getResources().getColor(color));
            exist.setTextColor(mContext.getResources().getColor(color));
            tvCT.setTextColor(mContext.getResources().getColor(color));
        }


        public void setSelectState(boolean isSelect, int position) {
            int bgColor = android.R.color.holo_orange_light;
            if (position % 2 == 0) {
                contentLayout.setBackgroundColor(Color.WHITE);
                if (isSelect) {
                    contentLayout.setBackgroundResource(bgColor);
                } else {
                    contentLayout.setBackgroundColor(Color.WHITE);
                }
            } else {
                if (isSelect) {
                    contentLayout.setBackgroundResource(bgColor);
                } else {
                    contentLayout.setBackgroundColor(mContext.getResources().getColor(R.color.tableview_color));
                }
            }
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView exist;

        public TitleViewHolder(View view) {
            super(view);
            exist = (TextView) view.findViewById(R.id.isExist);
        }
    }

}
