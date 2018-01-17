package com.mm.luna.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.ZhiHuEntity;

import java.util.ArrayList;
import java.util.List;

public class ZhiHuActivity extends BaseActivity<ZhiHuContract.Presenter> implements ZhiHuContract.View {

    private List<ZhiHuEntity.StoriesBean> listData = new ArrayList<>();

    //    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
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
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter.getData();
        mAdapter = new ZhiHuAdapter(R.layout.item_zhihu, listData);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setData(List<ZhiHuEntity.StoriesBean> listData) {
        listData.addAll(listData);
//        mAdapter = new ZhiHuAdapter(R.layout.item_zhihu, listData);
//        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
