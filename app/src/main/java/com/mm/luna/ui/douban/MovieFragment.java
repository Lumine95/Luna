package com.mm.luna.ui.douban;

import android.view.View;

import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.bean.DoubanEntity;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;

/**
 * Created by ZMM on 2018/5/3  23:30.
 */

public class MovieFragment extends BaseFragment<DoubanContract.Presenter> implements DoubanContract.View {

    private StatusLayoutManager statusLayoutManager;

    @Override
    public void setData(DoubanEntity entity) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    protected DoubanContract.Presenter initPresenter() {
        return new DoubanPresenter(this);
    }

    @Override
    protected void initView(View view) {
//        statusLayoutManager = new StatusLayoutManager.Builder(content)
//                .setOnStatusChildClickListener(v -> {
//
//                }).build();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onError() {

    }
}
