package com.mm.luna.ui.wan;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.bean.ArticleBean;
import com.mm.luna.ui.common.WebViewActivity;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/8/14 11:28.
 */
public class ArticleFragment extends BaseFragment<WanContract.Presenter> implements WanContract.View {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SmartRefreshLayout refreshLayout;
    private StatusLayoutManager statusLayoutManager;
    private int pageIndex = 0;
    private BaseQuickAdapter<ArticleBean.DataBean.DatasBean, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    protected WanContract.Presenter initPresenter() {
        return new WanPresenter(this);
    }


    @Override
    protected void initView(View view) {
        initRecyclerView();
        presenter.getArticleList(pageIndex, true);
    }

    private void initRecyclerView() {
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout)
                .setOnStatusChildClickListener(v -> {
                    pageIndex = 0;
                    presenter.getArticleList(pageIndex, true);
                }).build();
        statusLayoutManager.showLoadingLayout();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<ArticleBean.DataBean.DatasBean, BaseViewHolder>(R.layout.item_article) {
            @Override
            protected void convert(BaseViewHolder helper, ArticleBean.DataBean.DatasBean item) {
                helper.setText(R.id.tv_title, item.getTitle());
                helper.itemView.setOnClickListener(v -> startActivity(new Intent(mContext, WebViewActivity.class)
                        .putExtra("title", item.getTitle())
                        .putExtra("url", item.getLink())));
            }
        });
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(() -> {
            pageIndex++;
            presenter.getArticleList(pageIndex, false);
        }, recyclerView);
        refreshLayout.setRefreshHeader(new PhoenixHeader(mContext));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageIndex = 0;
            presenter.getArticleList(pageIndex, true);
        });
    }

    @Override
    public void setData(ArticleBean bean, boolean isClear) {
        if (bean.getData().getDatas().size() == 0) {
            mAdapter.loadMoreEnd();
        } else {
            if (isClear) {
                mAdapter.setNewData(bean.getData().getDatas());
            } else {
                mAdapter.addData(bean.getData().getDatas());
            }
            mAdapter.loadMoreComplete();
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
