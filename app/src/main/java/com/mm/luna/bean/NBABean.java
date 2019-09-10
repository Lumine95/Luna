package com.mm.luna.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by ZMM on 2018/10/11 9:42.
 */
public class NBABean implements MultiItemEntity, Serializable {
    public static final int DATE = 1;
    public static final int NORMAL = 2;
    private int itemType;

    private String homeTeam;
    private String homeTeamImg;
    private String visitingTeam;
    private String visitingTeamImg;
    private String time;
    private String date;
    private String title;
    private String url;
    private String score;

    public NBABean(String date, int itemType) {
        this.date = date;
        this.itemType = itemType;
    }

    public NBABean(String[] split, int itemType) {
        time = split[0];
        homeTeam = split[1];
        visitingTeam = split[3];
        this.itemType = itemType;
    }

    public NBABean() {
    }

    public NBABean(String time, String title, String score, String homeTeam, String homeTeamImg, String visitingTeam, String visitingTeamImg, String url, int itemType) {
        this.time = time;
        this.title = title;
        this.homeTeam = homeTeam;
        this.homeTeamImg = homeTeamImg;
        this.visitingTeam = visitingTeam;
        this.visitingTeamImg = visitingTeamImg;
        this.score = score;
        this.url = url;
        this.itemType = itemType;
    }

    public String getHomeTeam() {
        return homeTeam == null ? "" : homeTeam;
    }

    public String getVisitingTeam() {
        return visitingTeam == null ? "" : visitingTeam;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public String getDate() {
        return date == null ? "" : date;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setVisitingTeam(String visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHomeTeamImg() {
        return homeTeamImg == null ? "" : homeTeamImg;
    }

    public void setHomeTeamImg(String homeTeamImg) {
        this.homeTeamImg = homeTeamImg;
    }

    public String getVisitingTeamImg() {
        return visitingTeamImg == null ? "" : visitingTeamImg;
    }

    public String getScore() {
        return score == null ? "" : score;
    }


    @Override
    public int getItemType() {
        return itemType;
    }
}
