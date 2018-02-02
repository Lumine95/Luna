package com.mm.luna.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.ZhiHuEntity;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class ZhiHuActivity extends BaseActivity<ZhiHuContract.Presenter> implements ZhiHuContract.View {

    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private List<ZhiHuEntity.StoriesBean> listData = new ArrayList<>();
    private String currentDate;
    private ZhiHuAdapter mAdapter;

    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public ZhiHuContract.Presenter initPresenter() {
        return new ZhiHuPresenter(this);
    }

    @Override
    public void initView() {
        StatusBarUtil.setColorForDrawerLayout(this, drawer, Color.TRANSPARENT, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter.getTodayData(true);
        mAdapter = new ZhiHuAdapter(R.layout.item_zhihu, listData);
        recyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Log.d("", "onItemClick: " + position);
            startActivity(new Intent(this, ZhiHuDetailActivity.class).putExtra("id", listData.get(position).getId()));
        });
        mAdapter.setOnLoadMoreListener(() -> presenter.getBeforeData(currentDate, false), recyclerView);
        refreshLayout.setRefreshHeader(new PhoenixHeader(this));
        refreshLayout.setOnRefreshListener(refreshLayout -> presenter.getTodayData(true));
        toolbar.setTitle(R.string.ZhiHuDaily);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);

        fab.setOnClickListener(v -> selectData());
    }

    @Override
    public void setData(ZhiHuEntity zhiHuEntity, boolean isClear) {
        currentDate = zhiHuEntity.getDate();
        if (isClear) {
            listData.clear();
            mAdapter.setNewData(zhiHuEntity.getStories());
        } else {
            mAdapter.addData(zhiHuEntity.getStories());
        }
        listData.addAll(zhiHuEntity.getStories());
        mAdapter.loadMoreComplete();
        refreshLayout.finishRefresh(true);
    }

    /**
     * 时间选择
     */
    private void selectData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            Calendar temp = Calendar.getInstance();
            temp.clear();
            temp.set(year, monthOfYear, dayOfMonth);

            String date = new SimpleDateFormat("yyyyMMdd").format(new Date(temp.getTimeInMillis() + 24 * 60 * 60 * 1000));
            Log.d("date======", date);
            presenter.getBeforeData(date, true);
            recyclerView.smoothScrollToPosition(0);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        // 设置日历可选区间
        datePickerDialog.setMaxDate(Calendar.getInstance());
        Calendar minDate = Calendar.getInstance();
        minDate.set(2013, 5, 20);
        datePickerDialog.setMinDate(minDate);
        datePickerDialog.vibrate(false);
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }
}
