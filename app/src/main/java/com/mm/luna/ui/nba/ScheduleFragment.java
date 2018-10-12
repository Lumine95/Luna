package com.mm.luna.ui.nba;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.library.utils.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.bean.NBABean;
import com.mm.luna.ui.adapter.ScheduleAdapter;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/10/10 15:38.
 */
public class ScheduleFragment extends BaseFragment<NBAContract.Presenter> implements NBAContract.View {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SmartRefreshLayout refreshLayout;
    private StatusLayoutManager statusLayoutManager;
    private String currentDate;
    private String currentYear = DateUtil.getCurrentStrDate("yyyy");
    List<NBABean> list = new ArrayList<>();
    private ScheduleAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    protected NBAContract.Presenter initPresenter() {
        return new NBAPresenter(this);
    }

    @Override
    protected void initView(View view) {
        initRecyclerView();
        currentDate = DateUtil.getCurrentStrDate("yyyy-MM-dd");
        presenter.getScheduleList(currentDate, true);
    }

    private void initRecyclerView() {
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout)
                .setOnStatusChildClickListener(v -> {
                    presenter.getScheduleList(currentDate, true);
                }).build();
        statusLayoutManager.showLoadingLayout();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter = new ScheduleAdapter(list));
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(() -> {
            presenter.getScheduleList(currentDate, false);
        }, recyclerView);
        refreshLayout.setRefreshHeader(new PhoenixHeader(mContext));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            currentDate = DateUtil.getCurrentStrDate("yyyy-MM-dd");
            presenter.getScheduleList(currentDate, true);
        });
    }

    @Override
    public void setData(boolean isClear, List<NBABean> beanList) {
        for (NBABean bean : beanList) {
            Log.d("schedule: ", bean.getDate() + "  " + bean.getTime() + bean.getVisitingTeam());
            if (!TextUtils.isEmpty(bean.getDate())) {
                getNextDay(bean.getDate());
                ;
            }
        }
        if (beanList.size() == 0) {
            mAdapter.loadMoreEnd();
        } else {
            if (isClear) {
                mAdapter.setNewData(beanList);
            } else {
                mAdapter.addData(beanList);
            }
            mAdapter.loadMoreComplete();
        }
        refreshLayout.finishRefresh(true);
    }

    private void getNextDay(String date) {
        String[] split = date.split(" ");
        String thisDay = currentYear + "-" + split[0].replace("日", "").replace("月", "-");
        @SuppressLint("SimpleDateFormat") DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date temp = dft.parse(thisDay);
            Calendar cld = Calendar.getInstance();
            cld.setTime(temp);
            cld.add(Calendar.DATE, 1);
            temp = cld.getTime();
            currentDate = dft.format(temp);   // 获得下一天日期字符串
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish() {
        statusLayoutManager.showSuccessLayout();
    }

    @Override
    public void onError() {
        statusLayoutManager.showErrorLayout();
    }
}
