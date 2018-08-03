package com.mm.luna.ui.douban;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;

import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.base.BasePresenter;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/8/2 16:43.
 */

public class MovieDetailActivity extends BaseActivity {
    @BindView(R.id.iv_movie) ImageView ivMovie;

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

    }

    public static void start(Activity context, ImageView imageView) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        // intent.putExtra(INTENT_KEY_MOVIE_SUBJECTBEAN, subjectsBean);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (context, imageView, "test");
        //与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }
}
