package com.liuyang.tray.rsshub.bilibili;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;

public class BilibiliSubmissionService {

    public static void main(String[] args) throws DocumentException {
        new BilibiliSubmissionService().getRssEntityByUrl();
    }

    public void getRssEntityByUrl() throws DocumentException {
//        String rssContent = getSubmissionByUrl("http://192.168.1.107:1200/bilibili/user/dynamic/2267573");

        SAXReader reader = new SAXReader();
        Document document = reader.read("http://192.168.1.107:1200/bilibili/user/dynamic/2267573");
        System.out.println("document = " + document);
        System.out.println("channel.title "+document.getRootElement().element("channel").element("title").getText()); // channel.title DIYgod 的 bilibili 动态
    }


    public String getSubmissionByUrl(String userSpaceUrl){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.107:1200/bilibili/user/dynamic/2267573")
                .method("GET", null)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
//            System.out.println("responseBody = " + responseBody);
            return responseBody;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



}
