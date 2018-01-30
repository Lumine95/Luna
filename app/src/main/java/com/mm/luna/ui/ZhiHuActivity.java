package com.mm.luna.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.ZhiHuEntity;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZhiHuActivity extends BaseActivity<ZhiHuContract.Presenter> implements ZhiHuContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private List<ZhiHuEntity.StoriesBean> listData = new ArrayList<>();
    private String currentDate;

    private ZhiHuAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public ZhiHuContract.Presenter initPresenter() {
        return new ZhiHuPresenter(this);
    }

    @Override
    public void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter.getTodayData();
        mAdapter = new ZhiHuAdapter(R.layout.item_zhihu, listData);
        recyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Log.d("", "onItemClick: " + position);
            startActivity(new Intent(this, TestActivity.class).putExtra("id", listData.get(position).getId()));
        });
        mAdapter.setOnLoadMoreListener(() -> presenter.getBeforeData(currentDate), recyclerView);
        refreshLayout.setRefreshHeader(new PhoenixHeader(this));

        toolbar.setTitle("知乎日报");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void setData(ZhiHuEntity zhiHuEntity) {
        listData.addAll(zhiHuEntity.getStories());
        currentDate = zhiHuEntity.getDate();
        mAdapter.addData(zhiHuEntity.getStories());
        mAdapter.loadMoreComplete();
    }
}
