package com.mm.luna.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZMM on 2019/2/28 17:54.
 */
public class BingBean {
    private List<ImagesBean> images;
    public static class ImagesBean {
        private String url;
        private String urlbase;
        private String copyright;
        private String title;

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlbase() {
            return urlbase == null ? "" : urlbase;
        }

        public void setUrlbase(String urlbase) {
            this.urlbase = urlbase;
        }

        public String getCopyright() {
            return copyright == null ? "" : copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public List<ImagesBean> getImages() {
        if (images == null) {
            return new ArrayList<>();
        }
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }
}
