package com.mm.luna.ui.computer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mm.luna.R;
import com.mm.luna.bean.CFanBean;
import com.mm.luna.ui.common.WebViewActivity;
import com.mm.luna.util.GlideUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZMM on 2018/8/23 9:49.
 */
@SuppressLint("ViewConstructor")
public class FanHeaderView extends LinearLayout {

    private Context mContext;
    private List<CFanBean> bannerList;
    private List<String> titleList = new ArrayList<>();
    public Banner banner;

    public FanHeaderView(Context context, List<CFanBean> bannerList) {
        super(context);
        mContext = context;
        this.bannerList = bannerList;
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.layout_fan_banner, null);
        this.addView(view);
        banner = view.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                GlideUtil.loadImage(context, ((CFanBean) path).getImage(), imageView, R.mipmap.ic_default_bilibili_h);
            }
        });
        banner.setImages(bannerList);

        for (CFanBean bean : bannerList) {
            titleList.add(bean.getTitle());
        }
        banner.setBannerTitles(titleList);
        banner.isAutoPlay(true);
        banner.setDelayTime(2400);
        banner.setOnBannerListener(position -> mContext.startActivity(new Intent(mContext, WebViewActivity.class)
                .putExtra("title", mContext.getString(R.string.computer_fan))
                .putExtra("url", bannerList.get(position).getUrl())));
        banner.start();
    }
}
