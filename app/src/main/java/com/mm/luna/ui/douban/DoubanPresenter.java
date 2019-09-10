package com.mm.luna.ui.douban;

import android.annotation.SuppressLint;

import com.mm.luna.Constant;
import com.mm.luna.api.Api;
import com.mm.luna.base.BasePresenterImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZMM on 2018/5/3  22:34.
 */

public class DoubanPresenter extends BasePresenterImpl<DoubanContract.View> implements DoubanContract.Presenter {
    DoubanPresenter(DoubanContract.View view) {
        super(view);
    }

    @Override
    public void getMovieList(int pageIndex, boolean isClear, int position) {
        switch (position) {
            case 0:
                Api.getInstance().getMovieInTheater(pageIndex * 20, 20, Constant.doubanKey)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(disposable -> {
                        })
                        .map(entity -> entity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(entity -> {
                            view.onFinish();
                            view.setData(entity, isClear);
                        }, throwable -> view.onError());
                break;
            case 1:
                Api.getInstance().getMovieComing(pageIndex * 20, 20,Constant.doubanKey)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(disposable -> {
                        })
                        .map(entity -> entity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(entity -> {
                            view.onFinish();
                            view.setData(entity, isClear);
                        }, throwable -> view.onError());
                break;
            case 2:
                Api.getInstance().getMovieTop(pageIndex * 25, 25,Constant.doubanKey)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(disposable -> {
                        })
                        .map(entity -> entity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(entity -> {
                            view.onFinish();
                            view.setData(entity, isClear);
                        }, throwable -> view.onError());
                break;
        }
    }


    @SuppressLint("CheckResult")
    @Override
    public void getMovieDetail(String id) {
        Api.getInstance().getMovieDetail(id,Constant.doubanKey)
                .subscribeOn(Schedulers.io())
                .map(entity -> entity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    view.onFinish();
                    view.loadMovieDetail(entity);
                }, throwable -> view.onError());
    }

    @SuppressLint("CheckResult")
    @Override
    public void searchMovie(String keyword, int pageIndex, boolean isClear) {
        Api.getInstance().searchMovie(keyword, pageIndex * 20, 20,Constant.doubanKey)
                .subscribeOn(Schedulers.io())
                .map(entity -> entity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    view.onFinish();
                    view.setData(entity, isClear);
                }, throwable -> view.onError());
    }

    @SuppressLint("CheckResult")
    @Override
    public void getBoxOffice() {
        Api.getInstance().getMovieBoxOffice("2764aa36b1b8a", "CN")
                .subscribeOn(Schedulers.io())
                .map(entity -> entity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    view.onFinish();
                    view.setData(entity, true);
                }, throwable -> view.onError());

    }
}
