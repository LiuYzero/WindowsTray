package com.liuyang.tray.rsshub.bilibili;

import com.liuyang.tray.rsshub.entity.RSSChannelEntity;
import com.liuyang.tray.rsshub.entity.RSSItemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * B站服务
 * @author liuyang
 * @since 2025/06/17
 */
public class BiliServices {
    private static final Logger logger = LoggerFactory.getLogger(BiliServices.class);

    public  void start() {
        // 2025年6月17日
        // B站用户投稿解析失败，代码不做处理
        List<String> BiliUserSpaceUrls = Arrays.asList(
                "https://rsshub.app/bilibili/user/video/2267573"
        );

        for(String url : BiliUserSpaceUrls){
            RSSChannelEntity rssEntity = new BiliSubmissionService().getRssEntityByUrl(url);
            List<RSSItemEntity> items = rssEntity.getItems();
            RSSItemEntity firstItems = items.get(0);
            logger.info("firstItems is {}", firstItems);


        }
    }
}
