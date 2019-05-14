package com.mm.luna.ui.nba;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.NBABean;
import com.mm.luna.util.StatusBarUtils;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;
import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2019/1/12  13:07.
 */
public class LiveDetailActivity extends BaseActivity<NBAContract.Presenter> implements NBAContract.View {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.video_player) StandardGSYVideoPlayer videoPlayer;
    @BindView(R.id.refresh_layout) SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_status) TextView tvStatus;
    private NBABean data;
    OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private StatusLayoutManager statusLayoutManager;
    private List<NBABean> beanList = new ArrayList<>();
    private String title;
    private MenuItem itemSwitch;

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_detail;
    }

    @Override
    public NBAContract.Presenter initPresenter() {
        return new NBAPresenter(this);
    }

    @Override
    public void initView() {
        data = (NBABean) getIntent().getSerializableExtra("data");
        setStatusBarColor();
        toolbar.setTitle(title = data.getVisitingTeam() + " vs " + data.getHomeTem());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        presenter.getLiveSignalList(data.getUrl());
        initVideoPlayer();

        statusLayoutManager = new StatusLayoutManager.Builder(refreshLayout).setOnStatusChildClickListener(v -> {
            presenter.getLiveSignalList(data.getUrl());
        }).build();
        statusLayoutManager.showLoadingLayout();
        refreshLayout.setRefreshHeader(new DropBoxHeader(this));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            presenter.getLiveSignalList(data.getUrl());
        });
    }

    private void initVideoPlayer() {
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.ic_default_bilibili_h);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.GONE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                //.setUrl(url)
                .setCacheWithPlay(false)
                .setVideoTitle(title)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                        (findViewById(android.R.id.content)).setPadding(0, StatusBarUtils.getStatusBarHeight(LiveDetailActivity.this), 0, 0);
                    }
                }).setLockClickListener((view, lock) -> {
            if (orientationUtils != null) {
                //配合下方的onConfigurationChanged
                orientationUtils.setEnable(!lock);
            }
        }).build(videoPlayer);
        videoPlayer.getFullscreenButton().setOnClickListener(v -> {
            //直接横屏
            orientationUtils.resolveByClick();
            StatusBarUtil.hideFakeStatusBarView(this);
            (findViewById(android.R.id.content)).setPadding(0, 0, 0, 0);
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏StatusBar
            videoPlayer.startWindowFullscreen(LiveDetailActivity.this, true, true);
        });

    }

    @Override
    public void setData(boolean isClear, List<NBABean> beanList) {
        this.beanList = beanList;
        refreshLayout.finishRefresh(true);
        if (beanList.size() > 0) {
            itemSwitch.setVisible(true);
            tvStatus.setVisibility(View.GONE);
            videoPlayer.setUp(beanList.get(0).getUrl(), true, title);
            videoPlayer.startPlayLogic();
        } else {
            itemSwitch.setVisible(false);
            tvStatus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        itemSwitch = menu.findItem(R.id.action_switch);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch:
                switchSignalSource();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchSignalSource() {
        String[] signalArr = new String[beanList.size()];
        for (int i = 0; i < beanList.size(); i++) {
            signalArr[i] = beanList.get(i).getTitle();
        }
        new AlertDialog.Builder(this)
                .setTitle("切换信号源")
                .setItems(signalArr, (dialog, which) -> {
                    videoPlayer.setUp(beanList.get(which).getUrl(), true, title);
                    videoPlayer.startPlayLogic();
                })
                .create().show();
    }

    @Override
    public void onFinish() {
        statusLayoutManager.showSuccessLayout();
    }

    @Override
    public void onError() {
        statusLayoutManager.showErrorLayout();
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
//        videoPlayer.getCurrentPlayer().onVideoPause();
        GSYVideoManager.onPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
//        videoPlayer.getCurrentPlayer().onVideoResume(false);
        GSYVideoManager.onResume();
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (isPlay && videoPlayer != null) {
//            videoPlayer.getCurrentPlayer().release();
//        }
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 如果旋转了就全屏
        if (isPlay && !isPause) {
            videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }
}
