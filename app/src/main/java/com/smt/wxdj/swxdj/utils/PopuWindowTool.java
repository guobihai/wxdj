package com.smt.wxdj.swxdj.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.adapt.PupuRecylerBayViewAdapt;
import com.smt.wxdj.swxdj.adapt.PupuRecylerViewAdapt;
import com.smt.wxdj.swxdj.bean.Bay;
import com.smt.wxdj.swxdj.interfaces.IMenuSelectInterface;

import java.util.List;

/**
 * Created by gbh on 16/6/22.
 */
public class PopuWindowTool {

    /**
     * menu菜单选项
     *
     * @param context
     * @param parent  位置下面
     * @param anchor  参考对象
     */
    public static void showPopupWindow(Context context, Object object, View parent, final View anchor, final IMenuSelectInterface iMenuSelectInterface) {
        View view = LayoutInflater.from(context).inflate(R.layout.popu_layout, null);
        final PopupWindow popupWindow = new PopupWindow(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
        popupWindow.setTouchable(true); // 设置PopupWindow可触摸
        popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域是否可触摸
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        RecyclerView pRecycleView = (RecyclerView) view.findViewById(R.id.recycle_view);
        int row = 6;
        if (object instanceof String[]) {
            String array[] = (String[]) object;
            if (array.length < 6) {
                row = 1;
            }
            PupuRecylerViewAdapt adapt = new PupuRecylerViewAdapt(array);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, row);
            pRecycleView.setLayoutManager(gridLayoutManager);
            pRecycleView.setHasFixedSize(true);
            pRecycleView.setAdapter(adapt);
            adapt.setOnItemClickListener(new PupuRecylerViewAdapt.OnItemClickListener() {
                @Override
                public void onItemClick(View view, Object object) {
                    if (null != iMenuSelectInterface)
                        iMenuSelectInterface.onItemSelectResult(anchor, object);
                    popupWindow.dismiss();
                }
            });
        } else if (object instanceof List) {
            List<Bay> list = (List<Bay>) object;
            if (list.size() < 6) {
                row = 1;
            }
            PupuRecylerBayViewAdapt adapt = new PupuRecylerBayViewAdapt(list);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, row);
            pRecycleView.setLayoutManager(gridLayoutManager);
            pRecycleView.setHasFixedSize(true);
            pRecycleView.setAdapter(adapt);
            adapt.setOnItemClickListener(new PupuRecylerBayViewAdapt.OnItemClickListener() {
                @Override
                public void onItemClick(View view, Object obj) {
                    if (null != iMenuSelectInterface)
                        iMenuSelectInterface.onItemSelectResult(anchor, obj);
                    popupWindow.dismiss();
                }
            });


        }

        // 添加pop窗口关闭事件
        // popupWindow.setOnDismissListener(new poponDismissListener());
        // popupWindow.setAnimationStyle(R.style.popuStyle); //设置
        // popupWindow动画样式
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        // 设置好参数之后再show,参照物显示位置
        popupWindow.showAsDropDown(anchor, -(3 * 140), 5);
        popupWindow.showAtLocation(parent, Gravity.TOP, location[0], location[1]);

    }


}
