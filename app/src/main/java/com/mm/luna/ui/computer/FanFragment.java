package com.mm.luna.ui.computer;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.library.view.LabelView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.bean.CFanBean;
import com.mm.luna.ui.common.WebViewActivity;
import com.mm.luna.util.GlideUtil;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/8/22 17:11.
 */
public class FanFragment extends BaseFragment<FanContract.Presenter> implements FanContract.View {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SmartRefreshLayout refreshLayout;

    private int pageIndex = 1;
    private int type;
    private StatusLayoutManager statusLayoutManager;
    private BaseQuickAdapter<CFanBean, BaseViewHolder> mAdapter;
    private FanHeaderView headerView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    protected FanContract.Presenter initPresenter() {
        return new FanPresenter(this);
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

        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<CFanBean, BaseViewHolder>(R.layout.item_computer_fan) {
            @Override
            protected void convert(BaseViewHolder helper, CFanBean item) {
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_intro, item.getIntro());
                ((LabelView) helper.getView(R.id.label_view)).setText(item.getCategory());
                GlideUtil.loadImage(mContext, item.getImage(), helper.getView(R.id.iv_card), R.mipmap.ic_default_bilibili);
                helper.itemView.setOnClickListener(v -> startActivity(new Intent(mContext, WebViewActivity.class)
                        .putExtra("title", getString(R.string.computer_fan))
                        .putExtra("url", item.getUrl())));
            }
        });
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(() -> {
            if (pageIndex == 50) {
                mAdapter.loadMoreEnd();
            } else {
                pageIndex++;
                presenter.getArticleList(pageIndex, false, type);
            }
        }, recyclerView);
        refreshLayout.setRefreshHeader(new DeliveryHeader(mContext));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageIndex = 1;
            presenter.getArticleList(pageIndex, true, type);
        });
    }

    @Override
    public void setData(List<CFanBean> list, boolean isClear) {
        if (list.size() == 0) {
            mAdapter.loadMoreEnd();
        } else {
            if (isClear) {
                mAdapter.setNewData(list);
            } else {
                mAdapter.addData(list);
            }
            mAdapter.loadMoreComplete();
        }
        refreshLayout.finishRefresh(true);
    }

    @Override
    public void setBannerData(List<CFanBean> bannerList) {
        if (bannerList.size() > 0 && headerView == null) {
            mAdapter.addHeaderView(headerView = new FanHeaderView(mContext, bannerList));
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

    @Override
    public void onStart() {
        super.onStart();
        if (headerView != null) {
            headerView.banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (headerView != null) {
            headerView.banner.stopAutoPlay();
        }
    }
}
