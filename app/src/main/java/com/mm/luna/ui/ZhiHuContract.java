package com.mm.luna.ui;

import com.mm.luna.base.BasePresenter;
import com.mm.luna.base.BaseView;
import com.mm.luna.bean.ZhiHuEntity;

import java.util.List;

/**
 * Created by ZMM on 2018/1/17.
 */

public interface ZhiHuContract {
    interface View extends BaseView {
        void setData(List<ZhiHuEntity.StoriesBean> listData);
    }

    interface Presenter extends BasePresenter {
        void getData();
    }
}
