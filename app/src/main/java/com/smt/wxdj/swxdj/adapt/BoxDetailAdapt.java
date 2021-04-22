package com.smt.wxdj.swxdj.adapt;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.compares.SmtCompare;
import com.smt.wxdj.swxdj.utils.BoxTool;
import com.smt.wxdj.swxdj.utils.NumTool;
import com.smtlibrary.utils.PreferenceUtils;

import java.util.Collections;
import java.util.List;

import static com.smt.wxdj.swxdj.compares.SmtCompare.TRUCK;

/**
 * Created by gbh on 16/6/15.
 */
public class BoxDetailAdapt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TITLE = 1;
    private static final int TYPE_CONTENT = 2;
    private static final int TYPE_NOTDATA = 3;//无数据显示界面
    private static final String SORT_NAME = "sortName";//排序名称
    private static final String ISSORT = "isSort";//是否排序

    private boolean mShowFooter = true;
    private Context mContext;
    private List<BoxDetalBean> mData;
    private OnItemClickListener onItemClickListener;
    private int mType;
    private boolean isSortTruck;
    private boolean isSortCntr;
    private boolean isSortLocation;
    private boolean isSortOPR;
    private boolean isSortBoxType;
    private boolean isSortWeight;
    private boolean isSortTime;

    public BoxDetailAdapt(Context context, int type) {
        this.mContext = context;
        this.mType = type;
    }


    /**
     * 等待时间转换
     */
    private String changeWaitTime(String str) {
        Long time = Long.parseLong(str);
        long day = time / (24 * 60);
        long hour = (time % (24 * 60)) / 60;
        long minute = (time % (24 * 60)) % 60;
        if (day == 0) {
            if (hour == 0) {
                return "" + minute;
            } else {
                return hour + ":" + minute;
            }
        }
        return day + " " + hour + ":" + minute;
    }

    private boolean isVVDVSL(BoxDetalBean bean) {
        boolean flag = true;
        if (null != bean) {
            if (!TextUtils.isEmpty(bean.getVVDVSL())) {
                if (!"/".equals(bean.getVVDVSL())) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * 设置数据体
     *
     * @param data
     */
    public void setData(List<BoxDetalBean> data) {
        this.mData = data;
//        boolean isSort = PreferenceUtils.getBoolean(mContext, ISSORT, false);
//        String sortName = PreferenceUtils.getString(mContext, SORT_NAME, SmtCompare.TIME);
//        if (isSort)
//            Collections.sort(mData, new SmtCompare(sortName));
//        else
//            Collections.sort(mData, Collections.reverseOrder(new SmtCompare(sortName)));
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_TITLE;
        else
            return TYPE_CONTENT;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TITLE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_title_tetail_layout, parent, false);
            ViewGroup.LayoutParams params = v.getLayoutParams();
            int length = (int) mContext.getResources().getDimension(R.dimen.recyclview_width);
            params.width = 10 * length;
            v.setLayoutParams(params);
            return new TitleView(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_content_tetail_layout, parent, false);
            ViewGroup.LayoutParams params = v.getLayoutParams();
            int length = (int) mContext.getResources().getDimension(R.dimen.recyclview_width);
            params.width = 10 * length;
            v.setLayoutParams(params);
            return new ContentView(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentView) {
            BoxDetalBean bean = mData.get(position - 1);
            if (null == bean) return;

            ((ContentView) holder).carNum.setText(String.valueOf(bean.getTrk()));
            ((ContentView) holder).boxNum.setText(String.valueOf(bean.getCntr()));
            ((ContentView) holder).boxLocation.setText(bean.getRown());
            ((ContentView) holder).boxType.setText(bean.getEqp_Type());
            ((ContentView) holder).boxWeight.setText(bean.getFe_Ind());
            ((ContentView) holder).boxOwn.setText(bean.getOpr());
            ((ContentView) holder).boxAllWeight.setText(NumTool.praseNum(bean.getGrs_Ton()));
            ((ContentView) holder).boxHmhc.setText(isVVDVSL(bean) ? "" : bean.getVVDVSL());
            ((ContentView) holder).boxName.setText("");
            ((ContentView) holder).enter_Time.setText((TextUtils.isEmpty(bean.getWaitTime()) ? "" : changeWaitTime(bean.getWaitTime())));
            ((ContentView) holder).tvCell.setText(bean.getCell());
            ((ContentView) holder).tvTier.setText(bean.getTier());
            ((ContentView) holder).workLine.setText(bean.getPortainer());
            String dt = bean.getActivity();
            if (dt.equals("PK") || dt.equals("IP")) {
                ((ContentView) holder).boxDt.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_light));
            } else {
                ((ContentView) holder).boxDt.setTextColor(mContext.getResources().getColor(android.R.color.holo_green_light));
            }
            ((ContentView) holder).boxDt.setText(BoxTool.keyReference.get(dt));
            ((ContentView) holder).tvPOD.setText(bean.getPOD());
            ((ContentView) holder).tvEqpCond.setText(bean.getEQP_COND());
        }
    }


    public BoxDetalBean getItem(int position) {
        if (null == mData) return null;
        BoxDetalBean box = mData.get(position - 1);
        return box;
    }


    public void removeObj(BoxDetalBean bean) {
        if (null != mData) {
            mData.remove(bean);
            this.notifyDataSetChanged();
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 排序
     *
     * @param view
     */
    public void sortData(View view) {
        switch (view.getId()) {
            case R.id.num:
                if (!isSortTruck) {
                    isSortTruck = true;
                    Collections.sort(mData, new SmtCompare(TRUCK));
                } else {
                    isSortTruck = false;
                    Collections.sort(mData, Collections.reverseOrder(new SmtCompare(TRUCK)));
                }
                setViewIcon(view, isSortTruck);
                PreferenceUtils.putString(mContext, SORT_NAME, SmtCompare.TRUCK);
                break;
            case R.id.boxNum:
                if (!isSortCntr) {
                    Collections.sort(mData, new SmtCompare(SmtCompare.CNTR));
                    isSortCntr = true;
                } else {
                    isSortCntr = false;
                    Collections.sort(mData, Collections.reverseOrder(new SmtCompare(SmtCompare.CNTR)));
                }
                setViewIcon(view, isSortCntr);
                PreferenceUtils.putString(mContext, SORT_NAME, SmtCompare.CNTR);
                break;
            case R.id.boxLocation:
                if (!isSortLocation) {
                    Collections.sort(mData, new SmtCompare(SmtCompare.TOROW));
                    isSortLocation = true;
                } else {
                    isSortLocation = false;
                    Collections.sort(mData, Collections.reverseOrder(new SmtCompare(SmtCompare.TOROW)));
                }
                setViewIcon(view, isSortLocation);
                PreferenceUtils.putString(mContext, SORT_NAME, SmtCompare.TOROW);
                break;
            case R.id.boxOwn:
                if (!isSortOPR) {
                    Collections.sort(mData, new SmtCompare(SmtCompare.OPR));
                    isSortOPR = true;
                } else {
                    isSortOPR = false;
                    Collections.sort(mData, Collections.reverseOrder(new SmtCompare(SmtCompare.OPR)));
                }
                setViewIcon(view, isSortOPR);
                PreferenceUtils.putString(mContext, SORT_NAME, SmtCompare.OPR);
                break;
            case R.id.boxType:
                if (!isSortBoxType) {
                    Collections.sort(mData, new SmtCompare(SmtCompare.TYPE));
                    isSortBoxType = true;
                } else {
                    isSortBoxType = false;
                    Collections.sort(mData, Collections.reverseOrder(new SmtCompare(SmtCompare.TYPE)));
                }
                setViewIcon(view, isSortBoxType);
                PreferenceUtils.putString(mContext, SORT_NAME, SmtCompare.TYPE);
                break;
            case R.id.boxALlWeight:
                if (!isSortWeight) {
                    Collections.sort(mData, new SmtCompare(SmtCompare.WEIGHT));
                    isSortWeight = true;
                } else {
                    isSortWeight = false;
                    Collections.sort(mData, Collections.reverseOrder(new SmtCompare(SmtCompare.WEIGHT)));
                }
                setViewIcon(view, isSortWeight);
                PreferenceUtils.putString(mContext, SORT_NAME, SmtCompare.WEIGHT);
                break;
            case R.id.inSwTime://入闸时间，现改为等待时间
                if (!isSortTime) {
                    Collections.sort(mData, new SmtCompare(SmtCompare.TIME));
                    isSortTime = true;
                } else {
                    isSortTime = false;
                    Collections.sort(mData, Collections.reverseOrder(new SmtCompare(SmtCompare.TIME)));
                }
                setViewIcon(view, isSortTime);
                PreferenceUtils.putString(mContext, SORT_NAME, SmtCompare.TIME);
                break;
        }
        this.notifyDataSetChanged();
    }

    private void setViewIcon(View view, boolean isSort) {
        if (view instanceof TextView) {
            TextView item = (TextView) view;
            if (isSort) {
                Drawable icon = mContext.getResources().getDrawable(R.mipmap.up_sort);
                icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
                item.setCompoundDrawables(null, null, icon, null);
            } else {
                Drawable icon = mContext.getResources().getDrawable(R.mipmap.desc_sort);
                icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
                item.setCompoundDrawables(null, null, icon, null);
            }
            PreferenceUtils.putBoolean(mContext, ISSORT, isSort);
        }
    }

    /**
     * 标题视图
     */
    public class TitleView extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView carNum, boxNum, boxType, boxWeight, tvPOD, tvEqpCond,
                boxLocation, boxDt, boxOwn, boxAllWeight, boxName, enter_Time, boxHmhc, workLine;


        public TitleView(View view) {
            super(view);
            carNum = (TextView) view.findViewById(R.id.num);
            boxNum = (TextView) view.findViewById(R.id.boxNum);
            boxType = (TextView) view.findViewById(R.id.boxType);
            boxWeight = (TextView) view.findViewById(R.id.boxWeight);
            boxLocation = (TextView) view.findViewById(R.id.boxLocation);
            boxDt = (TextView) view.findViewById(R.id.boxDt);
            boxOwn = (TextView) view.findViewById(R.id.boxOwn);
            boxAllWeight = (TextView) view.findViewById(R.id.boxALlWeight);
            boxName = (TextView) view.findViewById(R.id.boxName);
            boxHmhc = (TextView) view.findViewById(R.id.boxHmhc);
            enter_Time = (TextView) view.findViewById(R.id.inSwTime);
            workLine = (TextView) view.findViewById(R.id.workLine);
            tvPOD = (TextView) view.findViewById(R.id.tvPOD);
            tvEqpCond = (TextView) view.findViewById(R.id.tvEqpCond);
            switch (mType) {
                case 1:
                    boxHmhc.setVisibility(View.GONE);
                    break;
                case 2:
                    boxHmhc.setVisibility(View.GONE);
                    boxName.setVisibility(View.GONE);
                    break;
            }
            carNum.setOnClickListener(this);
            boxNum.setOnClickListener(this);
            boxLocation.setOnClickListener(this);
            boxOwn.setOnClickListener(this);
            boxType.setOnClickListener(this);
            boxAllWeight.setOnClickListener(this);
            enter_Time.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            sortData(view);
        }
    }


    //内容视图
    public class ContentView extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView carNum, boxNum, boxType, boxWeight, tvPOD, tvEqpCond,
                boxLocation, boxDt, boxOwn, boxAllWeight, boxName, enter_Time, boxHmhc, tvCell, tvTier, workLine;
        LinearLayout contentLayout;

        public ContentView(View view) {
            super(view);
            contentLayout = (LinearLayout) view.findViewById(R.id.contentLayout);
            carNum = (TextView) view.findViewById(R.id.num);
            boxNum = (TextView) view.findViewById(R.id.boxNum);
            boxType = (TextView) view.findViewById(R.id.boxType);
            boxWeight = (TextView) view.findViewById(R.id.boxWeight);
            boxLocation = (TextView) view.findViewById(R.id.boxLocation);
            boxDt = (TextView) view.findViewById(R.id.boxDt);
            boxOwn = (TextView) view.findViewById(R.id.boxOwn);
            boxAllWeight = (TextView) view.findViewById(R.id.boxALlWeight);
            boxName = (TextView) view.findViewById(R.id.boxName);
            enter_Time = (TextView) view.findViewById(R.id.inSwTime);
            boxHmhc = (TextView) view.findViewById(R.id.boxHmhc);
            tvCell = (TextView) view.findViewById(R.id.tvCell);
            tvTier = (TextView) view.findViewById(R.id.tvTier);
            workLine = (TextView) view.findViewById(R.id.workLine);
            tvPOD = (TextView) view.findViewById(R.id.tvPOD);
            tvEqpCond = (TextView) view.findViewById(R.id.tvEqpCond);
            view.setOnClickListener(this);
            contentLayout.setOnClickListener(this);
            switch (mType) {
                case 1:
                    boxHmhc.setVisibility(View.GONE);
                    break;
                case 2:
                    boxHmhc.setVisibility(View.GONE);
                    boxName.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            if (null != onItemClickListener)
                onItemClickListener.onItemClick(v, getPosition());
        }

        /**
         * 是否选中状态
         *
         * @param isSelect
         */
        public void setSelectState(boolean isSelect, int position) {
            int bgColor = android.R.color.holo_orange_light;
            if (position % 2 == 0) {
                if (isSelect) contentLayout.setBackgroundResource(bgColor);
                else contentLayout.setBackgroundResource(R.drawable.recy_item_press);
            } else {
                if (isSelect) contentLayout.setBackgroundResource(bgColor);
                else contentLayout.setBackgroundResource(R.drawable.recy_item_nomal);
            }
        }

    }

}
