package com.mm.luna.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;
import com.mm.luna.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ZMM on 2018/1/17.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    public P presenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置透明状态栏
        //StatusBarUtils.setTransparent(this);
        StatusBarUtil.setTranslucent(this);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        presenter = initPresenter();
        initView();
    }

    public abstract int getLayoutId();

    public abstract P initPresenter();

    public abstract void initView();

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
    }
    @Override
    protected void onDestroy() {
        unbinder.unbind();
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
        super.onDestroy();
    }
}
