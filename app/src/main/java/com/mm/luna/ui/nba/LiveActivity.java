package com.mm.luna.ui.nba;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.NBABean;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2019/1/11 14:54.
 */
public class LiveActivity extends BaseActivity<NBAContract.Presenter> implements NBAContract.View {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SmartRefreshLayout refreshLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private StatusLayoutManager statusLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_live;
    }

    @Override
    public NBAContract.Presenter initPresenter() {
        return new NBAPresenter(this);
    }

    @Override
    public void initView() {
        setStatusBarColor();
        toolbar.setNavigationOnClickListener(view -> finish());
        initRecyclerView();
        presenter.getLiveList(true);
    }

    private void initRecyclerView() {
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout)
                .setOnStatusChildClickListener(v -> {

                }).build();
        statusLayoutManager.showLoadingLayout();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // recyclerView.setAdapter(mAdapter = new ScheduleAdapter(list));
//        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        mAdapter.setOnLoadMoreListener(() -> {
//            presenter.getScheduleList(currentDate, false);
//        }, recyclerView);
//        refreshLayout.setRefreshHeader(new PhoenixHeader(mContext));
//        refreshLayout.setOnRefreshListener(refreshLayout -> {
//
//        });
    }

    @Override
    public void setData(boolean isClear, List<NBABean> beanList) {

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
