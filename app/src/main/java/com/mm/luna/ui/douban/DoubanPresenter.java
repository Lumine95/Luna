package com.mm.luna.ui.douban;

import com.mm.luna.api.Api;
import com.mm.luna.base.BasePresenterImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZMM on 2018/5/3  22:34.
 */

public class DoubanPresenter extends BasePresenterImpl<DoubanContract.View> implements DoubanContract.Presenter {
    public DoubanPresenter(DoubanContract.View view) {
        super(view);
    }

    @Override
    public void getMovieList(int position) {
        Api.getInstance().getComicsList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> view.onLoading())
                .map(entity -> entity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    view.onFinish();
                //    view.setData(entity);
                }, throwable -> view.onError());
    }
}
