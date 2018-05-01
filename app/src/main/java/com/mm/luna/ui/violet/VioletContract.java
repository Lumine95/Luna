package com.mm.luna.ui.violet;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.ComicEntity;

/**
 * Created by ZMM on 2018/2/6.
 */

public interface VioletContract {
    interface View extends BaseView {
        void setData(ComicEntity entity);
    }

    interface Presenter extends BasePresenter {
        void getVideoList();
    }
}
