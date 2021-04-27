package com.smt.wxdj.swxdj.utils;

import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.viewmodel.nbean.YardCntrInfo;

import java.util.List;

/**
 * Created by gbh on 16/6/21.
 */
public class CellTool {

    /**
     * 箱子没有移动
     */
    public static int BOX_TYPE = -1;//没状态
    /**
     * 条件复合
     */
    public static final int BOX_TYPE_SUCESS = 0;
    /**
     * 悬空放置箱子
     */
    public static final int BOX_TYPE_EA = 1001;
    /**
     * 箱子被压住了
     */
    public static final int BOX_TYPE_EB = 1002;
    /**
     * 已有箱子在该位置，位置不合法
     */
    public static final int BOX_TYPE_EC = 1003;


    private static int Cell;//排
    private static int Tier;//层

    /**
     * 进口已启航
     */
    public static final int IN_DOCKING = 11;
    /**
     * 进口未启航
     */
    public static final int IN_NOT_DOCKING = 12;
    /**
     * 出口和中转已启航
     */
    public static final int OUT_DOCKING = 13;
    /**
     * 出口和中转未启航
     */
    public static final int OUT_NOT_DOCKING = 14;

    /**
     * 获取当前排
     *
     * @return
     */
    public static int getCell() {
        return Cell;
    }

    public static void setCell(int cell) {
        Cell = cell;
    }

    /**
     * 获取当前层
     *
     * @return
     */
    public static int getTier() {
        return Tier;
    }

    public static void setTier(int tier) {
        Tier = tier;
    }


    /**
     * 特殊计算倒箱位坐标
     * 根据posstion 计算出坐标系（x,y）
     *
     * @param position
     * @param col      排
     * @param row      层
     */
    public static String getDxwCell(int position, int col, int row) {
        int index = position + 1;//当前item的位置
        int y = index / col;//计算item 相对GridView（0，0）位置的X坐标位置
        int x = index % col;//计算item 相对GridView（0，0）位置的Y坐标位置（排）
        if (index % col == 0) {
            x = col;
            //如果整除，则y-1
            y = y - 1;
        }
        //倒序算出Y的坐标值(层)
        int ty = row - y;
        return String.format("(0,%s)", ty);
    }


    /**
     * 根据posstion 计算出坐标系（x,y）
     *
     * @param position
     * @param col      排
     * @param row      层
     */
    public static String getCell(int position, int col, int row) {
        int index = position + 1;//当前item的位置
        int y = index / col;//计算item 相对GridView（0，0）位置的X坐标位置
        int x = index % col;//计算item 相对GridView（0，0）位置的Y坐标位置（排）
        if (index % col == 0) {
            x = col;
            //如果整除，则y-1
            y = y - 1;
        }
        //倒序算出Y的坐标值(层)
        int ty = row - y;
        setCell(x);
        setTier(ty);
        return String.format("(%s,%s)", x, ty);
    }


    /**
     * 根据posstion 计算出坐标系X
     *
     * @param position
     * @param col      排
     * @param row      层
     */
    public static int getCellX(int position, int col, int row) {
        int index = position + 1;//当前item的位置
        int y = index / col;//计算item 相对GridView（0，0）位置的X坐标位置
        int x = index % col;//计算item 相对GridView（0，0）位置的Y坐标位置
        if (index % col == 0) {
            x = col;
            //如果整除，则y-1
            y = y - 1;
        }
        //倒序算出Y的坐标值
//        int ty = row - y;
        return x;
    }

    /**
     * 根据posstion 计算出坐标系X
     *
     * @param position
     * @param col      排
     * @param row      层
     */
    public static int getCellY(int position, int col, int row) {
        int index = position + 1;//当前item的位置
        int y = index / col;//计算item 相对GridView（0，0）位置的X坐标位置
//        int x = index % col;//计算item 相对GridView（0，0）位置的Y坐标位置
        if (index % col == 0) {
//            x = col;
            //如果整除，则y-1
            y = y - 1;
        }
        //倒序算出Y的坐标值
        int ty = row - y;
        return ty;
    }


