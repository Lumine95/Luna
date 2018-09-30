package com.mm.luna.ui.violet;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.ComicEntity;

import org.yczbj.ycvideoplayerlib.VideoPlayer;
import org.yczbj.ycvideoplayerlib.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.VideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/2/4.
 */

public class VioletActivity extends BaseActivity<VioletContract.Presenter> implements VioletContract.View {

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.video_player)
    VideoPlayer videoPlayer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private VioletAdapter mAdapter;

    List<ComicEntity> listData = new ArrayList<>();
    private int tag = 0;
    private String cover;

    @Override
    public int getLayoutId() {
        return R.layout.activity_violet;
    }

    @Override
    public VioletContract.Presenter initPresenter() {
        return new VioletPresenter(this);
    }

    @Override
    public void initView() {
        tag = getIntent().getIntExtra("tag", 0);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        presenter.getVideoList();
        rvVideo.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new VioletAdapter(R.layout.item_violet, listData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rvVideo.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, v, position) -> videoPlayer(position, true));
        // videoPlayer();
    }

    @Override
    public void setData(ComicEntity entity) {
        cover = entity.getData().get(tag).getCover();
        toolbar.setTitle(entity.getData().get(tag).getVideoName());
        listData = entity.getData().get(tag).getVideoList();
        runOnUiThread(() -> {
            videoPlayer(0, false);
            mAdapter.addData(entity.getData().get(tag).getVideoList());
        });
    }

    private void videoPlayer(int position, boolean isPlay) {
        VideoPlayerManager.instance().releaseVideoPlayer();
        //设置播放类型 :IjkPlayer or MediaPlayer
        videoPlayer.setPlayerType(VideoPlayer.TYPE_IJK);
        //网络视频地址
        //设置视频地址和请求头部
        videoPlayer.setUp(listData.get(position).getVideoUrl(), null);
        //是否从上一次的位置继续播放
        videoPlayer.continueFromLastPosition(true);
        //设置播放速度
        videoPlayer.setSpeed(1.0f);
        //创建视频控制器
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setTitle("第" + (position + 1) + "话");
        //设置视频时长
        // controller.setLength(0);
        //设置5秒不操作后则隐藏头部和底部布局视图
        controller.setHideTime(4000);
        //controller.setImage(R.mipmap.violet);
        Glide.with(this).load(cover).crossFade().centerCrop().into(controller.imageView());
        //设置视频控制器
        videoPlayer.setController(controller);
        if (isPlay) {
            videoPlayer.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) return;
        super.onBackPressed();
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onError() {

    }
}
