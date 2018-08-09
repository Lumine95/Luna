package com.mm.luna.ui.douban;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.HotMovieBean;
import com.mm.luna.bean.MovieDetailBean;
import com.mm.luna.ui.common.WebViewActivity;
import com.mm.luna.util.DisplayUtils;
import com.mm.luna.util.GlideUtil;
import com.mm.luna.view.CompatNestedScrollView;
import com.mm.luna.view.MyRatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.mm.luna.util.StatusBarUtils.getStatusBarHeight;

/**
 * Created by ZMM on 2018/8/2 16:43.
 */

public class MovieDetailActivity extends BaseActivity<DoubanContract.Presenter> implements DoubanContract.View {
    @BindView(R.id.iv_movie) ImageView ivMovie;
    @BindView(R.id.iv_header_bg) ImageView ivHeaderBg;
    @BindView(R.id.rating_bar) MyRatingBar ratingBar;
    @BindView(R.id.tv_score) TextView tvScore;
    @BindView(R.id.tv_director) TextView tvDirector;
    @BindView(R.id.tv_actor) TextView tvActor;
    @BindView(R.id.tv_type) TextView tvType;
    @BindView(R.id.tv_date) TextView tvDate;
    @BindView(R.id.tv_country) TextView tvCountry;
    @BindView(R.id.tv_alias) TextView tvAlias;
    @BindView(R.id.tv_intro) TextView tvIntro;
    @BindView(R.id.scroll_view) CompatNestedScrollView scrollView;
    @BindView(R.id.iv_toolbar_bg) ImageView ivToolbarBg;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private HotMovieBean.SubjectsBean movieBean;

    @Override
    public void onFinish() {

    }

    @Override
    public void onError() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public DoubanContract.Presenter initPresenter() {
        return new DoubanPresenter(this);
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            movieBean = (HotMovieBean.SubjectsBean) getIntent().getSerializableExtra("data");
            presenter.getMovieDetail(movieBean.getId());
            setData();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        toolbar.setTitle(movieBean.getTitle());
        toolbar.setNavigationIcon(R.mipmap.ic_left_arrow_white);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> ActivityCompat.finishAfterTransition(this));
        scrollView.bindAlphaView(ivToolbarBg);

        Glide.with(this).load(movieBean.getImages().getLarge()).crossFade().into(ivMovie);
        tvDirector.setText(list2Str(movieBean.getDirectors()));
        tvActor.setText(list2Str(movieBean.getCasts()));
        tvType.setText(extractStrList(movieBean.getGenres()));
        tvDate.setText(movieBean.getYear());
        ratingBar.setStar((float) (movieBean.getRating().getAverage() / 2));
        tvScore.setText(movieBean.getRating().getAverage() + getString(R.string.score));
        DisplayUtils.displayBlurImg(this, movieBean.getImages().getLarge(), ivHeaderBg);
        DisplayUtils.displayBlurImg(this, movieBean.getImages().getLarge(), ivToolbarBg);
        int headerBgHeight;
        headerBgHeight = toolbar.getLayoutParams().height + getStatusBarHeight(this);
        // 使背景图向上移动到图片的最低端，保留（toolbar+状态栏）的高度
        // 实际上此时ivToolbarBg高度还是330dp，只是除了toolbar外，剩下部分是透明状态
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams)
                ivToolbarBg.getLayoutParams();
        int marginTop = ivToolbarBg.getLayoutParams().height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        List<HotMovieBean.SubjectsBean.CastsBean> personList = new ArrayList<>();
        personList.addAll(movieBean.getDirectors());
        personList.addAll(movieBean.getCasts());
        recyclerView.setAdapter(new BaseQuickAdapter<HotMovieBean.SubjectsBean.CastsBean, BaseViewHolder>(R.layout.item_cast, personList) {
            @Override
            protected void convert(BaseViewHolder helper, HotMovieBean.SubjectsBean.CastsBean item) {
                helper.setText(R.id.tv_name, item.getName());
                helper.setText(R.id.tv_type, helper.getAdapterPosition() < movieBean.getDirectors().size() ? "导演" : "演员");
                if (item.getAvatars() != null) {
                    GlideUtil.loadImage(mContext, item.getAvatars().getMedium(), helper.getView(R.id.iv_avatar), R.mipmap.ic_image_default);
                }
                helper.itemView.setOnClickListener(v -> {
                    if (!TextUtils.isEmpty(item.getAlt())) {
                        startActivity(new Intent(mContext, WebViewActivity.class)
                                .putExtra("title", item.getName())
                                .putExtra("url", item.getAlt()));
                    }
                });
            }
        });
    }

    public static void start(Activity context, HotMovieBean.SubjectsBean bean, ImageView imageView) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("data", bean);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (context, imageView, context.getString(R.string.transition_movie_img));
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

    private String list2Str(List<HotMovieBean.SubjectsBean.CastsBean> list) {
        if (list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (HotMovieBean.SubjectsBean.CastsBean bean : list) {
            sb.append(bean.getName()).append("/");
        }
        return sb.substring(0, sb.length() - 1);
    }

    @OnClick(R.id.iv_movie)
    public void onViewClicked() {
        startActivity(new Intent(this, WebViewActivity.class)
                .putExtra("title", movieBean.getTitle())
                .putExtra("url", movieBean.getAlt()));
    }

    @Override
    public void setData(HotMovieBean bean, boolean isClear) {
    }

    @Override
    public void loadMovieDetail(MovieDetailBean bean) {
        tvCountry.setText(extractStrList(bean.getCountries()));
        tvAlias.setText(extractStrList(bean.getAka()));
        tvIntro.setText(bean.getSummary());
    }

    private String extractStrList(List<String> stringList) {
        return stringList.toString().replace("[", "").replace("]", "").replaceAll(",", "/").replaceAll(" ", "");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityCompat.finishAfterTransition(this);
        }
        return true;
    }
}
