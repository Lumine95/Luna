package com.mm.luna.ui.wan;

import android.annotation.SuppressLint;

import com.mm.luna.api.Api;
import com.mm.luna.base.BasePresenterImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZMM on 2018/8/14 11:41.
 */
public class WanPresenter extends BasePresenterImpl<WanContract.View> implements WanContract.Presenter {
    public WanPresenter(WanContract.View view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getArticleList(int pageIndex, boolean isClear) {
        Api.getInstance().getArticleList(pageIndex)
                .subscribeOn(Schedulers.io())
                .map(entity -> entity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    view.onFinish();
                    view.setData(entity,isClear);
                }, throwable -> view.onError());
    }
}
