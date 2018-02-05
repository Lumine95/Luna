package com.mm.luna.ui;


import android.util.Log;

import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.base.BasePresenter;
import com.orhanobut.logger.Logger;

import org.yczbj.ycvideoplayerlib.VideoPlayer;
import org.yczbj.ycvideoplayerlib.VideoPlayerController;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ZMM on 2018/2/4.
 */

public class TestActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                getJson();
            }
        }).start();
        // videoPlayer();
    }

    private void getJson() {
       // String url = "https://raw.githubusercontent.com/moonlove95/Luna/master/json/violet.txt";

        try {
            URL url = new URL("https://raw.githubusercontent.com/moonlove95/Luna/master/json/violet.txt");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024 * 8];
            int current = 0;
            while ((current = bis.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, current);
            }

            String str = new String(buffer.toByteArray(), "utf-8");
            String[] split = str.split(",");
            Logger.d(split);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("", "initView:--- " + e.getMessage());
        }

    }

    private void videoPlayer() {
        VideoPlayer videoPlayer = findViewById(R.id.video_player);
        String videoUrl = "https://vd3.bdstatic.com/mda-iaqigc561xrkf216/mda-iaqigc561xrkf216.mp4";

        //设置播放类型
        // IjkPlayer or MediaPlayer
        videoPlayer.setPlayerType(VideoPlayer.TYPE_NATIVE);
        //网络视频地址
        //设置视频地址和请求头部
        videoPlayer.setUp(videoUrl, null);
        //是否从上一次的位置继续播放
        videoPlayer.continueFromLastPosition(true);
        //设置播放速度
        videoPlayer.setSpeed(1.0f);
        //创建视频控制器
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setTitle("办快来围观拉，自定义视频播放器可以播放视频拉");
        //设置视频时长
        controller.setLength(98000);
        //设置5秒不操作后则隐藏头部和底部布局视图
        controller.setHideTime(5000);
        controller.setImage(R.mipmap.ic_launcher_round);
        // ImageUtil.loadImgByPicasso(this, R.drawable.image_default, R.drawable.image_default, controller.imageView());
        //设置视频控制器
        videoPlayer.setController(controller);
    }
}
