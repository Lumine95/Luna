package com.mm.luna.ui.main;

import android.annotation.SuppressLint;

import com.mm.luna.api.Api;
import com.mm.luna.base.BasePresenterImpl;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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
        Api.getInstance().getMonthPicture(new Random().nextInt(6839))
                .subscribeOn(Schedulers.io())
                .map(entity -> entity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    view.setMonthPicture(entity);
                }, throwable -> view.onError());
    }
}
