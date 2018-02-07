package com.mm.luna.ui.zhihu;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import com.mm.luna.R;
import com.mm.luna.api.Api;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.base.BasePresenter;
import com.mm.luna.bean.ZhiHuDetailEntity;
import com.mm.luna.util.HtmlUtils;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZMM on 2018/1/26.
 */

public class ZhiHuDetailActivity extends BaseActivity {


    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_source)
    TextView tvSource;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.nested_view)
    NestedScrollView nestedView;
    @BindView(R.id.iv_star)
    ImageView ivStar;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tap_bar)
    TapBarMenu tapBar;

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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tapBar.setOnClickListener(v -> tapBar.toggle());
        int id = getIntent().getIntExtra("id", 0);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        collapsingToolbarLayout.setTitle("知乎日报");
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        Api.getInstance().getNewsDetail(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> ShowLoadingDialog("正在加载"))
                .map(ZhiHuDetailEntity -> ZhiHuDetailEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ZhiHuDetailEntity -> {
                    dismissLoadingDialog();
                    setData(ZhiHuDetailEntity);
                }, throwable -> dismissLoadingDialog());

    }

    private void setData(ZhiHuDetailEntity entity) {
        Glide.with(this).load(entity.getImage()).crossFade().into(ivHeader);
        tvTitle.setText(entity.getTitle());
        tvSource.setText(entity.getImage_source());
        webView.setDrawingCacheEnabled(true);
        String htmlData = HtmlUtils.createHtmlData(entity.getBody(), entity.getCss(), entity.getJs());
        webView.loadData(htmlData, HtmlUtils.MIME_TYPE, HtmlUtils.ENCODING);
    }

}
