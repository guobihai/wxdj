package com.smt.wxdj.swxdj.adapt;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.utils.DisplayTool;

/**
 * Created by gbh on 16/6/20.
 */
public class MyCdOrTwAdapt extends ArrayAdapter<String> {
    private String[] array;
    private LayoutInflater inflater;
    private int textColor = Color.BLACK;
    private Context mContext;
    private float mDensity;

    public MyCdOrTwAdapt(Context context, String[] array,int textColor) {
        super(context, android.R.layout.simple_spinner_item, array);
        inflater = LayoutInflater.from(context);
        this.array = array;
        this.textColor = textColor;
        this.mContext = context;
        mDensity = DisplayTool.getDisplayDensity(mContext);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        //修改Spinner展开后的字体颜色 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            convertView.setBackgroundResource(R.drawable.menu_item_bg);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(array[position]);
        tv.setTextSize((int)(mContext.getResources().getDimension(R.dimen.login_spiner_textsize)/mDensity));
        tv.setTextColor(textColor);
//        tv.getPaint().setFakeBoldText(true);
        if(textColor == Color.WHITE) {
//            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextColor(Color.BLACK);
        }

        return convertView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 修改Spinner选择后结果的字体颜色
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            convertView.setBackgroundResource(R.drawable.menu_item_bg);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(array[position]);
        tv.setTextSize((int)(mContext.getResources().getDimension(R.dimen.login_spiner_textsize)/mDensity));
        tv.setTextColor(textColor);
        tv.getPaint().setFakeBoldText(true);
        if(textColor == Color.WHITE) {
            tv.setGravity(Gravity.CENTER);
        }
        return convertView;
    }

}
