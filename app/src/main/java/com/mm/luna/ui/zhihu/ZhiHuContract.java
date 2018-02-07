package com.mm.luna.ui.zhihu;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.ZhiHuEntity;

/**
 * Created by ZMM on 2018/1/17.
 */

public interface ZhiHuContract {
    interface View extends BaseView {
        void setData(ZhiHuEntity zhiHuEntity, boolean isClear);
    }

    interface Presenter extends BasePresenter {
        void getTodayData(boolean isClear);
        void getBeforeData(String date, boolean isClear);
    }
}
