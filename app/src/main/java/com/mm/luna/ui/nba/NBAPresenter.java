package com.mm.luna.ui.nba;

import android.annotation.SuppressLint;

import com.mm.luna.base.BasePresenterImpl;
import com.mm.luna.bean.NBABean;
import com.mm.luna.util.HtmlParseUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZMM on 2018/10/10 15:49.
 */
public class NBAPresenter extends BasePresenterImpl<NBAContract.View> implements NBAContract.Presenter {
    NBAPresenter(NBAContract.View view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getScheduleList(String date, boolean isClear) {
        Observable.create((ObservableOnSubscribe<List<NBABean>>) e -> {
            e.onNext(HtmlParseUtil.parseNBASchedule(date));
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    view.onFinish();
                    view.setData(isClear, list);
                }, throwable -> view.onError());
    }
}
