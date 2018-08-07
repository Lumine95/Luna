package com.mm.luna.ui.douban;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.base.BasePresenter;
import com.mm.luna.bean.HotMovieBean;
import com.mm.luna.view.CompatNestedScrollView;
import com.mm.luna.view.MyRatingBar;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/8/2 16:43.
 */

public class MovieDetailActivity extends BaseActivity {
    @BindView(R.id.iv_movie) ImageView ivMovie;
    @BindView(R.id.iv_header_bg) ImageView ivHeaderBg;
    @BindView(R.id.rating_bar) MyRatingBar ratingBar;
    @BindView(R.id.tv_score) TextView tvScore;
    @BindView(R.id.tv_director) TextView tvDirector;
    @BindView(R.id.tv_actor) TextView tvActor;
    @BindView(R.id.tv_type) TextView tvType;
    @BindView(R.id.tv_date) TextView tvDate;
    @BindView(R.id.tv_country) TextView tvCountry;
    @BindView(R.id.scroll_view) CompatNestedScrollView scrollView;

    private HotMovieBean.SubjectsBean movieBean;

    @Override
    public void onLoading() {

    }

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
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            movieBean = (HotMovieBean.SubjectsBean) getIntent().getSerializableExtra("data");
            setData();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        Glide.with(this).load(movieBean.getImages().getLarge()).crossFade().into(ivMovie);
        tvDirector.setText(list2Str(movieBean.getDirectors()));
        tvActor.setText(list2Str(movieBean.getCasts()));
        tvType.setText(movieBean.getGenres().toString().replace("[", "").replace("]", "").replaceAll(",", "/"));
        tvDate.setText(movieBean.getYear());
        ratingBar.setStar((float) (movieBean.getRating().getAverage() / 2));
        tvScore.setText(movieBean.getRating().getAverage() + getString(R.string.score));
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

}
