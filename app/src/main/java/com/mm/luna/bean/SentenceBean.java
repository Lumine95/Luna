package com.mm.luna.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZMM on 2018/8/17 14:20.
 */
public class SentenceBean {
    private String page;
    private List<ResultBean> list;

    public static class ResultBean {
        private String text;
        private String desc;
        private String url;
        private String pic;

        public String getText() {
            return text == null ? "" : text.trim();
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDesc() {
            return desc == null ? "" : desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPic() {
            return pic == null ? "" : pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

    public String getPage() {
        return page == null ? "" : page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<ResultBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ResultBean> list) {
        this.list = list;
    }
}
