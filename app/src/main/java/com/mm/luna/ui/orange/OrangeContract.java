package com.mm.luna.ui.orange;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.SentenceBean;

/**
 * Created by ZMM on 2018/8/16 17:20.
 */
class OrangeContract {
    interface View extends BaseView {
        void setData(SentenceBean bean, boolean isClear);
    }

    interface Presenter extends BasePresenter {
        void getSentenceList(int pageIndex, boolean isClear, int type);
    }
}
