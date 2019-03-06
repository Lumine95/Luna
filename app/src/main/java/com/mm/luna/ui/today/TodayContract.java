package com.mm.luna.ui.today;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.BingBean;
import com.mm.luna.bean.FictionBean;
import com.mm.luna.bean.HomeBean;

class TodayContract {
    interface View extends BaseView {
        void showTodayEnglish(HomeBean bean);

        void showTodayArticle(FictionBean bean);

        void ShowBingWallpaper(BingBean bean);

        void ShowOneImage(String[] array);
    }

    interface Presenter extends BasePresenter {
        void getTodayEnglish();

        void getTodayArticle();

        void getBingWallpaper();

        void getOneImage();
    }
}