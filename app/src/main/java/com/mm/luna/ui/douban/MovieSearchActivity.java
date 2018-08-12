package com.mm.luna.ui.douban;

import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.HotMovieBean;
import com.mm.luna.bean.MovieDetailBean;

/**
 * Created by ZMM on 2018/8/12  19:39.
 */
public class MovieSearchActivity extends BaseActivity<DoubanContract.Presenter> implements  DoubanContract.View {
    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_search;
    }

    @Override
    public DoubanContract.Presenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setData(HotMovieBean bean, boolean isClear) {

    }

    @Override
    public void loadMovieDetail(MovieDetailBean bean) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onError() {

    }
}
