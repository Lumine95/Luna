package com.mm.luna.ui.nba;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.NBABean;

import java.util.List;

/**
 * Created by ZMM on 2018/10/10 15:40.
 */
class NBAContract {
    interface View extends BaseView {
        void setData(boolean isClear, List<NBABean> beanList);
    }

    interface Presenter extends BasePresenter {
        void getScheduleList(String date, boolean isClear);

        void initTeamLogos();

        void getLiveList(boolean isClear);

        void getLiveSignalList(String url);
    }
}
