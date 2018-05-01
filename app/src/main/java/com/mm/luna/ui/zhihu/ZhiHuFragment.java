package com.mm.luna.ui.zhihu;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
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

/**
 * Created by ZMM on 2018/2/5.
 */

public class ZhiHuFragment extends BaseFragment<ZhiHuContract.Presenter> implements ZhiHuContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    FloatingActionButton fabTop;
    private List<ZhiHuEntity.StoriesBean> listData = new ArrayList<>();
    private String currentDate;
    private ZhiHuAdapter mAdapter;

    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private LinearLayoutManager layoutManager;
    private boolean isTop;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zhihu;
    }

    @Override
    protected ZhiHuContract.Presenter initPresenter() {
        return new ZhiHuPresenter(this);
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        fab = view.findViewById(R.id.fab);
        fabTop = view.findViewById(R.id.fab_top);

        layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        presenter.getTodayData(true);
        mAdapter = new ZhiHuAdapter(R.layout.item_zhihu, listData);
        recyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnItemClickListener((adapter, v, position) -> {
            Log.d("", "onItemClick: " + position);
            startActivity(new Intent(mContext, ZhiHuDetailActivity.class).putExtra("id", listData.get(position).getId()));
        });
        mAdapter.setOnLoadMoreListener(() -> presenter.getBeforeData(currentDate, false), recyclerView);
        refreshLayout.setRefreshHeader(new PhoenixHeader(mContext));
        refreshLayout.setOnRefreshListener(refreshLayout -> presenter.getTodayData(true));

        setRVListener();
        fab.setOnClickListener(v -> selectData());
        fabTop.setOnClickListener(v -> recyclerView.smoothScrollToPosition(0));
        //   fab.setOnClickListener(v -> startActivity(new Intent(mContext, VioletActivity.class)));
    }

    /**
     * 监听recyclerView滚动
     */
    private void setRVListener() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isTop = !recyclerView.canScrollVertically(-1);
                fabTop.setVisibility(isTop ? View.GONE : View.VISIBLE);
                fab.setVisibility(isTop ? View.VISIBLE : View.GONE);
            }
        });
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
        datePickerDialog.show(mActivity.getFragmentManager(), "DatePickerDialog");
    }

}
