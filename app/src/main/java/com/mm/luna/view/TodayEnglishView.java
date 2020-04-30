package com.mm.luna.view;

import android.app.Activity;
import android.app.Dialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mm.luna.R;
import com.mm.luna.bean.HomeBean;
import com.mm.luna.ui.common.ImagePreviewActivity;
import com.mm.luna.util.GlideUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZMM on 2018/10/1  20:24.
 */
public class TodayEnglishView extends Dialog {

    public TodayEnglishView(@NonNull Activity context, HomeBean bean) {
        super(context);
        setContentView(R.layout.dialog_today_english);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        GlideUtil.loadImage(context, bean.getPicture(), findViewById(R.id.iv_image), R.mipmap.ic_default_bilibili_h);
        TextView tvSentence = findViewById(R.id.tv_sentence);
        //   tvSentence.setText(bean.getContent());
        ((TextView) findViewById(R.id.tv_translation)).setText(bean.getNote());
        findViewById(R.id.iv_close).setOnClickListener(view -> dismiss());

        MyImageSpan span = new MyImageSpan(context, R.mipmap.ic_horn);
        SpannableString spanStr = new SpannableString(bean.getContent() + "   ");
        spanStr.setSpan(span, bean.getContent().length() + 2, bean.getContent().length() + 3, DynamicDrawableSpan.ALIGN_BASELINE);
        tvSentence.setText(spanStr);

        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(bean.getFenxiang_img());
        findViewById(R.id.iv_image).setOnClickListener(view -> ImagePreviewActivity.startImagePagerActivity(context, imageUrls, 0));


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
    }

    public void showDialog() {
        Window window = getWindow();
        window.setWindowAnimations(R.style.anim_dialog);
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
        show();
    }
}
