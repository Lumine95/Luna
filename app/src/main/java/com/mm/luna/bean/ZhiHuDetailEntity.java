package com.mm.luna.bean;

import java.util.List;

/**
 * Created by ZMM on 2018/1/30.
 */

public class ZhiHuDetailEntity {

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private List<String> js;
    private String ga_prefix;
    private List<String> images;
    private int type;
    private long id;
    private List<String> css;

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public List<String> getJs() {
        return js;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getImages() {
        return images;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public List<String> getCss() {
        return css;
    }

}