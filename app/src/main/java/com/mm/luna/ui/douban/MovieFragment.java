package com.mm.luna.ui.douban;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.bean.HotMovieBean;
import com.mm.luna.ui.adapter.HotMovieAdapter;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/5/3  23:30.
 */

public class MovieFragment extends BaseFragment<DoubanContract.Presenter> implements DoubanContract.View {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SmartRefreshLayout refreshLayout;
    private StatusLayoutManager statusLayoutManager;
    private int position;
    private HotMovieAdapter mAdapter;
    private List<HotMovieBean.SubjectsBean> listData = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    protected DoubanContract.Presenter initPresenter() {
        return new DoubanPresenter(this);
    }

    @Override
    protected void initView(View view) {
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout)
                .setOnStatusChildClickListener(v -> {
                    presenter.getMovieList(pageIndex, true, position);
                }).build();
        statusLayoutManager.showLoadingLayout();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        presenter.getMovieList(pageIndex, true, position);

        mAdapter = new HotMovieAdapter(R.layout.item_movie, listData);
        recyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(() -> {
            pageIndex++;
            presenter.getMovieList(pageIndex, false, position);
        }, recyclerView);
        refreshLayout.setRefreshHeader(new PhoenixHeader(mContext));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageIndex = 0;
            presenter.getMovieList(pageIndex, true, position);
        });
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onFinish() {
        statusLayoutManager.showSuccessLayout();
    }

    @Override
    public void onError() {
        statusLayoutManager.showErrorLayout();
    }

    @Override
    public void setData(HotMovieBean bean, boolean isClear) {
        if (bean.getSubjects().size() == 0) {
            mAdapter.loadMoreEnd();
        } else {
            if (isClear) {
                listData.clear();
                mAdapter.setNewData(bean.getSubjects());
            } else {
                mAdapter.addData(bean.getSubjects());
            }
            mAdapter.loadMoreComplete();
        }
        listData.addAll(bean.getSubjects());
        refreshLayout.finishRefresh(true);
    }
}
