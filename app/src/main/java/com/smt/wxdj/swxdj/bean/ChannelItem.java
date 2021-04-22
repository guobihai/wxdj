package com.smt.wxdj.swxdj.bean;

import java.io.Serializable;

/**
 * ITEM的对应可序化队列属性
 */
public class ChannelItem implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6465237897027410019L;
    /**
     * 栏目对应ID
     */
    public Integer id;
    /**
     * 栏目对应NAME
     */
    public String name;
    /**
     * 栏目在整体中的排序顺序  rank
     */
    public Integer orderId;
    /**
     * 栏目是否选中
     */
    public boolean selected;

    /**
     * 箱子位置坐标
     */
    private String cell;

    /**
     * 判断是否已有箱子
     */
    private boolean isHashBox;



    public ChannelItem() {

    }

    public ChannelItem(int id, String name, int orderId, boolean selected, String cell,boolean isHashBox) {
        this.id = Integer.valueOf(id);
        this.name = name;
        this.orderId = Integer.valueOf(orderId);
        this.selected = selected;
        this.cell = cell;
        this.isHashBox = isHashBox;
    }

    public ChannelItem(int id, String name, int orderId, boolean selected) {
        this.id = Integer.valueOf(id);
        this.name = name;
        this.orderId = Integer.valueOf(orderId);
        this.selected = selected;
    }


    public int getId() {
        return this.id.intValue();
    }

    public String getName() {
        return this.name;
    }

    public int getOrderId() {
        return this.orderId.intValue();
    }

    public boolean getSelected() {
        return this.selected;
    }

    public void setId(int paramInt) {
        this.id = Integer.valueOf(paramInt);
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public void setOrderId(int paramInt) {
        this.orderId = Integer.valueOf(paramInt);
    }

    public void setSelected(boolean paramInteger) {
        this.selected = paramInteger;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public boolean HashBox() {
        return isHashBox;
    }

    public void setHashBox(boolean hashBox) {
        isHashBox = hashBox;
    }

    public String toString() {
        return "ChannelItem [id=" + this.id + ", name=" + this.name
                + ", selected=" + this.selected + "]";
    }
}