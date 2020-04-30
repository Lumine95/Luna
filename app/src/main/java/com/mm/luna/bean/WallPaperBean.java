package com.mm.luna.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZMM on 2019/8/29  11:16.
 */
public class WallPaperBean {
    private String errno;
    private String errmsg;
    private String consume;
    private String total;
    private List<DataBean> data;

    public String getErrno() {
        return errno == null ? "" : errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg == null ? "" : errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getConsume() {
        return consume == null ? "" : consume;
    }

    public void setConsume(String consume) {
        this.consume = consume;
    }

    public String getTotal() {
        return total == null ? "" : total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String pid;
        private String cid;
        private String dl_cnt;
        private String c_t;
        private String imgcut;
        private String url;
        private String tempdata;
        private String fav_total;

        public String getPid() {
            return pid == null ? "" : pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getCid() {
            return cid == null ? "" : cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getDl_cnt() {
            return dl_cnt == null ? "" : dl_cnt;
        }

        public void setDl_cnt(String dl_cnt) {
            this.dl_cnt = dl_cnt;
        }

        public String getC_t() {
            return c_t == null ? "" : c_t;
        }

        public void setC_t(String c_t) {
            this.c_t = c_t;
        }

        public String getImgcut() {
            return imgcut == null ? "" : imgcut;
        }

        public void setImgcut(String imgcut) {
            this.imgcut = imgcut;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTempdata() {
            return tempdata == null ? "" : tempdata;
        }

        public void setTempdata(String tempdata) {
            this.tempdata = tempdata;
        }

        public String getFav_total() {
            return fav_total == null ? "" : fav_total;
        }

        public void setFav_total(String fav_total) {
            this.fav_total = fav_total;
        }
    }
}
