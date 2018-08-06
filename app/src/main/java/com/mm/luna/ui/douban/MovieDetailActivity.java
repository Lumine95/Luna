package com.mm.luna.ui.douban;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;

import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.base.BasePresenter;
import com.mm.luna.bean.HotMovieBean;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/8/2 16:43.
 */

public class MovieDetailActivity extends BaseActivity {
    @BindView(R.id.iv_movie) ImageView ivMovie;
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
        }
    }

    public static void start(Activity context, HotMovieBean.SubjectsBean bean, ImageView imageView) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("data", bean);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (context, imageView, context.getString(R.string.transition_movie_img));
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }
}
