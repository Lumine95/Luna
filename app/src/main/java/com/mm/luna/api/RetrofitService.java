package com.mm.luna.api;

import com.mm.luna.bean.ZhiHuEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ZMM on 2018/1/17.
 */

public interface RetrofitService {
    String BASE_URL = "https://news-at.zhihu.com/api/4/";

    @GET("news/latest")
    Observable<ZhiHuEntity> getNews();

    @GET("stories/before/{date}")
    Observable<ZhiHuEntity> getBeforeNews(@Path("date") String date);
}
