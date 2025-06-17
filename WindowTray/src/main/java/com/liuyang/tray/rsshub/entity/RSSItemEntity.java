package com.liuyang.tray.rsshub.entity;

import lombok.Data;

@Data
public class RSSItemEntity {
        private String title;
        private String author;
        private String description;
        private String pubDate;

        private String url;


        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getAuthor() {
                return author;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getPubDate() {
                return pubDate;
        }

        public void setPubDate(String pubDate) {
                this.pubDate = pubDate;
        }

        public String getUrl() {
                return url;
        }

        public void setUrl(String url) {
                this.url = url;
        }
}
