package com.liuyang.tray.rsshub.v2ex;

import com.liuyang.tray.rsshub.bilibili.BiliSubmissionService;
import com.liuyang.tray.rsshub.entity.RSSChannelEntity;
import com.liuyang.tray.rsshub.entity.RSSItemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * V2ex
 * @author liuyang
 * @since 2025/06/17
 */
public class V2exServices {
    private static final Logger logger = LoggerFactory.getLogger(V2exServices.class);

    public static void main(String[] args) {
        new V2exServices().start();
    }

    public  void start() {
        List<String> BiliUserSpaceUrls = Arrays.asList(
                "https://rsshub.app/v2ex/topics/latest"
        );

        for(String url : BiliUserSpaceUrls){
            RSSChannelEntity rssEntity = new BiliSubmissionService().getRssEntityByUrl(url);
            List<RSSItemEntity> items = rssEntity.getItems();
            RSSItemEntity firstItems = items.get(0);
            logger.info("firstItems is {}", firstItems);


        }
    }
}
