package com.liuyang.tray.rsshub.bilibili;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 使用yt-dlp下载B站视频<br/>
 */
public class BiliDownloadVideoServices {
    private static final Logger logger = LoggerFactory.getLogger(BiliDownloadVideoServices.class);

    /**
     * 视频下载
     * @param url b站视频url
     * @return success
     */
    public boolean downloadVideoByUrl(String url){
        ProcessBuilder processBuilder = new ProcessBuilder("powershell",
                "-Command",
                "cd D:/x/WhipserSchedulerProject; yt-dlp.exe "+ url);
        processBuilder.redirectErrorStream(true);
        Process process = null;
        try {
            process = processBuilder.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(),"GBK"));

            String line;
            while ((line = reader.readLine()) != null) {
                logger.info("{}", line);
            }

            System.out.println("Exit Code: " + process.exitValue());
            return process.exitValue() == 0;
        } catch (IOException ex) {
            logger.error("",ex);
            return false;
        }
    }
}
