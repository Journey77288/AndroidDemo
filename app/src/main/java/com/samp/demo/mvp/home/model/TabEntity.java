package com.samp.demo.mvp.home.model;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/2/24
 *     desc   : Tab Entity
 * </pre>
 */
public class TabEntity {
    private String text;
    private Integer icon;

    public TabEntity(String text, Integer icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public TabEntity setText(String text) {
        this.text = text;
        return this;
    }

    public Integer getIcon() {
        return icon;
    }

    public TabEntity setIcon(Integer icon) {
        this.icon = icon;
        return this;
    }
}
