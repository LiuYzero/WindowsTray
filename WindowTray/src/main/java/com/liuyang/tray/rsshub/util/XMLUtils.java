package com.liuyang.tray.rsshub.util;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XML工具类
 *
 * @author liuyang
 * @since 2025/06/25
 */
public class XMLUtils {
    private static final Logger logger = LoggerFactory.getLogger(XMLUtils.class);


    /**
     * 从url中生成document对象
     *
     * @param url 网址
     * @return root
     */
    public static Document getDocument(String url) {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(url);
            return document;
        } catch (DocumentException e) {
            logger.error("url fail:  {} ", url);
            logger.error("",e);
        }

        return null;
    }

    /**
     * 创建空的XML Document对象
     *
     * @return <?xml version="1.0" encoding="UTF-8"?>
     */
    public static Document EmptyDocument(){
        return DocumentHelper.createDocument();

        /*
        SAXReader reader = new SAXReader();
        return reader.read(new StringReader(""));
         */
    }
}
