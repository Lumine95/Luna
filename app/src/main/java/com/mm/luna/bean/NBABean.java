package com.mm.luna.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by ZMM on 2018/10/11 9:42.
 */
public class NBABean implements MultiItemEntity {
    public static final int DATE = 1;
    public static final int NORMAL = 2;
    private int itemType;

    private String homeTem;
    private String visitingTeam;
    private String time;
    private String date;

    public NBABean(String date, int itemType) {
        this.date = date;
        this.itemType = itemType;
    }

    public NBABean(String[] split, int itemType) {
        time = split[0];
        homeTem = split[1];
        visitingTeam = split[3];
        this.itemType = itemType;
    }

    public String getHomeTem() {
        return homeTem == null ? "" : homeTem;
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

    @Override
    public int getItemType() {
        return itemType;
    }
}
