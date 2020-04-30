package com.mm.luna.ui.zhihu;

import android.annotation.SuppressLint;
import android.graphics.Color;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
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
import com.mm.luna.util.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;
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
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tap_bar)
    TapBarMenu tapBar;
    private String title;
    private String shareUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhihu_detail;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @SuppressLint("CheckResult")
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
                .map(ZhiHuDetailEntity -> ZhiHuDetailEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ZhiHuDetailEntity -> {
                    onFinish();
                    setData(ZhiHuDetailEntity);
                }, throwable -> onError());
    }

    private void setData(ZhiHuDetailEntity entity) {
        Glide.with(this).load(entity.getImage()).crossFade().into(ivHeader);
        tvTitle.setText(title = entity.getTitle());
        tvSource.setText(entity.getImage_source());
        webView.setDrawingCacheEnabled(true);
        String htmlData = HtmlUtils.createHtmlData(entity.getBody(), entity.getCss(), entity.getJs());
        webView.loadData(htmlData, HtmlUtils.MIME_TYPE, HtmlUtils.ENCODING);
        shareUrl = entity.getShare_url();
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onError() {

    }

    @OnClick({R.id.iv_star, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_star:
                ivStar.setSelected(!ivStar.isSelected());
                break;
            case R.id.iv_share:
                SystemUtil.share(this, title, shareUrl);
                tapBar.toggle();
                break;
        }
    }
}
