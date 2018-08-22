package com.mm.luna.bean;

/**
 * Created by ZMM on 2018/8/22 15:53.
 */
public class CFanBean {
    private String category;
    private String title;
    private String image;
    private String url;
    private String intro;
    private String author;

    public CFanBean() {

    }

    public CFanBean(String category, String title, String image, String url, String intro, String author) {
        this.category = category;
        this.title = title;
        this.image = image;
        this.url = url;
        this.intro = intro;
        this.author = author;
    }

    public String getCategory() {
        return category == null ? "" : category;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public String getImage() {
        return image == null ? "" : image;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public String getIntro() {
        return intro == null ? "" : intro;
    }

    public String getAuthor() {
        return author == null ? "" : author;
    }
}
