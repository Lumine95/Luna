package com.mm.luna.ui.nba;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.NBABean;
import com.mm.luna.ui.adapter.LiveAdapter;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;
import com.scwang.smartrefresh.header.DropboxHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
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
    private LiveAdapter mAdapter;

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
        toolbar.setTitle("体育直播");
        toolbar.setNavigationOnClickListener(view -> finish());
        initRecyclerView();
        presenter.getLiveList();
    }

    private void initRecyclerView() {
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout).setOnStatusChildClickListener(v -> {
            presenter.getLiveList();
        }).build();
        statusLayoutManager.showLoadingLayout();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter = new LiveAdapter(new ArrayList()));
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(() -> {
        }, recyclerView);
        refreshLayout.setRefreshHeader(new DropboxHeader(this));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            presenter.getLiveList();
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            NBABean item = (NBABean) adapter.getItem(position);
            startActivity(new Intent(LiveActivity.this, LiveDetailActivity.class)
                    .putExtra("data", item));
        });
        refreshLayout.setRefreshHeader(new DropboxHeader(this));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            presenter.getLiveList();
        });
    }

    @Override
    public void setData(boolean isClear, List<NBABean> beanList) {
        if (beanList.size() > 0) {
            mAdapter.setNewData(beanList);
            mAdapter.loadMoreEnd();
        } else {
            statusLayoutManager.showEmptyLayout();
        }  refreshLayout.finishRefresh(true);
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
