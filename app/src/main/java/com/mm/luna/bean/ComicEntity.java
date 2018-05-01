package com.mm.luna.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZMM on 2018/5/11 14:05.
 */

public class ComicEntity {

    private List<ComicEntity> data;


    private String videoName;
    private String cover;
    private String videoUrl;
    private List<ComicEntity> videoList;

    public List<ComicEntity> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<ComicEntity> data) {
        this.data = data;
    }

    public String getVideoName() {
        return videoName == null ? "" : videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getCover() {
        return cover == null ? "" : cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<ComicEntity> getVideoList() {
        if (videoList == null) {
            return new ArrayList<>();
        }
        return videoList;
    }

    public void setVideoList(List<ComicEntity> videoList) {
        this.videoList = videoList;
    }

    public String getVideoUrl() {
        return videoUrl == null ? "" : videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
