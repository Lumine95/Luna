package com.mm.luna.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.R;
import com.mm.luna.bean.NBABean;
import com.mm.luna.util.GlideUtil;

import java.util.List;

public class LiveAdapter extends BaseMultiItemQuickAdapter<NBABean, BaseViewHolder> {

    public LiveAdapter(List data) {
        super(data);
        addItemType(NBABean.DATE, R.layout.item_date_view);
        addItemType(NBABean.NORMAL, R.layout.item_live);
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
                helper.setText(R.id.tv_title, item.getTitle());

                GlideUtil.loadImage(mContext, item.getHomeTeamImg(), helper.getView(R.id.iv_home_team), R.mipmap.ic_default_bilibili);
                GlideUtil.loadImage(mContext, item.getVisitingTeamImg(), helper.getView(R.id.iv_visiting_team), R.mipmap.ic_default_bilibili);

                if (item.getScore().contains("-")) {
                    String[] split = item.getScore().split("-");
                    helper.setText(R.id.tv_visiting_score, split[0]);
                    helper.setText(R.id.tv_home_score, split[1]);
                }
                break;
        }
    }
}

