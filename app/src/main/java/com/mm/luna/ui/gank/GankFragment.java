package com.mm.luna.ui.gank;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.bean.GankBean;
import com.mm.luna.ui.common.WebViewActivity;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/8/10 15:32.
 */
public class GankFragment extends BaseFragment<GankContract.Presenter> implements GankContract.View {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SmartRefreshLayout refreshLayout;
    private int type;
    private int pageIndex = 1;
    private List<GankBean.ResultsBean> listData = new ArrayList<>();
    private StatusLayoutManager statusLayoutManager;
    private BaseQuickAdapter<GankBean.ResultsBean, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    protected GankContract.Presenter initPresenter() {
        return new GankPresenter(this);
    }

    @Override
    protected void initView(View view) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout)
                .setOnStatusChildClickListener(v -> {
                    presenter.getArticleList(pageIndex, true, type);
                }).build();
        statusLayoutManager.showLoadingLayout();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        presenter.getArticleList(pageIndex, true, type);

        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<GankBean.ResultsBean, BaseViewHolder>(R.layout.item_gank, listData) {
            @Override
            protected void convert(BaseViewHolder helper, GankBean.ResultsBean item) {
                helper.setText(R.id.tv_title, item.getDesc());
                helper.setText(R.id.tv_author, item.getWho());
                helper.setText(R.id.tv_date, item.getPublishedAt());
                helper.itemView.setOnClickListener(v -> startActivity(new Intent(mContext, WebViewActivity.class)
                        .putExtra("title", item.getDesc())
                        .putExtra("url", item.getUrl())));
            }
        });
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(() -> {
            pageIndex++;
            presenter.getArticleList(pageIndex, false, type);
        }, recyclerView);
        refreshLayout.setRefreshHeader(new PhoenixHeader(mContext));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageIndex = 0;
            presenter.getArticleList(pageIndex, true, type);
        });
    }

    @Override
    public void setData(GankBean bean, boolean isClear) {
        if (bean.getResults().size() == 0) {
            mAdapter.loadMoreEnd();
        } else {
            if (isClear) {
                listData.clear();
                mAdapter.setNewData(bean.getResults());
            } else {
                mAdapter.addData(bean.getResults());
            }
            mAdapter.loadMoreComplete();
        }
        listData.addAll(bean.getResults());
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
