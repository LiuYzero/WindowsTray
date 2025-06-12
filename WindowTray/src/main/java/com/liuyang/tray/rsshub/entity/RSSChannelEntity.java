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

    @Data
    static class RSSItemEntity{
        private String title;
        private String author;
        private String description;
        private String pubDate;

        private String url;
    }
}