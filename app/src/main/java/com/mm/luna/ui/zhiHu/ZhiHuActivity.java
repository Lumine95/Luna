package com.mm.luna.ui.zhiHu;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.jaeger.library.StatusBarUtil;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.ZhiHuEntity;

import butterknife.BindView;

public class ZhiHuActivity extends BaseActivity<ZhiHuContract.Presenter> implements ZhiHuContract.View {

    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
        StatusBarUtil.setColorForDrawerLayout(this, drawer, Color.TRANSPARENT, 0);
        toolbar.setTitle(R.string.ZhiHuDaily);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
    }

    @Override
    public void setData(ZhiHuEntity zhiHuEntity, boolean isClear) {

    }
}
