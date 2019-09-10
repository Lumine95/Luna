package com.mm.luna.util;

import android.text.TextUtils;
import android.util.Log;

import com.mm.luna.bean.CFanBean;
import com.mm.luna.bean.NBABean;
import com.mm.luna.bean.SentenceBean;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;

/**
 * Created by ZMM on 2018/8/17 11:56.
 */
public class HtmlParseUtil {

    public static SentenceBean parseOrangeSentence(boolean isClear, String html) {
        SentenceBean sentenceBean = new SentenceBean();
        Document document = Jsoup.parse(html);
        if (isClear) {
            Elements pageLasts = document.getElementsByClass("pager-last");
            if (pageLasts != null && pageLasts.size() > 0) {
                sentenceBean.setPage(pageLasts.first().text());
            }
        }
        List<SentenceBean.ResultBean> sentenceList = new ArrayList<>();
        Elements elements = document.getElementsByClass("views-field-phpcode");
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            if (element != null) {
                Element bdshare = element.getElementById("bdshare");
                if (bdshare != null) {
                    String data = bdshare.attr("data");
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        SentenceBean.ResultBean bean = new SentenceBean.ResultBean();
                        bean.setText(jsonObject.get("text").toString());
                        bean.setDesc(jsonObject.get("desc").toString());
                        bean.setUrl(jsonObject.get("url").toString());
                        bean.setPic(jsonObject.get("pic").toString());
                        sentenceList.add(bean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        sentenceBean.setList(sentenceList);
        return sentenceBean;
    }

    public static List<CFanBean> parseCFanNews(String html) {
        List<CFanBean> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("left-post");
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            if (element != null) {
                String category = element.select("div.new-item").text();
                String detailUrl = element.select("a").attr("href");
                String title = element.select("a").attr("title");
                String image = SystemUtil.getStrFromParen(element.select("div.left-post-pic").attr("style"));
                String intro = element.select("div.left-post-txt").text();
                String author = element.select("div.author").text();
                list.add(new CFanBean(category, title, image, detailUrl, intro, author));
            }
        }
        return list;
    }

    public static List<CFanBean> parseCFanBanner(String html) {
        List<CFanBean> bannerList = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("focusimg");
        Element element = elements.get(0);
        if (element != null) {
            Elements item = element.select("a");
            for (Element e : item) {
                String url = e.attr("href");
                String title = e.attr("title");
                String imageLeft = SystemUtil.getStrFromParen(e.select("div.focusimg_left").attr("style"));
                String imageRight = SystemUtil.getStrFromParen(e.select("div.right_list").attr("style"));
                Logger.d(TextUtils.isEmpty(imageLeft) ? imageRight : imageLeft + "---" + url);
                bannerList.add(new CFanBean("", title, TextUtils.isEmpty(imageLeft) ? imageRight : imageLeft, url, "", ""));
            }
        }
        return bannerList;
    }

    public static List<NBABean> parseNBASchedule(String d, ObservableEmitter<List<NBABean>> emitter) {
        List<NBABean> scheduleList = new ArrayList<>();
        try {
            Document document = Jsoup.connect("https://nba.hupu.com/schedule/" + d).get();
            String date = "";
            Elements elements = document.getElementsByClass("left");
            for (Element element : elements) {
                if (element.text().contains("vs") || element.text().contains("星期")) {
                    if (element.text().contains("星期")) {
                        if (!element.text().equals(date)) {
                            scheduleList.add(new NBABean(element.text(), NBABean.DATE));
                        }
                        date = element.text();
                    } else {
                        scheduleList.add(new NBABean(element.text().split(" "), NBABean.NORMAL));
                    }
                }
            }
        } catch (Exception e) {
            emitter.onError(e);
            Log.i("Exception: ", e.toString());
        }
        return scheduleList;
    }

    public static List<NBABean> parseLive(ObservableEmitter<List<NBABean>> emitter) {
        List<NBABean> dataList = new ArrayList<>();
        try {
            Document document = Jsoup.connect("http://feisuzhibo.com").timeout(30000).get();
            Element element = document.getElementsByClass("rightTab").first();
            if (element != null) {
                Elements elements = element.getElementsByTag("div");
                for (Element e : elements) {
                    if (e.attr("class").equals("tab") || !TextUtils.isEmpty(e.attr("type"))) {
                        if (e.attr("class").equals("tab")) {
                            dataList.add(new NBABean(e.text(), NBABean.DATE));
                        } else {
                            String url = "http://feisuzhibo.com" + e.select("div.play > a").attr("href");
                            String time = e.select("div.timer").text();
                            String title = e.select("div.name").text();
                            String score = e.select("div.srcoe").text();
                            Logger.d("score: " + score);
                            Logger.d("score: " + score);
                            String homeTeamImg = e.select("div.home_team > img").attr("src");
                            String homeTeam = e.select("div.home_team").tagName("p").text();

                            String visitingTeamImg = e.select("div.visit_team > img").attr("src");
                            String visitingTeam = e.select("div.visit_team").tagName("p").text();

                            dataList.add(new NBABean(time, title, score, homeTeam, homeTeamImg, visitingTeam, visitingTeamImg, url, NBABean.NORMAL));
                        }
                    }
                }
            }
//            Elements elements = document.getElementsByClass("content");
//            Element element = elements.get(0);
//            if (element != null) {
//                Elements item = element.select("a,p.date.myTime");
//                item.remove(0);// Delete first useless item.
//                for (Element e : item) {
//                    if (e.text().contains("vs")) {
//                        String url = e.attr("href");
//                        String titleTime = e.select("h2.tlt").text();
//                        String time = titleTime.split(" ")[0];
//                        String title = titleTime.split(" ")[1];
//                        String homeTem = e.select("div.left-team").text();
//                        String visitingTeam = e.select("div.right-team").text();
//                        dataList.add(new NBABean(time, title, homeTem, visitingTeam, url, NBABean.NORMAL));
//                    } else {
//                        dataList.add(new NBABean(e.text(), NBABean.DATE));
//                    }
//                }
//                if (dataList.size() > 2 && dataList.get(0).getItemType() == dataList.get(1).getItemType()) {
//                    dataList.remove(0);
//                }
            //  }
        } catch (Exception e) {
            emitter.onError(e);
            Log.i("Exception: ", e.toString());
        }
        return dataList;
    }

    public static List<NBABean> parseLiveDetail(String url) {
        List<NBABean> dataList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByAttribute("p");
            for (Element element : elements) {
                Logger.d(element.text());
            }
        } catch (Exception e) {
            Log.i("Exception: ", e.toString());
        }
        return dataList;
    }

    public static List<NBABean> parseLiveSignal(String url, ObservableEmitter<List<NBABean>> emitter) {
        List<NBABean> dataList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("button");
            for (Element element : elements) {
                if (!element.text().contains("备用") && !element.text().contains("复制地址")) {
                    String decodeUrl = URLDecoder.decode(SystemUtil.getStrFromParen(element.toString()).split(",")[2].replaceAll("'", ""), "GBK");

                    NBABean bean = new NBABean();
                    bean.setTitle(element.text());
                    bean.setUrl(decodeUrl);
                    dataList.add(bean);
                }
            }
        } catch (IOException e) {
            emitter.onError(e);
            e.printStackTrace();
        }
        return dataList;
    }

    public static String[] parseOneImage(ObservableEmitter<String[]> emitter) {
        String[] array = new String[4];
        try {
            Document document = Jsoup.connect("http://wufazhuce.com/").get();
            Element element = document.selectFirst("div.item.active");
            String image = element.select("img.fp-one-imagen").attr("src");
            String type = element.getElementsByClass("fp-one-imagen-footer").text();
            String sentence = element.getElementsByTag("a").text();
            String number = element.getElementsByClass("titulo").text();

            array[0] = image;
            array[1] = type;
            array[2] = sentence;
            array[3] = number;

        } catch (IOException e) {
            emitter.onError(e);
            e.printStackTrace();
        }
        return array;
    }
}
