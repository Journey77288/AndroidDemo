package com.samp.demo.entity;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/1/9
 *     desc   :
 * </pre>
 */
public class RecyclerSampleEntity {
    private String avatar;
    private String title;
    private String desc;
    private boolean isSelected;

    public RecyclerSampleEntity(String avatar, String title, String desc, boolean isSelected) {
        this.avatar = avatar;
        this.title = title;
        this.desc = desc;
        this.isSelected = isSelected;
    }

    public String getTitle() {
        return title;
    }

    public RecyclerSampleEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public RecyclerSampleEntity setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public RecyclerSampleEntity setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public RecyclerSampleEntity setDesc(String desc) {
        this.desc = desc;
        return this;
    }
}
