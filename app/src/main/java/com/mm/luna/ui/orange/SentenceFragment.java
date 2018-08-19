package com.mm.luna.ui.orange;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.bean.SentenceBean;
import com.mm.luna.ui.common.ImagePreviewActivity;
import com.mm.luna.util.GlideUtil;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/8/16 17:19.
 */
public class SentenceFragment extends BaseFragment<OrangeContract.Presenter> implements OrangeContract.View {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SmartRefreshLayout refreshLayout;

    private int pageIndex = 0;
    private int type;
    private StatusLayoutManager statusLayoutManager;
    private BaseQuickAdapter<SentenceBean.ResultBean, BaseViewHolder> mAdapter;
    private int totalPage = 0;
    private List<String> imageUrls = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    protected OrangeContract.Presenter initPresenter() {
        return new OrangePresenter(this);
    }

    @Override
    protected void initView(View view) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout)
                .setOnStatusChildClickListener(v -> {
                    presenter.getSentenceList(pageIndex, true, type);
                }).build();
        statusLayoutManager.showLoadingLayout();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        presenter.getSentenceList(pageIndex, true, type);

        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<SentenceBean.ResultBean, BaseViewHolder>(R.layout.item_sentence) {
            @Override
            protected void convert(BaseViewHolder helper, SentenceBean.ResultBean item) {
                helper.setGone(R.id.tv_title, !TextUtils.isEmpty(item.getText()));
                helper.setText(R.id.tv_title, item.getText());
                GlideUtil.loadImage(mContext, item.getPic(), helper.getView(R.id.iv_card), R.mipmap.ic_default_bilibili_h);

                helper.itemView.setOnClickListener(v -> {
                    imageUrls.clear();
                    imageUrls.add(item.getPic());
                    ImagePreviewActivity.startImagePagerActivity(mActivity, imageUrls, 0);
                });
            }
        });
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(() -> {
            if (totalPage == pageIndex + 1) {
                mAdapter.loadMoreEnd();
            } else {
                pageIndex++;
                presenter.getSentenceList(pageIndex, false, type);
            }
        }, recyclerView);
        refreshLayout.setRefreshHeader(new PhoenixHeader(mContext));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageIndex = 0;
            presenter.getSentenceList(pageIndex, true, type);
        });
    }

    @Override
    public void setData(SentenceBean bean, boolean isClear) {
        if (bean.getList().size() == 0) {
            mAdapter.loadMoreEnd();
        } else {
            if (isClear) {
                totalPage = TextUtils.isEmpty(bean.getPage()) ? 0 : Integer.parseInt(bean.getPage());
                mAdapter.setNewData(bean.getList());
            } else {
                mAdapter.addData(bean.getList());
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
