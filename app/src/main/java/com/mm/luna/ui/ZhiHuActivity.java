package com.mm.luna.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.ZhiHuEntity;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhiHuActivity extends BaseActivity<ZhiHuContract.Presenter> implements ZhiHuContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private List<ZhiHuEntity.StoriesBean> listData = new ArrayList<>();


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
        presenter.getData();
        mAdapter = new ZhiHuAdapter(R.layout.item_zhihu, listData);
        recyclerView.setAdapter(mAdapter);

        refreshLayout.setRefreshHeader(new PhoenixHeader(this));
    }

    @Override
    public void setData(List<ZhiHuEntity.StoriesBean> listData) {
        listData.addAll(listData);
        mAdapter = new ZhiHuAdapter(R.layout.item_zhihu, listData);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
