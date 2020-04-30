package com.mm.luna.ui.main;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.HomeBean;
import com.mm.luna.bean.WallPaperBean;

/**
 * Created by ZMM on 2018/9/29 15:59.
 */
class MainContract {
    interface View extends BaseView {
        void setMonthPicture(WallPaperBean bean);
    }

    interface Presenter extends BasePresenter {
        void getMonthPicture();
    }
}
