package com.mm.luna.api;

import com.mm.luna.bean.ArticleBean;
import com.mm.luna.bean.ComicEntity;
import com.mm.luna.bean.GankBean;
import com.mm.luna.bean.HomeBean;
import com.mm.luna.bean.HotMovieBean;
import com.mm.luna.bean.MovieDetailBean;
import com.mm.luna.bean.ZhiHuDetailEntity;
import com.mm.luna.bean.ZhiHuEntity;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ZMM on 2018/1/17.
 */

public interface RetrofitService {
    String DEFAULT_BASE_URL = "https://news-at.zhihu.com/";

    @Headers({"Domain-Name: zhihu"})
    @GET("/api/4/news/latest")
    Observable<ZhiHuEntity> getNews();

    @Headers({"Domain-Name: zhihu"})
    @GET("/api/4/stories/before/{date}")
    Observable<ZhiHuEntity> getBeforeNews(@Path("date") String date);

    @Headers({"Domain-Name: zhihu"})
    @GET("/api/4/news/{id}")
    Observable<ZhiHuDetailEntity> getNewsDetail(@Path("id") int id);

    @GET("http://www.wanandroid.com/tools/mockapi/4060/comics")
    Observable<ComicEntity> getComicsList();

    @GET("http://www.wanandroid.com/tools/mockapi/4060/monthPicture")
    Observable<HomeBean> getMonthPicture();

    @GET("http://open.iciba.com/dsapi/")
    Observable<HomeBean> getTodayEnglish();

    @Headers({"Domain-Name: douban"})
    @GET("v2/movie/in_theaters")
    Observable<HotMovieBean> getMovieInTheater(@Query("start") int start, @Query("count") int count);

    @Headers({"Domain-Name: douban"})
    @GET("v2/movie/coming_soon")
    Observable<HotMovieBean> getMovieComing(@Query("start") int start, @Query("count") int count);

    @Headers({"Domain-Name: douban"})
    @GET("v2/movie/top250")
    Observable<HotMovieBean> getMovieTop(@Query("start") int start, @Query("count") int count);

    @Headers({"Domain-Name: douban"})
    @GET("v2/movie/search")
    Observable<HotMovieBean> searchMovie(@Query("q") String keyword, @Query("start") int start, @Query("count") int count);

    @Headers({"Domain-Name: douban"})
    @GET("v2/movie/subject/{id}")
    Observable<MovieDetailBean> getMovieDetail(@Path("id") String id);

    @Headers({"Domain-Name: gank"})
    @GET("data/{type}/{count}/{page}")
    Observable<GankBean> getGankList(@Path("type") String type, @Path("count") int count, @Path("page") int page);

    @Headers({"Domain-Name: orange"})
    @GET("meitumeiju")
    Observable<ResponseBody> getSentenceList(@Query("page") int page);

    @Headers({"Domain-Name: orange"})
    @GET("meitumeiju/shouxiemeiju")
    Observable<ResponseBody> getHandWriting(@Query("page") int page);

    @Headers({"Domain-Name: orange"})
    @GET("meitumeiju/jingdianduibai")
    Observable<ResponseBody> getMovieDialog(@Query("page") int page);

    @Headers({"Domain-Name: wan"})
    @GET("article/list/{page}/json")
    Observable<ArticleBean> getArticleList(@Path("page") int page);

    @Headers({"Domain-Name: cfan"})
    @GET("{page}.shtml")
    Observable<ResponseBody> getCFanHome(@Path("page") int page);

    @Headers({"Domain-Name: cfan"})
    @GET("news/{page}.shtml")
    Observable<ResponseBody> getCFanNews(@Path("page") int page);

    @Headers({"Domain-Name: cfan"})
    @GET("product/{page}.shtml")
    Observable<ResponseBody> getCFanProduct(@Path("page") int page);

    @Headers({"Domain-Name: cfan"})
    @GET("technic/{page}.shtml")
    Observable<ResponseBody> getCFanTech(@Path("page") int page);

    @Headers({"Domain-Name: mob"})
    @GET("/boxoffice/day/query")
    Observable<HotMovieBean> getMovieBoxOffice(@Query("key") String mobKey, @Query("area") String area);

    @GET("https://nba.hupu.com/schedule/{date}")
    Observable<ResponseBody> getNBASchedule(@Path("date") String date);
}
