package com.mm.luna.ui;

import com.michaldrabik.tapbarmenulib.TapBarMenu;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.base.BasePresenter;

/**
 * Created by ZMM on 2018/1/26.
 */

public class ZhiHuDetailActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_zhihu_detail;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        TapBarMenu tapBar = findViewById(R.id.tap_bar);
        tapBar.setOnClickListener(v -> tapBar.toggle());
    }
}
