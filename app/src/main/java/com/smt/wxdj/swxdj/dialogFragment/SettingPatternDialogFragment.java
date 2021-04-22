package com.smt.wxdj.swxdj.dialogFragment;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.smt.wxdj.swxdj.R;
import com.smtlibrary.utils.PreferenceUtils;


/**
 * Created by xlj on 2017/5/19.
 */

public class SettingPatternDialogFragment extends DialogFragment implements View.OnClickListener{

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        Window window = getDialog().getWindow();
//        View view = inflater.inflate(R.layout.setting_pattern, container, false);
        mContext = getActivity();
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
////        window.setLayout(-1, -2);//这2行,和上面的一样,注意顺序就行;
//
//        int width = PreferenceUtils.getInt(mContext, "width", 400);
//        int height = PreferenceUtils.getInt(mContext, "height", 400);
//        Toast.makeText(mContext, "width:"+width + "----height:" + height, 1).show();
//
//        WindowManager.LayoutParams lp = window.getAttributes();
////        lp.width = (int) (width - mContext.getResources().getDimension(R.dimen.activity_horizontal_margin));
//        lp.width = (int) (width - 200);
////        lp.height = height - height / 6;
//        lp.height = 300;
//        window.setAttributes(lp);
//        return view;

        final Window window = getDialog().getWindow();
        View view = inflater.inflate(R.layout.setting_pattern,  ((ViewGroup) window.findViewById(android.R.id.content)), false);//需要用android.R.id.content这个view
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
//        window.setLayout(700, 500);//这2行,和上面的一样,注意顺序就行;
//        window.setLayout(-1, -2);//这2行,和上面的一样,注意顺序就行;

        int width = PreferenceUtils.getInt(mContext, "width", 400);
        int height = PreferenceUtils.getInt(mContext, "height", 400);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (width - mContext.getResources().getDimension(R.dimen.activity_horizontal_margin));
        lp.height = height - height / 6;
        window.setAttributes(lp);

        setCancelable(false);
//        WindowManager wm = getActivity().getWindowManager();
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        Toast.makeText(mContext, "width:"+width + "----height:" + height, 1).show();

        view.findViewById(R.id.bottom).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bottom:
                dismiss();
                break;
            default:
                break;
        }
    }
}
