package com.smt.wxdj.swxdj.setting.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.utils.SettingConfig;
import com.smtlibrary.utils.PreferenceUtils;

import static com.smt.wxdj.swxdj.utils.SettingConfig.OPEN_TIME_TASK;


public class SetInfoFragment extends Fragment {

    private CheckBox checkBox;
    private EditText setTime;

    public static SetInfoFragment newInstance() {
        SetInfoFragment fragment = new SetInfoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_canshu_setting, container, false);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        setTime = (EditText) view.findViewById(R.id.settingTime);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PreferenceUtils.putBoolean(getActivity(), OPEN_TIME_TASK, b);
                if (!TextUtils.isEmpty(setTime.getText().toString().trim())) {
                    checkBox.setText(b ? getString(R.string.open) : getString(R.string.close));
                    setTime.setEnabled(!checkBox.isChecked());
//                    save();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.please_enter_the_time), Toast.LENGTH_SHORT).show();
                    checkBox.setChecked(false);
                }
            }
        });
        checkBox.setChecked(PreferenceUtils.getBoolean(getActivity(), OPEN_TIME_TASK, false));
        int timeOut =  PreferenceUtils.getInt(getActivity(), SettingConfig.SETTING_TIME, SettingConfig.TIME_OUT);
        setTime.setText("" + timeOut);
        setTime.setEnabled(!checkBox.isChecked());
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if(checkBox.isChecked() && !TextUtils.isEmpty(setTime.getText().toString())){
                    save();
                }
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void save() {
        if (!TextUtils.isEmpty(setTime.getText().toString().trim()))
            PreferenceUtils.putInt(getActivity(), SettingConfig.SETTING_TIME, Integer.parseInt(setTime.getText().toString()));
//        Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
    }

}
