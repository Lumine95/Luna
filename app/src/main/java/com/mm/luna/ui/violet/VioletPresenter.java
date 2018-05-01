package com.mm.luna.ui.violet;

import com.mm.luna.api.Api;
import com.mm.luna.base.BasePresenterImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZMM on 2018/2/6.
 */

public class VioletPresenter extends BasePresenterImpl<VioletContract.View> implements VioletContract.Presenter {

    public VioletPresenter(VioletContract.View view) {
        super(view);
    }
    @Override
    public void getVideoList() {
        Api.getInstance().getComicsList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> view.ShowLoadingDialog("正在加载"))
                .map(entity -> entity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    view.dismissLoadingDialog();
                    view.setData(entity);
                }, throwable -> view.dismissLoadingDialog());
    }
}
