package com.mm.luna.ui.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mm.luna.R;
import com.mm.luna.util.StatusBarUtils;
import com.mm.luna.view.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;


/**
 * 查看大图 glide
 * Created by  张明明 on  2017.10.24
 */
public class ImagePreviewActivity extends Activity {
    public static final String INTENT_IMGURLS = "imgurls";
    public static final String INTENT_POSITION = "position";
    private TextView tvIndicator;
    private Button btnSaveImage;
    private GlideDrawable glideDrawable;

    public static void startImagePagerActivity(Activity activity, List<String> imgUrls, int position) {
        Intent intent = new Intent(activity, ImagePreviewActivity.class);
        intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<>(imgUrls));
        intent.putExtra(INTENT_POSITION, position);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ImagePreviewActivity.this.finish();
            ImagePreviewActivity.this.overridePendingTransition(R.anim.fade_in,
                    R.anim.fade_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        initView();
    }


    @SuppressLint("SetTextI18n")
    public void initView() {
        StatusBarUtils.setTransparent(this);
        ViewPager viewPager = (ViewPagerFixed) findViewById(R.id.pager);
        tvIndicator = findViewById(R.id.tv_indicator);
        btnSaveImage = findViewById(R.id.btn_save_image);
        btnSaveImage.setOnClickListener(v -> {
            if (glideDrawable != null) {
//                SystemUtil.saveImageToGallery(this,glideDrawable);
            }
        });
        int startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
        ArrayList<String> imgUrls = getIntent().getStringArrayListExtra(INTENT_IMGURLS);
        if (imgUrls.size() > 1) {
            tvIndicator.setText((startPos + 1) + "/" + imgUrls.size());
        }
        ImageAdapter mAdapter = new ImageAdapter(this);
        mAdapter.setDatas(imgUrls);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvIndicator.setText((position + 1) + "/" + imgUrls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(startPos);
    }

    private class ImageAdapter extends PagerAdapter {

        private List<String> datas = new ArrayList<>();
        private LayoutInflater inflater;
        private Context context;

        void setDatas(List<String> datas) {
            if (datas != null)
                this.datas = datas;
        }

        ImageAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            if (datas == null) return 0;
            return datas.size();
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View view = inflater.inflate(R.layout.item_pager_image, container, false);
            if (view != null) {
                final PhotoView imageView = view.findViewById(R.id.image);
                //单击图片退出
                imageView.setOnPhotoTapListener((view1, x, y) -> {
                    ImagePreviewActivity.this.finish();
                    ImagePreviewActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                });

                //loading
                final ProgressBar loading = new ProgressBar(context);
                FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingLayoutParams.gravity = Gravity.CENTER;
                loading.setLayoutParams(loadingLayoutParams);
                ((FrameLayout) view).addView(loading);

                final String imgUrl = datas.get(position);

                loading.setVisibility(View.VISIBLE);
                Glide.with(context).load(imgUrl.trim())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.ic_default_bilibili)
                        .thumbnail(0.1f)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                loading.setVisibility(View.GONE);
                                btnSaveImage.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                                glideDrawable = resource;
                                loading.setVisibility(View.GONE);
                                btnSaveImage.setVisibility(View.VISIBLE);
                                return false;
                            }
                        })
                        .into(imageView);

                container.addView(view, 0);
            }
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


    }
}
