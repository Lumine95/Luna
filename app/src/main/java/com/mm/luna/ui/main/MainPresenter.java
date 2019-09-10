package com.mm.luna.ui.main;

import android.annotation.SuppressLint;

import com.mm.luna.base.BasePresenterImpl;


/**
 * Created by ZMM on 2018/9/29 16:02.
 */
public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {
    MainPresenter(MainContract.View view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMonthPicture() {
        view.setMonthPicture(null);
//        Api.getInstance().getMonthPicture()
//                .subscribeOn(Schedulers.io())
//                .map(entity -> entity)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(entity -> {
//                    view.setMonthPicture(entity);
//                }, throwable -> view.onError());
    }
}
