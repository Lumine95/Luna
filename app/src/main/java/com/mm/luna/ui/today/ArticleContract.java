package com.mm.luna.ui.today;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.FictionBean;

class ArticleContract {
    interface View extends BaseView {
        void showArticle(FictionBean bean);
    }

    interface Presenter extends BasePresenter {
        void getArticle(String date);

        void getRandomArticle();
    }
}