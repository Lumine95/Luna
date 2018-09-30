package com.mm.luna.ui.zhihu;

import android.annotation.SuppressLint;

import com.mm.luna.api.Api;
import com.mm.luna.base.BasePresenterImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZMM on 2018/1/17.
 */

public class ZhiHuPresenter extends BasePresenterImpl<ZhiHuContract.View> implements ZhiHuContract.Presenter {
    ZhiHuPresenter(ZhiHuContract.View view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getTodayData() {
        Api.getInstance().getNews()
                .subscribeOn(Schedulers.io())
                .map(zhiHuEntity -> zhiHuEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(zhiHuEntity -> {
                    view.onFinish();
                    view.setData(zhiHuEntity, true);
                }, throwable -> view.onError());
    }

    @SuppressLint("CheckResult")
    @Override
    public void getBeforeData(String date, boolean isClear) {
        Api.getInstance().getBeforeNews(date)
                .subscribeOn(Schedulers.io())
                .map(zhiHuEntity -> zhiHuEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(zhiHuEntity -> {
                    view.onFinish();
                    view.setData(zhiHuEntity, isClear);
                }, throwable -> view.onError());
    }
}
