package com.mm.luna.ui.today;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.bean.BingBean;
import com.mm.luna.bean.FictionBean;
import com.mm.luna.bean.HomeBean;
import com.mm.luna.ui.common.ImagePreviewActivity;
import com.mm.luna.util.GlideUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2019/2/26 17:49.
 */
public class TodayFragment extends BaseFragment<TodayContract.Presenter> implements TodayContract.View {
    @BindView(R.id.iv_english) ImageView ivEnglish;
    @BindView(R.id.tv_sentence) TextView tvSentence;
    @BindView(R.id.tv_translation) TextView tvTranslation;
    @BindView(R.id.iv_horn) ImageView ivHorn;
    @BindView(R.id.tv_digest) TextView tvDigest;
    @BindView(R.id.tv_count) TextView tvCount;
    @BindView(R.id.tv_from) TextView tvFrom;
    @BindView(R.id.card_today_article) CardView cardTodayArticle;
    @BindView(R.id.iv_bing) ImageView ivBing;
    @BindView(R.id.iv_copyright) ImageView ivCopyright;
    @BindView(R.id.tv_one_type) TextView tvOneType;
    @BindView(R.id.iv_one) ImageView ivOne;
    @BindView(R.id.tv_one_number) TextView tvOneNumber;
    @BindView(R.id.tv_one_sentence) TextView tvOneSentence;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_today;
    }

    @Override
    protected TodayPresenter initPresenter() {
        return new TodayPresenter(this);
    }

    @Override
    protected void initView(View view) {
        presenter.getTodayEnglish();
        presenter.getTodayArticle();
        presenter.getBingWallpaper();
        presenter.getOneImage();
    }

    @Override
    public void showTodayEnglish(HomeBean bean) {
        tvSentence.setText(bean.getContent());
        tvTranslation.setText(bean.getNote());
        GlideUtil.loadImage(mContext, bean.getPicture(), ivEnglish, R.mipmap.ic_default_bilibili_h);
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(bean.getFenxiang_img());
        ivEnglish.setOnClickListener(v -> ImagePreviewActivity.startImagePagerActivity(mActivity, imageUrls, 0));
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(bean.getTts());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        tvSentence.setOnClickListener(view -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        });
        ivHorn.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void showTodayArticle(FictionBean bean) {
        tvCount.setText(String.format("%s%s%s", getString(R.string.full_text), bean.getData().getWc(), getString(R.string.word)));
        tvDigest.setText(String.format("“%s……”", bean.getData().getDigest()));
        tvFrom.setText(String.format("—— %s《%s》", bean.getData().getAuthor(), bean.getData().getTitle()));
        cardTodayArticle.setOnClickListener(v -> startActivity(new Intent(mContext, DailyArticleActivity.class)));
    }

    @Override
    public void ShowBingWallpaper(BingBean bean) {
        BingBean.ImagesBean imagesBean = bean.getImages().get(0);
        GlideUtil.loadImage(mContext, "https://cn.bing.com" + imagesBean.getUrlbase() + "_1280x720.jpg", ivBing, R.mipmap.ic_default_bilibili_h);
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://cn.bing.com" + imagesBean.getUrl());
        ivBing.setOnClickListener(v -> ImagePreviewActivity.startImagePagerActivity(mActivity, imageUrls, 0));
        //  tvCopyright.setText(imagesBean.getCopyright());
        ivCopyright.setOnClickListener(v -> {
            new AlertDialog.Builder(mContext)
                    .setTitle("必应壁纸")
                    .setMessage(imagesBean.getCopyright())
                    .setPositiveButton(getString(R.string.sure), (dialog, which) -> {
                        dialog.dismiss();
                    }).create().show();
        });
    }

    @Override
    public void ShowOneImage(String[] array) {
        GlideUtil.loadImage(mContext, array[0], ivOne, R.mipmap.ic_default_bilibili_h);
        tvOneType.setText(array[1]);
        tvOneSentence.setText(array[2]);
        tvOneNumber.setText(array[3]);

        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(array[0]);
        ivOne.setOnClickListener(v -> ImagePreviewActivity.startImagePagerActivity(mActivity, imageUrls, 0));
    }
}
