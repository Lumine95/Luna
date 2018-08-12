package com.mm.luna.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZMM on 2018/8/11  17:39.
 */
public class GankBean {
    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id == null ? "" : _id;
        }

        public String getCreatedAt() {
            return createdAt == null ? "" : createdAt;
        }

        public String getDesc() {
            return desc == null ? "" : desc;
        }

        public String getPublishedAt() {
            return publishedAt == null ? "" : publishedAt.split("T")[0];
        }

        public String getSource() {
            return source == null ? "" : source;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public boolean isUsed() {
            return used;
        }

        public String getWho() {
            return who == null ? "" : who;
        }

        public List<String> getImages() {
            if (images == null) {
                return new ArrayList<>();
            }
            return images;
        }
    }
}
