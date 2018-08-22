package com.mm.luna.ui.computer;

import android.annotation.SuppressLint;

import com.mm.luna.api.Api;
import com.mm.luna.base.BasePresenterImpl;
import com.mm.luna.bean.CFanBean;
import com.mm.luna.util.DocParseUtil;
import com.mm.luna.util.SystemUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by ZMM on 2018/8/22 16:10.
 */
public class FanPresenter extends BasePresenterImpl<FanContract.View> implements FanContract.Presenter {
    public FanPresenter(FanContract.View view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getArticleList(int pageIndex, boolean isClear, int type) {
        Observable<ResponseBody> observableApi = Api.getInstance().getCFanHome(pageIndex);
        switch (type) {
            case 0:
                observableApi = Api.getInstance().getCFanHome(pageIndex);
                break;
            case 1:
                observableApi = Api.getInstance().getCFanNews(pageIndex);
                break;
            case 2:
                observableApi = Api.getInstance().getCFanProduct(pageIndex);
                break;
            case 3:
                observableApi = Api.getInstance().getCFanTech(pageIndex);
                break;
            case 4:
                observableApi = Api.getInstance().getCFanSpecial(pageIndex);
                break;
        }
        observableApi.subscribeOn(Schedulers.io())
                .map(entity -> entity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    view.onFinish();
                    String html = SystemUtil.is2Str(responseBody.byteStream());
                    List<CFanBean> list = DocParseUtil.parseCFanNews(html);
                    view.setData(list, isClear);
                }, throwable -> view.onError());

    }
}
