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
    public void getData() {
        Api.getInstance().getNews()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> view.ShowLoadingDialog("正在加载"))
                .map(zhiHuEntity -> zhiHuEntity.getStories())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(storiesBeans -> {
                    view.dismissLoadingDialog();
                    view.setData(storiesBeans);
                }, throwable -> view.dismissLoadingDialog());
    }
}
