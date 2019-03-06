package com.mm.luna.bean;

/**
 * Created by ZMM on 2019/2/27 14:33.
 */
public class FictionBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private DateBean date;
        private String author;
        private String title;
        private String digest;
        private String content;
        private int wc;

        public DateBean getDate() {
            return date;
        }

        public void setDate(DateBean date) {
            this.date = date;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getWc() {
            return wc+"";
        }

        public void setWc(int wc) {
            this.wc = wc;
        }

        public static class DateBean {

            private String curr;
            private String prev;
            private String next;

            public String getCurr() {
                return curr;
            }

            public void setCurr(String curr) {
                this.curr = curr;
            }

            public String getPrev() {
                return prev;
            }

            public void setPrev(String prev) {
                this.prev = prev;
            }

            public String getNext() {
                return next;
            }

            public void setNext(String next) {
                this.next = next;
            }
        }
    }
}
