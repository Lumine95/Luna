package com.mm.luna.ui.douban;

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
                Api.getInstance().getMovieInTheater(pageIndex * 20, 20)
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
                Api.getInstance().getMovieComing(pageIndex * 20, 20)
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
                Api.getInstance().getMovieTop(pageIndex * 20, 20)
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
}
