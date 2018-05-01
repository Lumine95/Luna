package com.mm.luna.ui.violet;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.R;
import com.mm.luna.bean.ComicEntity;

import java.util.List;

/**
 * Created by ZMM on 2018/2/6.
 */

public class VioletAdapter extends BaseQuickAdapter<ComicEntity, BaseViewHolder> {
    public VioletAdapter(int layoutResId, @Nullable List<ComicEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComicEntity item) {
        helper.setText(R.id.tv_episode, "第" + (helper.getLayoutPosition() + 1) + "集");
    }
}
