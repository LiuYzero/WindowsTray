package com.liuyang.tray.rsshub.entity;

import java.util.List;
import java.util.Date;

public class RssFeedEntity {
    private Channel channel;

    // Getters and Setters
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public static class Channel {
        private String title;
        private String link;
        private AtomLink atomLink;
        private String description;
        private String generator;
        private String webMaster;
        private String language;
        private Image image;
        private Date lastBuildDate;
        private int ttl;
        private List<Item> items;

        // Getters and Setters
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

        public AtomLink getAtomLink() {
            return atomLink;
        }

        public void setAtomLink(AtomLink atomLink) {
            this.atomLink = atomLink;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGenerator() {
            return generator;
        }

        public void setGenerator(String generator) {
            this.generator = generator;
        }

        public String getWebMaster() {
            return webMaster;
        }

        public void setWebMaster(String webMaster) {
            this.webMaster = webMaster;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public Date getLastBuildDate() {
            return lastBuildDate;
        }

        public void setLastBuildDate(Date lastBuildDate) {
            this.lastBuildDate = lastBuildDate;
        }

        public int getTtl() {
            return ttl;
        }

        public void setTtl(int ttl) {
            this.ttl = ttl;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }
    }

    public static class AtomLink {
        private String href;
        private String rel;
        private String type;

        // Getters and Setters
        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getRel() {
            return rel;
        }

        public void setRel(String rel) {
            this.rel = rel;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Image {
        private String url;
        private String title;
        private String link;

        // Getters and Setters
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

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
    }

    public static class Item {
        private String title;
        private String description;
        private String link;
        private String guid;
        private boolean isPermaLink;
        private Date pubDate;
        private String author;
        private String category;

        // Getters and Setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public boolean isPermaLink() {
            return isPermaLink;
        }

        public void setPermaLink(boolean permaLink) {
            isPermaLink = permaLink;
        }

        public Date getPubDate() {
            return pubDate;
        }

        public void setPubDate(Date pubDate) {
            this.pubDate = pubDate;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}