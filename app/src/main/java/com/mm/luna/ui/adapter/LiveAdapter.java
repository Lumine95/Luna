package com.mm.luna.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.R;
import com.mm.luna.bean.NBABean;

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
                helper.setText(R.id.tv_home_team, item.getHomeTem());
                helper.setText(R.id.tv_visiting_team, item.getVisitingTeam());
                helper.setText(R.id.tv_time, item.getTime());
                helper.setText(R.id.tv_title, item.getTitle());
                break;
        }
    }
}

