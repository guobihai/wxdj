package com.smt.wxdj.swxdj.adapt;

import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.enums.ColorType;
import com.smt.wxdj.swxdj.utils.BoxTool;
import com.smt.wxdj.swxdj.utils.CellTool;
import com.smt.wxdj.swxdj.utils.FileKeyName;
import com.smt.wxdj.swxdj.utils.LruchUtils;
import com.smt.wxdj.swxdj.utils.ScreenUtils;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardCntrInfo;

import java.util.List;
import java.util.Map;

/**
 * 处理某个田位的页面处理
 */
public class DragAdapter extends BaseAdapter {

    protected Bay mBay;//当前贝位
    protected YardCntrInfo mPutBox;//要放的箱子
    protected YardCntrInfo mGetBox;//提的箱子

    /**
     * 震动器
     */
    private Vibrator mVibrator;
    /**
     * 可以拖动的列表（即用户选择的频道列表）
     */
    protected List<YardCntrInfo> channelList;

    private Context mContext;
    private boolean isShowRecommend; //是否显示推荐位
    private boolean isShowInOrOut; //是否进出口背景颜色

    public DragAdapter(Context context) {
        mContext = context;
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        isShowRecommend = LruchUtils.isSwitch(FileKeyName.rm_color_switch);
        isShowInOrOut = LruchUtils.isSwitch(FileKeyName.ShowCustomBg);
    }

    public DragAdapter(Context context, YardCntrInfo bean) {
        mContext = context;
        this.mGetBox = bean;
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        isShowRecommend = LruchUtils.isSwitch(FileKeyName.rm_color_switch);
        isShowInOrOut = LruchUtils.isSwitch(FileKeyName.ShowCustomBg);
    }


    @Override
    public int getCount() {
        return channelList == null ? 0 : channelList.size();
    }

    @Override
    public YardCntrInfo getItem(int position) {
        if (channelList == null && channelList.size() <= 0) return null;
        return channelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHoldView holdView = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribe_category_item, parent, false);
            String hw = ScreenUtils.getScreenHeight2Width(mContext);
//            if ("1136*1920".equals(hw)) {
//                ViewGroup.LayoutParams params = convertView.getLayoutParams();
//                params.width = 200;
//                params.height = 150;
//                convertView.setLayoutParams(params);
//            }
            holdView = new MyHoldView();
            holdView.layout = convertView.findViewById(R.id.rl_subscribe);
            holdView.title = convertView.findViewById(R.id.text_item);
            holdView.detail = convertView.findViewById(R.id.detail);
            holdView.tvAction = convertView.findViewById(R.id.tvAction);
            holdView.tvActionRm = convertView.findViewById(R.id.tvActionRm);
            holdView.tvOccupy = convertView.findViewById(R.id.tvOccupy);
            holdView.tvActionWaiting = convertView.findViewById(R.id.tvActionWaiting);
            convertView.setTag(holdView);
        } else {
            holdView = (MyHoldView) convertView.getTag();
        }
        YardCntrInfo bean = getItem(position);
        if (bean.HashBox()) {
            holdView.title.setText(bean.getCntr());
            holdView.detail.setText(bean.getContent());
        } else {
            holdView.title.setText("");
            holdView.detail.setText("");
        }

        int backgroundResource = R.drawable.shapee_item;
        int textColor = Color.BLACK;

        //判断是否相同提箱任务（搜索箱子）
