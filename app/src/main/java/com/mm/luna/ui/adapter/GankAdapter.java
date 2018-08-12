package com.mm.luna.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.bean.GankBean;

import java.util.List;

/**
 * Created by ZMM on 2018/8/11  23:26.
 */
public class GankAdapter extends BaseQuickAdapter<GankBean.ResultsBean, BaseViewHolder> {


    public GankAdapter(int layoutResId, @Nullable List<GankBean.ResultsBean> data, int type) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankBean.ResultsBean item) {

    }
}
