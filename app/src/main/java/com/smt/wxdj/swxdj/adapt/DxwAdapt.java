package com.smt.wxdj.swxdj.adapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.compares.SmtCompare;
import com.smt.wxdj.swxdj.enums.ColorType;
import com.smt.wxdj.swxdj.utils.BoxTool;
import com.smt.wxdj.swxdj.utils.CellTool;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardCntrInfo;
import com.smtlibrary.utils.JsonUtils;
import com.smtlibrary.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by gbh on 16/6/23.
 * 倒箱位适配器
 */
public class DxwAdapt extends DragAdapter {

    private int showDxwNameNo;

    public DxwAdapt(Context context) {
        super(context);
    }

    @Override
    public YardCntrInfo getItem(int position) {
        // TODO Auto-generated method stub
        if (channelList != null && channelList.size() != 0) {
            return channelList.get(position);
        }
        return null;
    }

    @Override
    public void setListDate(List<YardCntrInfo> list) {
        channelList = list;
        sortDxw();
    }

    public void sortDxw() {
        List<YardCntrInfo> tList = new ArrayList<>();
        for (YardCntrInfo bean : channelList) {
            if (bean.HashBox())
                tList.add(bean);
        }
        Collections.sort(tList, new SmtCompare(SmtCompare.TIER));
        //判断如果没有到想位的时候
        if (tList.size() == 0) {
            for (int i = 0; i < channelList.size(); i++) {
                if (i == channelList.size() - 1)
                    channelList.get(i).setShowDxw(true);
                else
                    channelList.get(i).setShowDxw(false);
            }
        } else {//到箱位有数据
            int boxIndex = getCount() - 1;
            //获取最后一个排序的箱子层数
            showDxwNameNo = Integer.parseInt(tList.get(tList.size() - 1).getTier());
            int index = boxIndex - showDxwNameNo;
//            LogUtils.sysout("＝＝＝＝index＝＝＝getTier＝＝＝＝＝＝===", index);
            if (index < 0) {
                for (int i = 0; i < channelList.size(); i++) {
                    if (i == 0)
                        channelList.get(i).setShowDxw(true);
                    else
                        channelList.get(i).setShowDxw(false);
                }
            } else {
                for (int i = 0; i < channelList.size(); i++) {
                    if (i == index)
                        channelList.get(i).setShowDxw(true);
                    else
                        channelList.get(i).setShowDxw(false);
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 判断是否当前位置是否是箱子
     *
     * @param position
     * @return
     */
    public boolean HashBox(int position) {
        return channelList.get(position).HashBox();
    }

    public boolean isSelect(int position) {
        return channelList.get(position).isShowDxw();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHoldView holdView = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribe_dxw_category_item, null);
            holdView = new MyHoldView();
            holdView.layout = (RelativeLayout) convertView.findViewById(R.id.rl_subscribe);
            holdView.dxw = (TextView) convertView.findViewById(R.id.dxw);
            holdView.title = (TextView) convertView.findViewById(R.id.text_item);
            holdView.detail = (TextView) convertView.findViewById(R.id.detail);
            convertView.setTag(holdView);
        } else {
            holdView = (MyHoldView) convertView.getTag();
        }
        YardCntrInfo bean = getItem(position);
        if (bean.HashBox()) {
            holdView.dxw.setVisibility(View.GONE);
            holdView.layout.setVisibility(View.VISIBLE);
            holdView.title.setText(bean.getCntr());
            holdView.detail.setText(bean.getContent());
        } else {
            holdView.dxw.setVisibility(View.VISIBLE);
            holdView.layout.setVisibility(View.GONE);
            if (bean.isShowDxw()) {
                holdView.dxw.setText(R.string.dxw_name);
                if (null != bean.getRecommendColorType()) {
                    holdView.dxw.setBackgroundResource(bean.getRecommendColorType().getColor());
                    holdView.dxw.setTextColor(Color.WHITE);
                }
            } else {
                holdView.dxw.setText("");
                holdView.dxw.setBackgroundResource(R.drawable.shapee_dxw);
            }
        }

        if (bean.isSelectedBox()) {
            holdView.layout.setBackgroundResource(R.drawable.shapee_select);
        } else {
            holdView.layout.setBackgroundColor(parent.getContext().getResources().getColor(R.color.dxw_bg_color));
        }

        return convertView;
    }

    @Override
    public int exchange(YardCntrInfo dragItem, int dropPostion, int col) {
        String cellY;
        int res = CellTool.BOX_TYPE;
        //计算出列数
        int row = channelList.size();
        //遍历判断位置是否有箱子存在
        for (int i = row; i > 0; i--) {
            int index = i - 1;
            YardCntrInfo dropBean = channelList.get(index);
            if (!dropBean.HashBox()) {
                LogUtils.sysout("dxw bean=2=", JsonUtils.serialize(dragItem));
                dragItem.setSelectedBox(false);
                dragItem.setMoveSelect(false);
                cellY = String.valueOf(row - index);
                dragItem.setCell("0");
                dragItem.setCell("0");
                dragItem.setTier(cellY);
                dragItem.setTier(cellY);
                dragItem.setHashBox(true);
                channelList.set(index, dragItem);
                res = CellTool.BOX_TYPE_SUCESS;
                //赋值，直接退出循环
                break;
            }
        }
        sortDxw();
        return res;
    }

    /**
     * 获取倒箱位Y坐标
     *
     * @param dragItem
     * @param dropPostion
     * @param col
     * @return
     */
    public int getCellY(YardCntrInfo dragItem, int dropPostion, int col) {
        int cellY = 0;
        //计算出列数
        int row = channelList.size();
        //遍历判断位置是否有箱子存在 4  3  2  1
        for (int i = row; i > 0; i--) {
            int index = i - 1;
            YardCntrInfo dropBean = channelList.get(index);
            if (!dropBean.HashBox()) {
                cellY = row - index;
                //赋值，直接退出循环
                break;
            }
        }
        return cellY;
    }

    public void setBay(Bay bay) {
        this.mBay = bay;
    }

    public void setPutBox(YardCntrInfo putBox) {
        this.mPutBox = putBox;
    }

    /**
     * 放箱推荐位置
     *
     * @param col  列数
     * @param row  层数
     * @param bay  呗位
     * @param maps 箱子
     */
    public void findRecommentdLocation(int col, int row, Bay bay, YardCntrInfo putBox, Map<String, YardCntrInfo> maps) {
        if (null == maps || null == putBox || null == bay) return;
        //一层一层的遍历
        for (int cell = 0; cell < col; cell++) {
            for (int tier = 1; tier <= row; tier++) {
                String key = String.format("(%s,%s)", cell, tier);
                YardCntrInfo bean = maps.get(key);
                //判断位置是否有箱子
                if (null == bean) {//说明该位置可允许放箱
                    if (tier == 1) {
                        setRecommentLocation(key, ColorType.GREEN);
                    } else {
                        int lastTier = tier - 1;
                        String newkey = String.format("(%s,%s)", cell, lastTier);
                        bean = maps.get(newkey);
                        if (null != bean) {
                            //判断要压的箱子是什么类型箱子
                            if ((bean.getFe_Ind().equals(BoxTool.F) && (bean.getEqp_Type().equals(putBox.getEqp_Type()) || putBox.getEqp_Type().startsWith(bay.getCur_Size_Limit()))) ||
                                    (putBox.getFe_Ind().equals(BoxTool.E) && (bean.getEqp_Type().equals(putBox.getEqp_Type()) || putBox.getEqp_Type().startsWith(bay.getCur_Size_Limit())))) {
                                setRecommentLocation(key, ColorType.GREEN);
                            } else {
                                setRecommentLocation(key, ColorType.GRAY);
                            }

                        }
                    }
                    break;
                }
            }

        }

        notifyDataSetChanged();
    }

}
