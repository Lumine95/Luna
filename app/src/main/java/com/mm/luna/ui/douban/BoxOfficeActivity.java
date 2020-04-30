package com.mm.luna.ui.douban;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.HotMovieBean;
import com.mm.luna.bean.MovieDetailBean;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/8/30 17:07.
 */
public class BoxOfficeActivity extends BaseActivity<DoubanContract.Presenter> implements DoubanContract.View {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;

    private StatusLayoutManager statusLayoutManager;
    private BaseQuickAdapter<HotMovieBean.TicketBean, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_box_office;
    }

    @Override
    public DoubanContract.Presenter initPresenter() {
        return new DoubanPresenter(this);
    }

    @Override
    public void initView() {
        setStatusBarColor();
        toolbar.setNavigationOnClickListener(view -> finish());
        initRecyclerView();
        presenter.getBoxOffice();
    }

    private void initRecyclerView() {
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout)
                .setOnStatusChildClickListener(v -> {
                    presenter.getBoxOffice();
                }).build();
        statusLayoutManager.showEmptyLayout();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<HotMovieBean.TicketBean, BaseViewHolder>(R.layout.item_article) {
            @Override
            protected void convert(BaseViewHolder helper, HotMovieBean.TicketBean item) {
                helper.setText(R.id.tv_title, item.getName());
            }
        });
        refreshLayout.setOnRefreshListener(() -> {
            presenter.getBoxOffice();
        });
    }

    @Override
    public void setData(HotMovieBean bean, boolean isClear) {
        mAdapter.setNewData(bean.getResult());
        mAdapter.loadMoreComplete();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMovieDetail(MovieDetailBean bean) {

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
