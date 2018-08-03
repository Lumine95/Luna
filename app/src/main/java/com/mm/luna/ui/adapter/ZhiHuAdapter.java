package com.mm.luna.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.R;
import com.mm.luna.bean.ZhiHuEntity;

import java.util.List;

/**
 * Created by ZMM on 2018/1/17.
 */

public class ZhiHuAdapter extends BaseQuickAdapter<ZhiHuEntity.StoriesBean, BaseViewHolder> {


    public ZhiHuAdapter(int layoutResId, @Nullable List<ZhiHuEntity.StoriesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhiHuEntity.StoriesBean item) {
        helper.setText(R.id.text, item.getTitle());
        Glide.with(mContext).load(item.getImages().get(0)).crossFade().into((ImageView) helper.getView(R.id.image));
    }
}
