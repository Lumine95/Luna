package com.mm.luna.util;

import com.mm.luna.bean.SentenceBean;

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
public class DocParseUtil {

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
}