//        if (bean.isChange()) {
//            holdView.title.setTextColor(parent.getResources().getColor(android.R.color.holo_blue_dark));
//            holdView.detail.setTextColor(parent.getResources().getColor(android.R.color.holo_blue_dark));
//            holdView.tvAction.setVisibility(View.VISIBLE);
//            holdView.tvActionRm.setVisibility(View.GONE);
//        } else {
//            holdView.tvAction.setVisibility(View.GONE);
//        }

        if (bean.isSelectedBox()) {//选中要移动的箱子默认颜色
//            holdView.layout.setBackgroundResource(R.drawable.shapee_select);
            backgroundResource = R.drawable.shapee_select;
            if (null != bean.getTextColorType()) {
//                holdView.title.setTextColor(parent.getResources().getColor(bean.getTextColorType().getColor()));
//                holdView.detail.setTextColor(parent.getResources().getColor(bean.getTextColorType().getColor()));
//                holdView.tvAction.setVisibility(View.GONE);
//                holdView.tvActionRm.setVisibility(View.GONE);
                textColor = parent.getResources().getColor(bean.getTextColorType().getColor());
            }
        } else if (bean.isMoveSelect()) {//已选中箱子，要移动到位置的颜色
//            holdView.layout.setBackgroundResource(R.drawable.shapee_move);
            backgroundResource = R.drawable.shapee_move;
        } else if (bean.isPutBoxDt()) {//放箱箱子状态下，箱子背景颜色
            backgroundResource = null == bean.getRecommendColorType() ? R.drawable.shapee_item : bean.getRecommendColorType().getColor();
//            holdView.layout.setBackgroundResource(null == bean.getRecommendColorType() ? R.drawable.shapee_item : bean.getRecommendColorType().getColor());
        } else if (bean.isGetBoxDt()) {//提箱子状态下，箱子背景颜色
            //字体颜色
            if (null != bean.getTextColorType()) {
                textColor = parent.getResources().getColor(bean.getTextColorType().getColor());
//                holdView.title.setTextColor(parent.getResources().getColor(bean.getTextColorType().getColor()));
//                holdView.detail.setTextColor(parent.getResources().getColor(bean.getTextColorType().getColor()));
                //判断是否交换箱
//                if (bean.isChange()) {
//                    if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
//                        //目标箱子不显示
//                        holdView.tvAction.setVisibility(View.GONE);
//                    } else {
//                        holdView.tvAction.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    holdView.tvAction.setVisibility(View.GONE);
//                }
//                holdView.tvActionRm.setVisibility(View.GONE);
//                //判断是否有预约，如果有显示车的标志
//                if (bean.isWaiting()) {
//                    if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
//                        //目标箱子不显示
//                        holdView.tvActionWaiting.setVisibility(View.GONE);
//                    } else {
//                        holdView.tvActionWaiting.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    holdView.tvActionWaiting.setVisibility(View.GONE);
//                }
//                //20181229 判断是否待作业箱子，且不是目标箱子，如果是字体颜色为原谅色
//                if (!TextUtils.isEmpty(bean.getCy_Rsv()) || !TextUtils.isEmpty(bean.getLd_Rsv())) {
//                    if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
//                    } else {
//                        holdView.title.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
//                        holdView.detail.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
//                    }
//                }
            } else if (!TextUtils.isEmpty(bean.getCntr())) {
//                //判断是否有预约，如果有显示车的标志
//                if (bean.isWaiting()) {
//                    if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
//                        //目标箱子不显示
//                        holdView.tvActionWaiting.setVisibility(View.GONE);
//                    } else {
//                        holdView.tvActionWaiting.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    holdView.tvActionWaiting.setVisibility(View.GONE);
//                }
//                //20181229 判断是否待作业箱子，且不是目标箱子，如果是字体颜色为原谅色
//                if (!TextUtils.isEmpty(bean.getCy_Rsv()) || !TextUtils.isEmpty(bean.getLd_Rsv())) {
//                    if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
//                    } else {
//                        holdView.title.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
//                        holdView.detail.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
//                    }
//                }
            }
            //背景色
            backgroundResource = null == bean.getColorType() ? R.drawable.shapee_item : bean.getColorType().getColor();
//            holdView.layout.setBackgroundResource(null == bean.getColorType() ? R.drawable.shapee_item : bean.getColorType().getColor());
            //推荐颜色
            if (null != bean.getRecommendColorType() && isShowRecommend) {
//                holdView.layout.setBackgroundResource(bean.getRecommendColorType().getColor());
                backgroundResource = bean.getRecommendColorType().getColor();
            }
        } else {//默认颜色
//            if (bean.isWaiting()) {
//                if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
//                    //目标箱子不显示
//                    holdView.tvActionWaiting.setVisibility(View.GONE);
//                } else {
//                    holdView.tvActionWaiting.setVisibility(View.VISIBLE);
//                }
//            } else {
//                holdView.tvActionWaiting.setVisibility(View.GONE);
//            }
//            //20181229 判断是否待作业箱子，且不是目标箱子，如果是字体颜色为原谅色
//            if (!TextUtils.isEmpty(bean.getCy_Rsv()) || !TextUtils.isEmpty(bean.getLd_Rsv())) {
//                if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
//                } else {
//                    holdView.title.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
//                    holdView.detail.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
//                }
//            }
            //推荐颜色
//            backgroundResource = null == bean.getRecommendColorType() ? R.drawable.shapee_item : bean.getRecommendColorType().getColor();
//            holdView.layout.setBackgroundResource(null == bean.getRecommendColorType() ? R.drawable.shapee_item : bean.getRecommendColorType().getColor());
        }
        //是否要倒箱
        if (null != mGetBox && !mGetBox.getCntr().equals(bean.getCntr())) {
            holdView.tvActionRm.setVisibility(bean.isRmCntr() ? View.VISIBLE : View.GONE);
        } else {
            holdView.tvActionRm.setVisibility(View.GONE);
        }

        //判断是否相同提箱任务（搜索箱子）
        if (bean.isChange()) {
            if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
                //目标箱子不显示
            } else {
                textColor = parent.getResources().getColor(android.R.color.holo_blue_dark);
//                holdView.title.setTextColor(parent.getResources().getColor(android.R.color.holo_blue_dark));
//                holdView.detail.setTextColor(parent.getResources().getColor(android.R.color.holo_blue_dark));
                holdView.tvAction.setVisibility(View.VISIBLE);
            }
        } else {
            holdView.tvAction.setVisibility(View.GONE);
        }

        //判断是否交换箱
        if (bean.isChange()) {
            if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
                //目标箱子不显示
                holdView.tvAction.setVisibility(View.GONE);
            } else {
                holdView.tvAction.setVisibility(View.VISIBLE);
            }
        } else {
            holdView.tvAction.setVisibility(View.GONE);
        }

        //大小贝,箱子颜色为灰色，提示该箱子已被占用
        if (!TextUtils.isEmpty(bean.getRown())) {
            if (null != mBay) {
                if (!mBay.getBay().equals(bean.getRown())) {
                    holdView.layout.setBackgroundResource(R.drawable.shapee_gray);
                    holdView.title.setVisibility(View.GONE);
                    holdView.detail.setVisibility(View.GONE);
                    holdView.tvAction.setVisibility(View.GONE);
                    holdView.tvActionRm.setVisibility(View.GONE);
                    holdView.tvOccupy.setVisibility(View.VISIBLE);
                }
            }
        }

        //判断是否有预约，如果有显示车的标志
        if (bean.isWaiting()) {
            if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
                //目标箱子不显示
                holdView.tvActionWaiting.setVisibility(View.GONE);
            } else {
                holdView.tvActionWaiting.setVisibility(View.VISIBLE);
            }
        } else {
            holdView.tvActionWaiting.setVisibility(View.GONE);
        }

        //20181229 判断是否待作业箱子，且不是目标箱子，如果是字体颜色为原谅色
        if (!TextUtils.isEmpty(bean.getCy_Rsv()) || !TextUtils.isEmpty(bean.getLd_Rsv())) {
            if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
            } else {
                textColor = parent.getResources().getColor(android.R.color.holo_green_dark);
//                holdView.title.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
//                holdView.detail.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
            }
        }

        //推荐颜色
