package com.mm.luna.ui.douban;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.DoubanEntity;

/**
 * Created by ZMM on 2018/5/3  22:31.
 */

public interface DoubanContract {
    interface View extends BaseView {
        void setData(DoubanEntity entity);
    }

    interface Presenter extends BasePresenter {
        void getVideoList();
    }
}
