package com.smt.wxdj.swxdj.adapt;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smt.wxdj.swxdj.R;

/**
 * Created by gbh on 16/6/20.
 */
public class MenuAdapt extends BaseAdapter {
    private String[] array;
    private LayoutInflater inflater;
    private int textColor = Color.BLACK;
    private Context mContext;

    public MenuAdapt(Context context, String[] array, int textColor) {
        inflater = LayoutInflater.from(context);
        this.array = array;
        this.textColor = textColor;
        this.mContext = context;
    }


    @Override
    public int getCount() {
        return array.length;
    }

    @Override
    public Object getItem(int i) {
        return array[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView text = null;
        if (view == null) {
            view = inflater.inflate(R.layout.menu_selete_item, viewGroup, false);
        }
        text = (TextView) view.findViewById(R.id.text);
        text.setText(array[i]);
        return view;
    }
}
