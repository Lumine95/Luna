package com.mm.luna.ui.orange;

import android.annotation.SuppressLint;

import com.mm.luna.api.Api;
import com.mm.luna.base.BasePresenterImpl;
import com.mm.luna.bean.SentenceBean;
import com.mm.luna.util.DocParseUtil;
import com.mm.luna.util.SystemUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZMM on 2018/8/16 17:21.
 */
public class OrangePresenter extends BasePresenterImpl<OrangeContract.View> implements OrangeContract.Presenter {
    OrangePresenter(OrangeContract.View view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getSentenceList(int pageIndex, boolean isClear, int type) {
        switch (type) {
            case 0:
                Api.getInstance().getSentenceList(pageIndex)
                        .subscribeOn(Schedulers.io())
                        .map(entity -> entity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseBody -> {
                            view.onFinish();
                            String html = SystemUtil.is2Str(responseBody.byteStream());
                            SentenceBean sentenceBean = DocParseUtil.parseOrangeSentence(isClear, html);
                            view.setData(sentenceBean, isClear);
                        }, throwable -> view.onError());
                break;
            case 1:
                Api.getInstance().getHandWriting(pageIndex)
                        .subscribeOn(Schedulers.io())
                        .map(entity -> entity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseBody -> {
                            view.onFinish();
                            String html = SystemUtil.is2Str(responseBody.byteStream());
                            SentenceBean sentenceBean = DocParseUtil.parseOrangeSentence(isClear, html);
                            view.setData(sentenceBean, isClear);
                        }, throwable -> view.onError());
                break;
            case 2:
                Api.getInstance().getMovieDialog(pageIndex)
                        .subscribeOn(Schedulers.io())
                        .map(entity -> entity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseBody -> {
                            view.onFinish();
                            String html = SystemUtil.is2Str(responseBody.byteStream());
                            SentenceBean sentenceBean = DocParseUtil.parseOrangeSentence(isClear, html);
                            view.setData(sentenceBean, isClear);
                        }, throwable -> view.onError());
                break;
        }
    }
}