    /**
     * 判断箱子移动位置的合法性：（判断下标是否有箱子，或者是否可作为底箱）
     *
     * @param selectPosition 要移动的箱子
     * @param movePosition   移动箱子要放置的位置
     * @param col            排
     */
    public static int isLegal(int selectPosition, int movePosition, int col, List<YardCntrInfo> channelList) {
        if (selectPosition == movePosition) return CellTool.BOX_TYPE;
        //计算出列数
        int row = channelList.size() / col;
        // 1、计算出当前的坐标,得要坐标Y的数值：
        // 2、首先判断是否是排底，并且判断排底有没有箱子，如果没有，则合法，如果有则不合法；
        // 3、如果不是排底，判断该位置是否已有箱子，若有：非法操作，若没有，首先再判断该位置底下是否已有箱子，若有则合法；否则非法操作；
        // 4、如果是压底的箱子，往上移动箱子，判断箱子是否被压，或者悬空移动
        //1.
        int sx = getCellX(selectPosition, col, row);
        int sy = getCellY(selectPosition, col, row);
        int x = getCellX(movePosition, col, row);
        int y = getCellY(movePosition, col, row);

        setCell(x);
        setTier(y);
        //箱子悬空放，条件不合法
        if (sy == y - 1 && sx == x) {
            return BOX_TYPE_EA;
        }

        //如果要移动的箱子是第一个，向上移动，非法，
        if (sy < row && channelList.get(selectPosition - col).HashBox()) {
            return BOX_TYPE_EB;
        }

        //2.如果等于1（排底），并且没有箱子，则合法
        if (y == 1 && channelList.get(movePosition).HashBox()) {
            return BOX_TYPE_EB;
        }
        //3.移动的位置已有箱子，则不合法
        if (channelList.get(movePosition).HashBox()) {
            return BOX_TYPE_EC;
        }
        //4.如果底下没有箱子，则非法,悬空放置箱子，非法操作
        if (y != 1 && !channelList.get(movePosition + col).HashBox()) {
            return BOX_TYPE_EA;
        }
        return BOX_TYPE_SUCESS;
    }

    /**
     * 判断箱子移动位置的合法性：（判断下标是否有箱子，或者是否可作为底箱）
     *
     * @param movePosition 移动箱子要放置的位置
     * @param col          排
     */
    public static int isLegal(int movePosition, int col, List<YardCntrInfo> channelList) {
        //计算出列数
        int row = channelList.size() / col;
        // 1、计算出当前的坐标,得要坐标Y的数值：
        // 2、首先判断是否是排底，并且判断排底有没有箱子，如果没有，则合法，如果有则不合法；
        // 3、如果不是排底，判断该位置是否已有箱子，若有：非法操作，若没有，首先再判断该位置底下是否已有箱子，若有则合法；否则非法操作；
        // 4、如果是压底的箱子，往上移动箱子，判断箱子是否被压，或者悬空移动
        //1.
        int x = CellTool.getCellX(movePosition, col, row);
        int y = CellTool.getCellY(movePosition, col, row);

        setCell(x);
        setTier(y);

        //2.如果等于1（排底），并且没有箱子，则合法
        if (y == 1 && channelList.get(movePosition).HashBox()) {
            return BOX_TYPE_EB;
        }
        //3.移动的位置已有箱子，则不合法
        if (channelList.get(movePosition).HashBox()) {
            return BOX_TYPE_EC;
        }
        //4.如果底下没有箱子，则非法,悬空放置箱子，非法操作
        if (y != 1 && !channelList.get(movePosition + col).HashBox()) {
            return BOX_TYPE_EA;
        }
        return BOX_TYPE_SUCESS;
    }


    /**
     * 判断箱子是否被压住
     *
     * @param selectPosition 选中的箱子
     * @param col            排
     */
    public static int isSelectLegal(int selectPosition, int col, List<YardCntrInfo> channelList) {
        //计算出列数
        int row = channelList.size() / col;
        int y = getCellY(selectPosition, col, row);
        //判断箱子是否被压住
        if (y < row) {
            if (channelList.get(selectPosition - col).HashBox())
                return BOX_TYPE_EB;
        }
        return BOX_TYPE_SUCESS;
    }


}
