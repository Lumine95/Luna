package com.mm.luna.ui.gank;

import android.annotation.SuppressLint;

import com.mm.luna.api.Api;
import com.mm.luna.base.BasePresenterImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZMM on 2018/8/10 15:34.
 */
public class GankPresenter extends BasePresenterImpl<GankContract.View> implements GankContract.Presenter {
    GankPresenter(GankContract.View view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getArticleList(int pageIndex, boolean isClear, int type) {
        String[] typeArr = {"Android", "iOS", "前端", "福利"};
        Api.getInstance().getGankList(typeArr[type], 20, pageIndex)
                .subscribeOn(Schedulers.io())
                .map(entity -> entity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    if (entity.isError()) {
                        view.onError();
                    } else {
                        view.onFinish();
                        view.setData(entity, isClear);
                    }
                }, throwable -> view.onError());
    }
}
