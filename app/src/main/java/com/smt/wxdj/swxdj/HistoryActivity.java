package com.smt.wxdj.swxdj;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.smt.wxdj.swxdj.adapt.HistoryAdapter;
import com.smt.wxdj.swxdj.history.presenter.HistoryPresenterImpl;
import com.smt.wxdj.swxdj.history.view.HistoryView;

import java.util.Date;
import java.util.List;

import static com.codbking.widget.utils.DateUtils.TYPE_YMDHM;
import static com.codbking.widget.utils.DateUtils.yyyyMMddHHmmss;
import static com.smt.wxdj.swxdj.utils.DateUtils.chanceTime;
import static com.smt.wxdj.swxdj.utils.DateUtils.formatDate;
import static com.smt.wxdj.swxdj.utils.DateUtils.getCurrentY_M_D;
import static com.smt.wxdj.swxdj.utils.DateUtils.parseDate;

public class HistoryActivity extends BaseActivity<HistoryView, HistoryPresenterImpl> implements HistoryView, View.OnClickListener {

    private Toolbar toolbar;
    private TextView toolTitle;//标题
    private HistoryPresenterImpl presenter;
    private Button btnQuery;//查询
    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    private TextView tvStartDate;
    private TextView tvEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initViews();
        initData();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolTitle = (TextView) findViewById(R.id.taskTitle);
        toolTitle.setText(getString(R.string.history));
        setSupportActionBar(toolbar);
        btnQuery = (Button) findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(this);
        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HistoryAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        String date = getCurrentY_M_D();
        tvStartDate.setText(date + " 00:00");
        tvEndDate.setText(date + " 23:59");

//        getHistoryData();
    }

    private void getHistoryData(){
        StringBuffer sb = new StringBuffer();
        String startDate = chanceTime(tvStartDate.getText().toString(),
                TYPE_YMDHM, yyyyMMddHHmmss);
        String endDate = chanceTime(tvEndDate.getText().toString(),
                TYPE_YMDHM, yyyyMMddHHmmss);
        sb.append(MyApplication.user.getSignonUSERID())//工号
                .append(",")
                .append(startDate)
                .append(",")
                .append(endDate);
        presenter.getWorkStatistics(sb.toString());
    }

    private void showDatePickDialog(final TextView tv) {
        DatePickDialog dialog = new DatePickDialog(this);

        dialog.setStartDate(parseDate(tv.getText().toString(), TYPE_YMDHM));
        //设置上下年分限制
        dialog.setYearLimt(10);
        //设置标题
        dialog.setTitle(getString(R.string.select_time));
        //设置类型
        dialog.setType(DateType.TYPE_ALL);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd E HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                tv.setText(formatDate(date, TYPE_YMDHM));
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected HistoryPresenterImpl createPresenter() {
        return presenter = new HistoryPresenterImpl();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnQuery://查询
//                getHistoryData();
                break;
            case R.id.tvStartDate://开始时间日期
                showDatePickDialog(tvStartDate);
                break;
            case R.id.tvEndDate://结束时间日期
                showDatePickDialog(tvEndDate);
                break;
            default:
                break;
        }
    }

    @Override
    public void getWorkStatisticsData(Object obj) {
        List<String> list = (List<String>) obj;
        mAdapter.setNewData(list);
    }
}
