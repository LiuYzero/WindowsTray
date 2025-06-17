package com.liuyang.tray.rsshub.entity;


import lombok.Data;

import java.util.List;

/**
 * B站的RSS实体类
 */
@Data
public class RSSChannelEntity {
    private String title;
    private String link;
    private String description;
    private String updateDate;
    private String ttl;
    private List<RSSItemEntity> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public List<RSSItemEntity> getItems() {
        return items;
    }

    public void setItems(List<RSSItemEntity> items) {
        this.items = items;
    }
}