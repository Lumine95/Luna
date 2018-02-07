package com.mm.luna.ui.violet;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;

import java.util.ArrayList;

/**
 * Created by ZMM on 2018/2/6.
 */

public interface VioletContract {
    interface View extends BaseView {
        void setData(ArrayList<String> videos);
    }

    interface Presenter extends BasePresenter {
        void getVideoList();
    }
}
