package com.mm.luna.ui.douban;

import android.view.View;

import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.bean.DoubanEntity;

/**
 * Created by ZMM on 2018/5/3  23:30.
 */

public class MovieFragment extends BaseFragment<DoubanContract.Presenter> implements DoubanContract.View {
    @Override
    public void setData(DoubanEntity entity) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    protected DoubanContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(View view) {

    }
}
