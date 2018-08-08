package com.mm.luna.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZMM on 2018/8/8 17:44.
 */
public class MovieDetailBean {
    private List<String> countries;
    private List<String> aka;
    private String summary;
    private String original_title;

    public List<String> getCountries() {
        if (countries == null) {
            return new ArrayList<>();
        }
        return countries;
    }

    public List<String> getAka() {
        if (aka == null) {
            return new ArrayList<>();
        }
        return aka;
    }

    public String getSummary() {
        return summary == null ? "" : summary;
    }

    public String getOriginal_title() {
        return original_title == null ? "" : original_title;
    }
}
