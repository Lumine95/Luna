package com.mm.luna.ui.computer;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.CFanBean;

import java.util.List;

/**
 * Created by ZMM on 2018/8/22 16:08.
 */
class FanContract {
    interface View extends BaseView {
        void setData(List<CFanBean> list, boolean isClear);

        void setBannerData(List<CFanBean> bannerList);
    }

    interface Presenter extends BasePresenter {
        void getArticleList(int pageIndex, boolean isClear, int type);
    }
}