//        if (null != bean.getRecommendColorType()) {
//            backgroundResource = bean.getRecommendColorType().getColor();
//        } else {
//            backgroundResource = R.drawable.shapee_item;
//        }
        //
        if (!bean.isSelectedBox() && isShowInOrOut) {
            switch (bean.getBoatState()) {
                case CellTool.IN_DOCKING:
                    if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
                    } else {
                        backgroundResource = R.color.in_docking;
                    }
//                holdView.title.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
//                holdView.detail.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
                    break;
                case CellTool.IN_NOT_DOCKING:
                    if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
                    } else {
                        backgroundResource = R.color.in_not_docking;
                    }
//                holdView.title.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
//                holdView.detail.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
                    break;
                case CellTool.OUT_DOCKING:
                    if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
                    } else {
                        backgroundResource = R.color.out_docking;
                    }
//                holdView.title.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
//                holdView.detail.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
                    break;
                case CellTool.OUT_NOT_DOCKING:
                    if (null != mGetBox && mGetBox.getCntr().equals(bean.getCntr())) {
                    } else {
                        backgroundResource = R.color.out_not_docking;
                    }
//                holdView.title.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
//                holdView.detail.setTextColor(parent.getResources().getColor(android.R.color.holo_green_dark));
                    break;
                default:
                    break;
            }
        }
        holdView.title.setTextColor(textColor);
        holdView.detail.setTextColor(textColor);
        holdView.layout.setBackgroundResource(backgroundResource);

        return convertView;
    }

    class MyHoldView {
        RelativeLayout layout;
        TextView dxw;
        TextView title;
        TextView detail;
        /**
         * 换箱标志
         */
        TextView tvAction;
        /**
         * 倒车标志
         */
        TextView tvActionRm;
        /**
         * 已被占用
         */
        TextView tvOccupy;
        /**
         * 预约车
         */
        TextView tvActionWaiting;
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


    /**
     * 判断是否悬空选择位置
     *
     * @param col
     * @param position
     * @param selectBean 已选择的箱子
     * @return true 悬空位置, false  非悬空位置
     */
    public boolean isNullLocation(int col, int row, int position, YardCntrInfo selectBean, boolean isChaoChang) {
        CellTool.getCell(position, col, row);
        int cell = CellTool.getCell();
        int tier = CellTool.getTier();
        if (tier == 1) return false;
        //判断是否同一排
        if (null != selectBean) {
            int selectCell = Integer.parseInt(selectBean.getCell());
            int selectTier = Integer.parseInt(selectBean.getTier());
            if (selectCell == cell && tier > selectTier && selectTier != 0) {
                if (isChaoChang) {//if(!isChaoChang){
                    mVibrator.vibrate(500);
                    return true;
                }
            }
        }
        tier = tier - 1;//获取压着位置是否为空
        String key = String.format("(%s,%s)", cell, tier);
        YardCntrInfo bean = null;
        for (YardCntrInfo detalBean : channelList) {
            if (detalBean.getDefaultCell().equals(key)) {
                bean = detalBean;
                break;
            }
        }

        if (null != bean && !bean.HashBox()) {
            mVibrator.vibrate(500);
            return true;
        }
        return false;
    }

    /**
     * 判断提箱是否被压着
     *
     * @param YardCntrInfo
     * @return
     */
    public boolean AboveCntr(YardCntrInfo YardCntrInfo) {
        int tier = Integer.parseInt(YardCntrInfo.getTier());
        String key = String.format("(%s,%s)", YardCntrInfo.getCell(), (tier + 1));
        YardCntrInfo bean = null;
        for (YardCntrInfo detalBean : channelList) {
            if (detalBean.getDefaultCell().equals(key)) {
                bean = detalBean;
                break;
            }
        }
        if (null != bean && bean.HashBox()) {
            mVibrator.vibrate(500);
            return true;
        }
        return false;
    }

    /**
     * 获取推荐位是否能放箱
     *
     * @param position
     * @return true 能放,false 不能放
     */
    public boolean getRecomLocation(int position) {
        YardCntrInfo bean = channelList.get(position);
        //原判断  if (bean.getRecommendColorType() == null || bean.getRecommendColorType() == ColorType.GRAY) {
        //现改为如下
        if (bean.getRecommendColorType() != null && bean.getRecommendColorType() == ColorType.GRAY) {
            mVibrator.vibrate(500);
            return false;
        }
        return true;
    }

    /**
     * 清除选中的箱子
     */
    public void resetSelectBox() {
        for (YardCntrInfo bean : channelList)
            bean.setSelectedBox(false);
    }

    /**
     * 设置选中的位置
     *
     * @param col      排
     * @param position
     */
    public int exchangeSelect(int col, int position) {
        int res = CellTool.isSelectLegal(position, col, channelList);
        if (res != CellTool.BOX_TYPE_SUCESS) {
            mVibrator.vibrate(500);
            return res;
        }
        return exchangeSelect(position);
    }

    /**
     * 选中位置
     *
     * @param position
     * @return
     */
    public int exchangeSelect(int position) {
        int res = CellTool.BOX_TYPE;
        for (int i = 0; i < channelList.size(); i++) {
            YardCntrInfo bean = channelList.get(i);
            if (i == position && bean.HashBox()) {
                bean.setSelectedBox(true);
                res = CellTool.BOX_TYPE_SUCESS;
            } else {
                bean.setSelectedBox(false);
                bean.setMoveSelect(false);
            }
        }
        this.notifyDataSetChanged();
        return res;
    }

    /**
     * 抄场,清场
     *
     * @return 0
     */
    public int resetDefault() {
        for (YardCntrInfo bean : channelList) {
            bean.setHashBox(false);
            bean.setSelectedBox(false);
            bean.setMoveSelect(false);
        }
        this.notifyDataSetChanged();
        return 0;
    }

    /**
     * 判断抄场选中位置合法性
     *
     * @param position
     * @param col
     * @return
     */
    public int checkClearBox(int position, int col) {
        return CellTool.isLegal(position, col, channelList);
    }


    /**
     * 选中位置
     *
     * @param position
     * @return
     */
    public int exchangeMoveSelect(int position) {
        for (int i = 0; i < channelList.size(); i++) {
            YardCntrInfo bean = channelList.get(i);
            if (i == position && !bean.HashBox()) {
                bean.setMoveSelect(true);
            } else {
                bean.setMoveSelect(false);
            }
        }
        this.notifyDataSetChanged();
        return CellTool.BOX_TYPE_SUCESS;
    }

    /**
     * 判断要放的箱子是否能够重箱压空箱
     *
     * @param putBox         要放的箱子
     * @param selectPosition 当前选中的位置
     * @return true 满足，可以放，false，不满足，提示是否继续放置箱子
     */
    public boolean hashPutWgtBox(int col, YardCntrInfo putBox, int selectPosition) {
        //1.要放的箱子，如果是空箱，直接返回true
        if (TextUtils.isEmpty(putBox.getFeInd()) || putBox.getFeInd().equals(BoxTool.E))
            return true;
        //计算出列数
        int row = channelList.size() / col;
        int y = CellTool.getCellY(selectPosition, col, row);
        //如果选中的位置是最底下的位置，直接返回true
        if (y == 1) return true;
        else {
            //否则判断，底下的箱子是什么类型的箱子，如果为重箱，直接返回true
            //获得底下的箱子
            YardCntrInfo bottomBox = channelList.get(selectPosition + col);
            //如果底下没有箱子，直接返回true
            if (!bottomBox.HashBox()) return true;
            //如果底下的箱子是重箱，直接返回true
            if (TextUtils.isEmpty(putBox.getFeInd()) || bottomBox.getFeInd().equalsIgnoreCase(BoxTool.F))
                return true;
        }
        return false;
    }

    /**
     * 拖动变更频道排序
     */
    public void exchange(int dragPostion, int dropPostion) {
        if (dropPostion == -1) return;
        //获取开始选中的item
        YardCntrInfo dragItem = getItem(dragPostion);
        //要替换的位置item
        YardCntrInfo dropItem = getItem(dropPostion);
        //位置替换
        channelList.set(dropPostion, dragItem);
        channelList.set(dragPostion, dropItem);
        exchangeSelect(-1);
    }

    /**
     * 拖动变更频道排序
     *
     * @param dragItem    箱子對象
     * @param dropPostion 要移动的箱子位置
     * @param col         排
     */
    public int exchange(YardCntrInfo dragItem, int dropPostion, int col) {
        if (dropPostion == -1) return CellTool.BOX_TYPE_EA;
        int row = channelList.size() / col;
        String defaultCell = CellTool.getCell(dropPostion, col, row);
        //获取开始选中的item
        //坐标替换
        dragItem.setDefaultCell(defaultCell);
        dragItem.setCell(String.valueOf(CellTool.getCell()));
        dragItem.setCell(String.valueOf(CellTool.getCell()));
        dragItem.setTier(String.valueOf(CellTool.getTier()));
        dragItem.setTier(String.valueOf(CellTool.getTier()));
        dragItem.setHashBox(true);
        //位置替换
        channelList.set(dropPostion, dragItem);
        exchangeSelect(-1);
        return CellTool.BOX_TYPE_SUCESS;
    }

    /**
     * 更换位置
     *
     * @param dragPostion //选中的坐标
     * @param dropPostion //更换的坐标
     * @param col
     * @return
     */
    public int exchange(int dragPostion, int dropPostion, int col) {
        int res = CellTool.isLegal(dragPostion, dropPostion, col, channelList);
        if (res != 0) {
            mVibrator.vibrate(500);//设置震动时间
            return res;
        }
        if (dropPostion == -1 || dragPostion < 0) return CellTool.BOX_TYPE_EA;
        //获取开始选中的item
        YardCntrInfo dragItem = getItem(dragPostion);
        YardCntrInfo dropItem = getItem(dropPostion);
        //坐标替换
        String dragCell = dragItem.getDefaultCell();
        String dropCell = dropItem.getDefaultCell();

        dragItem.setDefaultCell(dropCell);

        dropItem.setDefaultCell(dragCell);
        dragItem.setCell(String.valueOf(CellTool.getCell()));
        dragItem.setCell(String.valueOf(CellTool.getCell()));
        dragItem.setTier(String.valueOf(CellTool.getTier()));
        dragItem.setTier(String.valueOf(CellTool.getTier()));

        dropItem.setCell(String.valueOf(CellTool.getCell()));
        dropItem.setCell(String.valueOf(CellTool.getCell()));
        dropItem.setTier(String.valueOf(CellTool.getTier()));
        dropItem.setTier(String.valueOf(CellTool.getTier()));

        //位置替换
        channelList.set(dropPostion, dragItem);
        channelList.set(dragPostion, dropItem);
        exchangeSelect(-1);
        return CellTool.BOX_TYPE_SUCESS;
    }


    /**
     * 提箱更新界面,恢复成空位
     *
     * @param dragItem
     */
    public void exchangeGetBox(YardCntrInfo dragItem) {
        for (YardCntrInfo item : channelList) {
            if (null != item.getCntr() && item.getCntr().equals(dragItem.getCntr())) {
                item.setHashBox(false);
                item.setBoxDt(BoxTool.CTRL_PUTBOX);
                break;
            }
        }
        exchangeSelect(-1);
    }

    /**
     * 提箱更新界面,恢复成空位
     *
     * @param dragItem
     */
    public void exchangeCheckGetBox(YardCntrInfo dragItem) {
        for (YardCntrInfo item : channelList) {
            if (null != item.getCntr() && item.getCntr().equals(dragItem.getCntr())) {
                item.setBoxDt(BoxTool.CTRL_PUTBOX);
                break;
            }
        }
        exchangeSelect(-1);
    }


    /**
     * 恢复箱子空位状态，只做倒箱位
     *
     * @param dragPositon
     */
    public void resetBox(int dragPositon) {
        if (dragPositon == -1) return;
        YardCntrInfo bean = this.channelList.get(dragPositon);
        bean.setHashBox(false);
        bean.setRecommendColorType(null);
        bean.setColorType(null);
        exchangeSelect(-1);
    }


    /**
     * 格式化所有能放想的位置颜色
     *
     * @param col  列数
     * @param row  层数
     * @param bay  呗位
     * @param maps 箱子
     */
    public boolean enadblAllLocation(int col, int row, Bay bay, List<String> reComBay, YardCntrInfo putBox, Map<String, YardCntrInfo> maps) {
        if (null == maps || null == putBox || null == bay || null == reComBay) return false;
        this.mBay = bay;
        this.mPutBox = putBox;
        //没有推荐位，默认位策划的拍
        if (reComBay.size() == 1) {
            for (int cell = 1; cell <= col; cell++) {
                for (int tier = 1; tier <= row; tier++) {
                    String key = String.format("(%s,%s)", cell, tier);
                    YardCntrInfo bean = maps.get(key);
                    //判断位置是否有箱子
                    if (null == bean) {
                        if (reComBay.get(0).equals(String.valueOf(cell))) {
                            setRecommentLocation(key, ColorType.GREEN);
                            break;
                        }
                    }
                }
            }
        } else {
            //一层一层的遍历
            for (int cell = 1; cell <= col; cell++) {
                for (int tier = 1; tier <= row; tier++) {
                    String key = String.format("(%s,%s)", cell, tier);
                    YardCntrInfo bean = maps.get(key);
                    //判断位置是否有箱子
                    if (null == bean) {
                        //大小位判断，不是该本位的箱子上面不设置颜色
                        if (tier > 1) {
                            //如果排层都大于1，取下面的箱子，如果下面的箱子不是该贝位的箱子，不设置颜色
                            String bayKey = String.format("(%s,%s)", cell, tier - 1);
                            YardCntrInfo detalBean = maps.get(bayKey);
                            if (null != detalBean) {
                                if (!detalBean.getRown().equals(mBay.getBay())) {
                                    continue;
                                }
                            } else continue;
                        }

                        //默认设置不能放箱
                        if (cell >= reComBay.size()) continue;
//                        LogUtils.e("tag", reComBay.toString());
                        if (reComBay.get(cell).trim().equals("GRAY")) {
//                            setRecommentLocation(key, ColorType.GRAY);//20190117，周浩说去掉
                        } else if (reComBay.get(cell).trim().equals("GREEN")) {
                            setRecommentLocation(key, ColorType.GREEN);
                        } else if (reComBay.get(cell).trim().equals("YELLOW")) {
                            setRecommentLocation(key, ColorType.YELLOW);
                        } else if (reComBay.get(cell).trim().equals("PINK")) {
                            setRecommentLocation(key, ColorType.PINK);
                        } else if (reComBay.get(cell).trim().equals("RED")) {
//                            setRecommentLocation(key, ColorType.RED);//20180207,滔哥说去掉
                        }
                        break;
                    }
                }

            }
        }
        notifyDataSetChanged();
        return true;
    }

    /**
     * 设置推荐数据
     */
    protected void setRecommentLocation(String defaultCell, ColorType colorType) {
        for (YardCntrInfo bean : channelList) {
            if (bean.getDefaultCell().equals(defaultCell)) {
                bean.setRecommendColorType(colorType);
                break;
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * 初始化推荐位置
     */
    public void resetRecommentLocation() {
        for (YardCntrInfo bean : channelList) {
            bean.setRecommendColorType(null);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置箱子列表
     */
    public void setListDate(List<YardCntrInfo> list) {
        channelList = list;
        this.notifyDataSetChanged();
    }


    /**
     * 更新Map
     */
    public void reflashMap(Map<String, YardCntrInfo> maps) {
        if (null == maps) return;
        maps.clear();
        for (YardCntrInfo x : channelList) {
            //如果相等，替换表格数据，箱子填充
            if (x.HashBox()) {
                x.setDefaultCell(String.format("(%s,%s)", x.getCell(), x.getTier()));
                maps.put(x.getDefaultCell(), x);
            }
        }
    }


    /**
     * 根据拖车方向，查找要倒箱的箱子，并且标志出来
     *
     * @param col  排
     * @param row  层
     * @param maps 在场箱数据
     */
    public void findRmCntrList(int col, int row, Map<String, YardCntrInfo> maps) {
//        LogUtils.e("tag", "isGetCntr:"+mGetBox.isGetCntr());
//        LogUtils.e("tag", "isTrkRight:"+mGetBox.isTrkRight());
        if (null == mGetBox || null == maps || !mGetBox.isGetCntr()) return;//只针对提箱这种情况下执行倒箱标志显示
        int cntrCell = Integer.parseInt(mGetBox.getCell());
        int cntrTier = Integer.parseInt(mGetBox.getTier());
        if (mGetBox.isTrkRight()) {
            for (int cell = cntrCell; cell <= col; cell++) {
                for (int tier = 1; tier <= row; tier++) {
                    if (cell == cntrCell && tier < cntrTier) continue;
                    //因为服务端返回坐标格式，保存的也是坐标格式，所以转换为坐标格式获取数据
                    String key = String.format("(%s,%s)", cell, tier);
                    YardCntrInfo bean = maps.get(key);
                    if (null == bean) continue;
                    bean.setRmCntr(true);//判断提箱类型
                }
            }
        } else if (mGetBox.isTrkLeft()) {
            for (int cell = 1; cell <= cntrCell; cell++) {
                for (int tier = 1; tier <= row; tier++) {
                    if (cell == cntrCell && tier < cntrTier) continue;
                    String key = String.format("(%s,%s)", cell, tier);
                    YardCntrInfo bean = maps.get(key);
                    if (null == bean) continue;
                    bean.setRmCntr(true);
                }
            }
        } else if (mGetBox.isTrkUp()) {
            for (int tier = 1; tier <= row; tier++) {
                if (tier < cntrTier) continue;
                String key = String.format("(%s,%s)", cntrCell, tier);
                YardCntrInfo bean = maps.get(key);
                if (null == bean) continue;
                bean.setRmCntr(true);
            }
        } else {
            for (int cell = 1; cell <= col; cell++) {
                for (int tier = 1; tier <= row; tier++) {
                    if (cell == cntrCell && tier < cntrTier) continue;
                    String key = String.format("(%s,%s)", cell, tier);
                    YardCntrInfo bean = maps.get(key);
                    if (null == bean) continue;
                    bean.setRmCntr(true);
                }
            }
        }

        notifyDataSetChanged();
    }

    public void setBay(Bay mBay) {
        this.mBay = mBay;
    }
}