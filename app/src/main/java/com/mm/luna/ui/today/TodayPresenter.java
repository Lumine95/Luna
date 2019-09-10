package com.mm.luna.ui.today;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.mm.luna.api.Api;
import com.mm.luna.base.BasePresenterImpl;
import com.mm.luna.bean.FictionBean;
import com.mm.luna.util.HtmlParseUtil;
import com.mm.luna.util.StreamUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZMM on 2019/2/27 13:42.
 */
public class TodayPresenter extends BasePresenterImpl<TodayContract.View> implements TodayContract.Presenter {
    TodayPresenter(TodayContract.View view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getTodayEnglish() {
        Api.getInstance().getTodayEnglish()
                .subscribeOn(Schedulers.io())
                .map(entity -> entity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    view.showTodayEnglish(entity);
                }, throwable -> view.onError());
    }

    @SuppressLint("CheckResult")
    @Override
    public void getTodayArticle() {
//        Api.getInstance().getTodayFiction(DateUtil.getCurrentStrDate("yyyyMMdd"))
//                .subscribeOn(Schedulers.io())
//                .map(entity -> entity)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(entity -> {
//                    view.showTodayFiction(entity);
//                }, throwable -> view.onError());
        // TODO: 2019/2/27 Retrofit 请求该链接出现400 Bad Request 错误
        // String url = "https://interface.meiriyiwen.com/article/day?dev=1&date=" + DateUtil.getCurrentStrDate("yyyyMMdd");
        String url = "https://interface.meiriyiwen.com/article/today?dev=1";
        Observable.create((ObservableOnSubscribe<FictionBean>) e -> {
            e.onNext(getTodayArticle(url, e));
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fictionBean -> {
                    view.onFinish();
                    view.showTodayArticle(fictionBean);
                }, throwable -> {
                    view.onError();
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getBingWallpaper() {
        Api.getInstance().getBingWallpaper()
                .subscribeOn(Schedulers.io())
                .map(entity -> entity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    view.ShowBingWallpaper(entity);
                }, throwable -> view.onError());
    }

    @SuppressLint("CheckResult")
    @Override
    public void getOneImage() {
        Observable.create((ObservableOnSubscribe<String[]>) e -> {
            e.onNext(HtmlParseUtil.parseOneImage(e));
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(array -> {
                    view.onFinish();
                    view.ShowOneImage(array);
                }, throwable -> view.onError());
    }

    private FictionBean getTodayArticle(String urlStr, ObservableEmitter<FictionBean> emitter) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = connection.getInputStream();
                String json = StreamUtils.inputSteam2String(inputStream);
                return new Gson().fromJson(json, FictionBean.class);
            } else {
                emitter.onError(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            emitter.onError(e);
        }
        return new FictionBean();
    }
}
