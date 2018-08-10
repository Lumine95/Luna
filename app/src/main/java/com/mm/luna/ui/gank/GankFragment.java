package com.mm.luna.ui.gank;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/8/10 15:32.
 */
public class GankFragment extends BaseFragment<GankContract.Presenter> implements GankContract.View {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SmartRefreshLayout refreshLayout;
    private int type;

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

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onError() {

    }
}
