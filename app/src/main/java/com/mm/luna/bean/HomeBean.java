package com.mm.luna.bean;

/**
 * Created by ZMM on 2018/9/29 16:01.
 */
public class HomeBean {
    private String month;   // month picture url
    private String tts;   //  voice url of today English
    private String content;
    private String note;
    private String translation;
    private String picture2;
    private String fenxiang_img;

    public String getMonth() {
        return month == null ? "" : month;
    }

    public String getTts() {
        return tts == null ? "" : tts;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public String getNote() {
        return note == null ? "" : note;
    }

    public String getTranslation() {
        return translation == null ? "" : translation;
    }

    public String getPicture() {
        return picture2  == null ? "" : picture2;
    }

    public String getFenxiang_img() {
        return fenxiang_img == null ? "" : fenxiang_img;
    }
}
