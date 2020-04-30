package com.mm.luna.ui.gank;

import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.bean.GankBean;
import com.mm.luna.ui.common.ImagePreviewActivity;
import com.mm.luna.ui.common.WebViewActivity;
import com.mm.luna.util.GlideUtil;
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
    private List<String> imageUrls = new ArrayList<>();
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

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(type == 3 ? staggeredGridLayoutManager : layoutManager);

        presenter.getArticleList(pageIndex, true, type);
        recyclerView.setAdapter(mAdapter = type == 3 ? new BaseQuickAdapter<GankBean.ResultsBean, BaseViewHolder>(R.layout.item_girl) {
                    @Override
                    protected void convert(BaseViewHolder helper, GankBean.ResultsBean item) {
                        GlideUtil.loadImage(mContext, item.getUrl(), helper.getView(R.id.iv_girl), R.mipmap.ic_default_bilibili);
                        helper.itemView.setOnClickListener(v -> ImagePreviewActivity.startImagePagerActivity(mActivity, imageUrls, helper.getLayoutPosition()));
                    }
                } : new BaseQuickAdapter<GankBean.ResultsBean, BaseViewHolder>(R.layout.item_gank) {
                    @Override
                    protected void convert(BaseViewHolder helper, GankBean.ResultsBean item) {
                        helper.setText(R.id.tv_title, item.getDesc());
                        helper.setText(R.id.tv_author, item.getWho());
                        helper.setText(R.id.tv_date, item.getPublishedAt());
                        helper.itemView.setOnClickListener(v -> startActivity(new Intent(mContext, WebViewActivity.class)
                                .putExtra("title", item.getDesc())
                                .putExtra("url", item.getUrl())));
                    }
                }
        );
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(() -> {
            pageIndex++;
            presenter.getArticleList(pageIndex, false, type);
        }, recyclerView);
        refreshLayout.setRefreshHeader(new PhoenixHeader(mContext));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageIndex = 1;
            presenter.getArticleList(pageIndex, true, type);
        });
    }

    @Override
    public void setData(GankBean bean, boolean isClear) {
        for (GankBean.ResultsBean data : bean.getResults()) {
            imageUrls.add(data.getUrl());
        }
        if (bean.getResults().size() == 0) {
            mAdapter.loadMoreEnd();
        } else {
            if (isClear) {
                mAdapter.setNewData(bean.getResults());
            } else {
                mAdapter.addData(bean.getResults());
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
