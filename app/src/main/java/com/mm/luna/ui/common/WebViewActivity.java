package com.mm.luna.ui.common;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebView;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.base.BasePresenter;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/8/8 13:54.
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.container) AgentWebView container;
    private AgentWeb mAgentWeb;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        setStatusBarColor();
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        initToolbar(title);
        loadUrl(url);
    }

    private void loadUrl(String url) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
    }

    private void initToolbar(String title) {
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.mipmap.ic_left_arrow_white);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onError() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
