package com.mm.luna.ui.douban;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.HotMovieBean;

/**
 * Created by ZMM on 2018/5/3  22:31.
 */

public interface DoubanContract {
    interface View extends BaseView {
        void setData(HotMovieBean bean, boolean isClear);
    }

    interface Presenter extends BasePresenter {
        void getMovieList(int pageIndex, boolean isClear, int position);
    }
}
