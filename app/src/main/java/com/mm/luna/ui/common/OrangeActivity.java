package com.mm.luna.ui.common;

import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.base.BasePresenter;

/**
 * Created by ZMM on 2018/8/18  10:08.
 */
public class OrangeActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_orange;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setStatusBarColor();
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onError() {

    }
}
