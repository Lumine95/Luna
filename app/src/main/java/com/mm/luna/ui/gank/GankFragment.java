package com.mm.luna.ui.gank;

import android.view.View;

import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.bean.ZhiHuEntity;
import com.mm.luna.ui.zhihu.ZhiHuContract;
import com.mm.luna.ui.zhihu.ZhiHuPresenter;

/**
 * Created by ZMM on 2018/5/3 11:43.
 */

public class GankFragment extends BaseFragment<ZhiHuContract.Presenter> implements ZhiHuContract.View {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_douban;
    }


    @Override
    protected ZhiHuContract.Presenter initPresenter() {
        return new ZhiHuPresenter(this);
    }


    @Override
    protected void initView(View view) {

    }

    @Override
    public void setData(ZhiHuEntity zhiHuEntity, boolean isClear) {

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
