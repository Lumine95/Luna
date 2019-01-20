package com.mm.luna.ui.nba;

import android.annotation.SuppressLint;

import com.mm.luna.Constant;
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
            e.onNext(HtmlParseUtil.parseNBASchedule(date, e));
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    view.onFinish();
                    view.setData(isClear, list);
                }, throwable -> {
                    view.onError();
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getLiveList() {
        Observable.create((ObservableOnSubscribe<List<NBABean>>) e -> {
            e.onNext(HtmlParseUtil.parseLive(e));
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    view.onFinish();
                    view.setData(true, list);
                }, throwable -> view.onError());
    }

    @SuppressLint("CheckResult")
    @Override
    public void getLiveSignalList(String url) {
        Observable.create((ObservableOnSubscribe<List<NBABean>>) e -> {
            e.onNext(HtmlParseUtil.parseLiveSignal(url, e));
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    view.onFinish();
                    view.setData(true, list);
                }, throwable -> view.onError());
    }

    @Override
    public void initTeamLogos() {
        Constant.teamLogos.clear();
        Constant.teamLogos.put("76人", "http://mat1.gtimg.com/sports/nba/logo/1602/20.png");
        Constant.teamLogos.put("热火", "http://mat1.gtimg.com/sports/nba/logo/1602/14.png");
        Constant.teamLogos.put("骑士", "http://img1.gtimg.com/sports/pics/hv1/131/116/2220/144385211.png");
        Constant.teamLogos.put("奇才", "http://mat1.gtimg.com/sports/nba/logo/1602/27.png");
        Constant.teamLogos.put("步行者", "http://img1.gtimg.com/sports/pics/hv1/43/116/2220/144385123.png");
        Constant.teamLogos.put("凯尔特人", "http://mat1.gtimg.com/sports/nba/logo/1602/2.png");
        Constant.teamLogos.put("黄蜂", "http://mat1.gtimg.com/sports/nba/logo/1602/30.png");
        Constant.teamLogos.put("雄鹿", "http://mat1.gtimg.com/sports/nba/logo/1602/15.png");
        Constant.teamLogos.put("魔术", "http://mat1.gtimg.com/sports/nba/logo/1602/19.png");
        Constant.teamLogos.put("活塞", "http://mat1.gtimg.com/sports/nba/logo/new/8.png");
        Constant.teamLogos.put("尼克斯", "http://mat1.gtimg.com/sports/nba/logo/1602/18.png");
        Constant.teamLogos.put("马刺", "http://img1.gtimg.com/sports/pics/hv1/231/116/2220/144385311.png");
        Constant.teamLogos.put("老鹰", "http://mat1.gtimg.com/sports/nba/logo/1602/1.png");
        Constant.teamLogos.put("公牛", "http://mat1.gtimg.com/sports/nba/logo/1602/4.png");
        Constant.teamLogos.put("火箭", "http://mat1.gtimg.com/sports/nba/logo/1602/10.png");
        Constant.teamLogos.put("雷霆", "http://mat1.gtimg.com/sports/nba/logo/1602/25.png");
        Constant.teamLogos.put("爵士", "http://mat1.gtimg.com/sports/nba/logo/1602/26.png");
        Constant.teamLogos.put("勇士", "http://mat1.gtimg.com/sports/nba/logo/black/9.png");
        Constant.teamLogos.put("快船", "http://mat1.gtimg.com/sports/nba/logo/1602/1201.png");
        Constant.teamLogos.put("国王", "http://mat1.gtimg.com/sports/nba/logo/1602/23.png");
        Constant.teamLogos.put("鹈鹕", "http://mat1.gtimg.com/sports/nba/logo/1602/3.png");
        Constant.teamLogos.put("开拓者", "http://mat1.gtimg.com/sports/nba/logo/new/22.png");
        Constant.teamLogos.put("湖人", "http://mat1.gtimg.com/sports/nba/logo/1602/13.png");
        Constant.teamLogos.put("森林狼", "http://mat1.gtimg.com/sports/nba/logo/new/16.png");
        Constant.teamLogos.put("独行侠", "http://mat1.gtimg.com/sports/nba/logo/1602/6.png");
        Constant.teamLogos.put("篮网", "http://mat1.gtimg.com/sports/nba/logo/1602/17.png");
        Constant.teamLogos.put("太阳", "http://mat1.gtimg.com/sports/nba/logo/1602/21.png");
        Constant.teamLogos.put("掘金", "http://mat1.gtimg.com/sports/nba/logo/1602/7.png");
        Constant.teamLogos.put("灰熊", "http://mat1.gtimg.com/sports/nba/logo/1602/29.png");
        Constant.teamLogos.put("猛龙", "http://img1.gtimg.com/sports/pics/hv1/133/21/2268/147482188.png");
    }

}
