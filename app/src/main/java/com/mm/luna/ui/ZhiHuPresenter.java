package com.mm.luna.ui;

import com.mm.luna.api.Api;
import com.mm.luna.base.BasePresenterImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZMM on 2018/1/17.
 */

public class ZhiHuPresenter extends BasePresenterImpl<ZhiHuContract.View> implements ZhiHuContract.Presenter {
    public ZhiHuPresenter(ZhiHuContract.View view) {
        super(view);
    }

    @Override
    public void getTodayData() {
        Api.getInstance().getNews()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> view.ShowLoadingDialog("正在加载"))
                .map(zhiHuEntity -> zhiHuEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(zhiHuEntity -> {
                    view.dismissLoadingDialog();
                    view.setData(zhiHuEntity);
                }, throwable -> view.dismissLoadingDialog());
    }

    @Override
    public void getBeforeData(String date) {
        Api.getInstance().getBeforeNews(date)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> view.ShowLoadingDialog("正在加载"))
                .map(zhiHuEntity -> zhiHuEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(zhiHuEntity -> {
                    view.dismissLoadingDialog();
                    view.setData(zhiHuEntity);
                }, throwable -> view.dismissLoadingDialog());

    }
}
