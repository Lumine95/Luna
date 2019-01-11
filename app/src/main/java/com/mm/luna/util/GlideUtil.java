package com.mm.luna.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by ZMM on 2018/8/7 16:07.
 */
public class GlideUtil {

    public static void loadImage(Context context, String url, ImageView view, int defaultImage) {
        if (context != null && !TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .crossFade()
                    // .placeholder(defaultImage)
                    .error(defaultImage)
                    .into(view);
        }
    }
}
