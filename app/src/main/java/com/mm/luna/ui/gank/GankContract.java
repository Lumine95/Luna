package com.mm.luna.ui.gank;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.GankBean;

/**
 * Created by ZMM on 2018/8/10 15:32.
 */
class GankContract {
    interface View extends BaseView {
        void setData(GankBean bean, boolean isClear);
    }

    interface Presenter extends BasePresenter {
        void getArticleList(int pageIndex, boolean isClear, int type);
    }
}
