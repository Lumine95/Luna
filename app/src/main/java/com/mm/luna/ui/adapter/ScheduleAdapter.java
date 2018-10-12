package com.mm.luna.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mm.luna.R;
import com.mm.luna.bean.NBABean;

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
                helper.setText(R.id.tv_date, "日期");
                break;
            case NBABean.NORMAL:
                helper.setText(R.id.tv_home_team, "主队");
                break;
        }
    }

}

