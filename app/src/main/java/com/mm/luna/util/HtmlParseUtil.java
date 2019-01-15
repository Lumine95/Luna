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

import java.util.ArrayList;
import java.util.List;

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

    public static List<NBABean> parseNBASchedule(String d) {
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
            Log.i("Exception: ", e.toString());
        }
        return scheduleList;
    }

    public static List<NBABean> parseLive() {
        List<NBABean> dataList = new ArrayList<>();
        try {
            Document document = Jsoup.connect("http://feisuzhibo.com/").get();
            Elements elements = document.getElementsByClass("content");
            Element element = elements.get(0);
            if (element != null) {
                Elements item = element.select("a,p.date.myTime");
                item.remove(0);// Delete first useless item.
                for (Element e : item) {
                    if (e.text().contains("vs")) {
                        String url = e.attr("href");
                        String titleTime = e.select("h2.tlt").text();
                        String time = titleTime.split(" ")[0];
                        String title = titleTime.split(" ")[1];
                        String homeTem = e.select("div.left-team").text();
                        String visitingTeam = e.select("div.right-team").text();
                        dataList.add(new NBABean(time, title, homeTem, visitingTeam, url, NBABean.NORMAL));
                    } else {
                        dataList.add(new NBABean(e.text(), NBABean.DATE));
                    }
                }
                if (dataList.size() > 2 && dataList.get(0).getItemType() == dataList.get(1).getItemType()) {
                    dataList.remove(0);
                }
            }

        } catch (Exception e) {
            Log.i("Exception: ", e.toString());
        }
        return dataList;
    }
}