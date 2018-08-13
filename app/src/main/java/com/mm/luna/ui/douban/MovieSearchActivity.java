package com.mm.luna.ui.douban;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.library.utils.U;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.HotMovieBean;
import com.mm.luna.bean.MovieDetailBean;
import com.mm.luna.ui.adapter.MovieAdapter;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/8/12  19:39.
 */
public class MovieSearchActivity extends BaseActivity<DoubanContract.Presenter> implements DoubanContract.View {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.refresh_layout) SmartRefreshLayout refreshLayout;
    @BindView(R.id.search_view) MaterialSearchView searchView;

    private StatusLayoutManager statusLayoutManager;
    private int position = 3;
    private int pageIndex = 0;
    private MovieAdapter mAdapter;
    private List<HotMovieBean.SubjectsBean> listData = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_search;
    }

    @Override
    public DoubanContract.Presenter initPresenter() {
        return new DoubanPresenter(this);
    }

    @Override
    public void initView() {
        setStatusBarColor();
        initToolbar();
        initSearchView();
        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout)
                .setOnStatusChildClickListener(v -> {
                    presenter.getMovieList(pageIndex, true, position);
                }).build();
        statusLayoutManager.showLoadingLayout();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        presenter.getMovieList(pageIndex, true, position);

        mAdapter = new MovieAdapter(R.layout.item_movie, listData, position);
        recyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(() -> {
            pageIndex++;
            presenter.getMovieList(pageIndex, false, position);
        }, recyclerView);
        refreshLayout.setRefreshHeader(new PhoenixHeader(this));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageIndex = 0;
            presenter.getMovieList(pageIndex, true, position);
        });
    }

    private void initSearchView() {
        searchView.showVoice(true);
        searchView.setVoiceSearch(true);
        searchView.setCursorDrawable(R.drawable.color_cursor_white);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });
    }

    private void initToolbar() {
        U.showToast(isVoiceAvailable() + "");
        toolbar.setTitle("电影搜索");
        toolbar.setNavigationIcon(R.mipmap.ic_left_arrow_white);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void setData(HotMovieBean bean, boolean isClear) {
        if (bean.getSubjects().size() == 0) {
            mAdapter.loadMoreEnd();
        } else {
            if (isClear) {
                listData.clear();
                mAdapter.setNewData(bean.getSubjects());
            } else {
                mAdapter.addData(bean.getSubjects());
            }
            mAdapter.loadMoreComplete();
        }
        listData.addAll(bean.getSubjects());
        refreshLayout.finishRefresh(true);
    }

    @Override
    public void loadMovieDetail(MovieDetailBean bean) {

    }

    @Override
    public void onFinish() {
        statusLayoutManager.showSuccessLayout();
    }

    @Override
    public void onError() {
        statusLayoutManager.showErrorLayout();
    }

    private boolean isVoiceAvailable() {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        return activities.size() == 0;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_movie, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
