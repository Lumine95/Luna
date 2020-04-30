package com.mm.luna.ui.nba;

import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.bean.NBABean;
import com.mm.luna.ui.adapter.LiveAdapter;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;
import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2019/8/25  19:26.
 */
public class LiveFragment extends BaseFragment<NBAContract.Presenter> implements NBAContract.View {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SmartRefreshLayout refreshLayout;
    private StatusLayoutManager statusLayoutManager;
    private LiveAdapter mAdapter;

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
        presenter.getLiveList();
    }

    private void initRecyclerView() {
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout).setOnStatusChildClickListener(v -> {
            presenter.getLiveList();
        }).build();
        statusLayoutManager.showLoadingLayout();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter = new LiveAdapter(new ArrayList()));
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(() -> {
        }, recyclerView);
        refreshLayout.setRefreshHeader(new DropBoxHeader(mActivity));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            presenter.getLiveList();
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            NBABean item = (NBABean) adapter.getItem(position);
            startActivity(new Intent(mActivity, LiveDetailActivity.class)
                    .putExtra("data", item));
        });
        refreshLayout.setRefreshHeader(new DropBoxHeader(mActivity));
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
        }
        refreshLayout.finishRefresh(true);
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
