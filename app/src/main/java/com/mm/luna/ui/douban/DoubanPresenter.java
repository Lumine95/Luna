package com.mm.luna.ui.douban;

import com.mm.luna.base.BasePresenterImpl;

/**
 * Created by ZMM on 2018/5/3  22:34.
 */

public class DoubanPresenter extends BasePresenterImpl<DoubanContract.View> implements DoubanContract.Presenter {
    public DoubanPresenter(DoubanContract.View view) {
        super(view);
    }

    @Override
    public void getVideoList() {
//        Api.getInstance().getComicsList()
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(disposable -> view.ShowLoadingDialog("正在加载"))
//                .map(entity -> entity)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(entity -> {
//                    view.dismissLoadingDialog();
//                    view.setData(entity);
//                }, throwable -> view.dismissLoadingDialog());

    }
}
