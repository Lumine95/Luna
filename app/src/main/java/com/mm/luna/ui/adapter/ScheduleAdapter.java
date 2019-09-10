package com.mm.luna.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.Constant;
import com.mm.luna.R;
import com.mm.luna.bean.NBABean;
import com.mm.luna.util.GlideUtil;

import java.util.List;

public class ScheduleAdapter extends BaseMultiItemQuickAdapter<NBABean, BaseViewHolder> {

    public ScheduleAdapter(List data) {
        super(data);
        addItemType(NBABean.DATE, R.layout.item_date_view);
        addItemType(NBABean.NORMAL, R.layout.item_schedule_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, NBABean item) {
        switch (helper.getItemViewType()) {
            case NBABean.DATE:
                helper.setText(R.id.tv_date, item.getDate());
                break;
            case NBABean.NORMAL:
                helper.setText(R.id.tv_home_team, item.getHomeTeam());
                helper.setText(R.id.tv_visiting_team, item.getVisitingTeam());
                helper.setText(R.id.tv_time, item.getTime());
                GlideUtil.loadImage(mContext, Constant.teamLogos.get(item.getVisitingTeam()), helper.getView(R.id.iv_visiting_team), R.mipmap.ic_default_bilibili);
                GlideUtil.loadImage(mContext, Constant.teamLogos.get(item.getHomeTeam()), helper.getView(R.id.iv_home_team), R.mipmap.ic_default_bilibili);
                break;
        }
    }
}

