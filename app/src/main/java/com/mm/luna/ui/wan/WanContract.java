package com.mm.luna.ui.wan;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.ArticleBean;

/**
 * Created by ZMM on 2018/8/14 11:30.
 */
public class WanContract {
    interface View extends BaseView {
        void setData(ArticleBean bean, boolean isClear);
    }

    interface Presenter extends BasePresenter {
        void getArticleList(int pageIndex, boolean isClear);
    }
}
