package com.liuyang.tray.rsshub.v2ex;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.liuyang.tray.rsshub.entity.RSSChannelEntity;
import com.liuyang.tray.rsshub.util.XMLUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * V2ex订阅信息服务解析
 *
 * @author liuyang
 * @since 2025/06/25
 */
public class V2exSubmissionService {
    private static final Logger logger = LoggerFactory.getLogger(V2exSubmissionService.class);

    public static void main(String[] args) throws DocumentException {

        System.setProperty("http.proxyHost", "192.168.1.175");
        System.setProperty("http.proxyPort", "7897");

        System.setProperty("https.proxyHost", "192.168.1.175");
        System.setProperty("https.proxyPort", "7897");


        String tmp = "https://rsshub.app/v2ex/topics/latest";
        new V2exSubmissionService().getRssEntityByUrl(tmp);
    }

    /**
     * 根据url获取rss实体类
     * @return RSSChannelEntity
     */
    public RSSChannelEntity getRssEntityByUrl(String url){
        RSSChannelEntity rssChannelEntity = null;
        try {
            JSONObject rssJSON = getRssJSONObjectByUrl(url);
            rssChannelEntity = JSON.parseObject(rssJSON.toJSONString(), RSSChannelEntity.class);
        } catch (DocumentException e) {
            logger.error("", e);
        }

        return rssChannelEntity;
    }


    /**
     * 根据url获取json信息
     *
     * @throws DocumentException 格式化信息异常
     */
    public JSONObject getRssJSONObjectByUrl(String url) throws DocumentException {
        Document document = XMLUtils.getDocument(url);
        Element root = document.getRootElement();

        JSONObject rssJSONObject = new JSONObject();

        Element channel = root.element("channel");
        String title =  channel.elementText("title");
        String link = channel.elementText("link");
        String description = channel.elementText("description");
        String lastBuildDate = channel.elementText("lastBuildDate");
        String ttl = channel.elementText("ttl");

        rssJSONObject.put("title", title);
        rssJSONObject.put("link", link);
        rssJSONObject.put("description", description);
        rssJSONObject.put("updateDate", formatPubDate(lastBuildDate));
        rssJSONObject.put("ttl", ttl);

        logger.info("title {}", title);
        logger.info("link {}", link);
        logger.info("description {}", description);
        logger.info("lastBuildDate {}", formatPubDate(lastBuildDate));
        logger.info("ttl {}", ttl);
        logger.info("================================");

        JSONArray itemsJSONArray = new JSONArray();

        List<Element> items = channel.elements("item");
        for(Element item : items){
            JSONObject itemJSONObject = new JSONObject();

            String itemTitle = item.elementText("title");;
            String itemDescription = item.elementText("description")
                    .replaceAll("&lt;","<")
                    .replaceAll("&quot;","\"")
                    .replaceAll("&gt;",">");

            String pubDate = item.elementText("pubDate");
            String author = item.elementText("author");
            String itemLink = item.elementText("link");

            itemJSONObject.put("title", itemTitle);
            itemJSONObject.put("pubDate", formatPubDate(pubDate));
            itemJSONObject.put("author", author);
            itemJSONObject.put("url", itemLink);
            itemJSONObject.put("description", itemDescription);

            logger.info("title {}", itemTitle);
            logger.info("description {}", itemDescription);
            logger.info("pubDate {}", formatPubDate(pubDate));
            logger.info("author {}", author);
            logger.info("url {}", itemLink);

            itemsJSONArray.add(itemJSONObject);

            logger.info("================================");
        }
        rssJSONObject.put("items", itemsJSONArray);

        logger.info("finally, rss json is {}", rssJSONObject.toJSONString());

        return rssJSONObject;
    }

    /**
     * 格式化Thu, 08 Feb 2024 14:16:54 GMT格式的事件
     * @param dateStr 时间
     * @return YYYY-MM-DD HH:mm:ss
     */
    public String formatPubDate(String dateStr){
        DateTimeFormatter gmtFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        ZonedDateTime zdt = ZonedDateTime.parse(dateStr, gmtFormatter);

        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return zdt.format(targetFormatter);
    }




}
