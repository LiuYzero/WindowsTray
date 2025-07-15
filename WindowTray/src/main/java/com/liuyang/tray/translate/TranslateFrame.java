package com.liuyang.tray.translate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.liuyang.tray.CustomTrayIcon;
import com.liuyang.tray.sqlite.SQLiteUitls;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 翻译窗口
 */
public class TranslateFrame {

    public static final Logger logger = LoggerFactory.getLogger(TranslateFrame.class);

    public static String translateText = "";
    public static JTextField textField;
    public static JTextArea textArea;

    public  void initFrame() {
        // 创建 JFrame 对象，设置标题和大小
        JFrame frame = new JFrame("Translate");
        frame.setSize(800, 600);

        // 加载 PNG 图标文件
        ImageIcon icon = new ImageIcon(TranslateFrame.class.getResource("/translate_ico.png"));
        frame.setIconImage(icon.getImage());

        // 启用绝对布局管理器
        frame.setLayout(null);

        // 添加 JLabel 提示信息
        JLabel label = new JLabel("请输入文字:");
        label.setBounds(50, 20, 100, 25);  // x=50，y=20，宽度=100，高度=25

        // 添加 JTextField 文本输入框
        textField = new JTextField();
        textField.setBounds(160, 20, 200, 25);  // x=160，y=20，宽度=200，高度=25

        // 添加 JTextArea 多行文本区域
        textArea = new JTextArea();
        textArea.setBounds(50, 70, 690, 450);  // x=50，y=70，宽度=690，高度=450
        textArea.setLineWrap(true);  // 自动换行
        textArea.setWrapStyleWord(true);  // 单词换行


        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                logger.info("input {}", input);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OllamaRequestBody ollamaRequestBody = new OllamaRequestBody(input);
                            logger.info("requestBody {}", ollamaRequestBody);
                            OkHttpClient client = new OkHttpClient.Builder()
                                    .connectTimeout(60, TimeUnit.SECONDS)
                                    .readTimeout(60, TimeUnit.SECONDS)
                                    .writeTimeout(60, TimeUnit.SECONDS)
                                    .build();

                            RequestBody requestBody = RequestBody.create(
                                    MediaType.parse("application/json; charset=utf-8"), JSONObject.toJSONString(ollamaRequestBody));

                            // 创建请求对象
                            Request request = new Request.Builder()

                                    .url(new URL("http://localhost:11434/api/generate"))
                                    .post(requestBody)
                                    .build();

                            Response response = client.newCall(request).execute();

                            if (response.isSuccessful()) {
                                String responseData = response.body().string();
                                logger.info("responseData {}", responseData);


                                JSONObject respData = JSONObject.parseObject(responseData);


                                String responseNode = respData.getString("response");
                                textArea.setFont(new Font("YaHei", Font.PLAIN, 18));
                                textArea.setText(responseNode+"\r\n");
                                boolean result = SQLiteUitls.insertTranslateData(input, responseNode);
                                logger.info("insert result {}", result);

                                save2pg(input,responseNode);
                            } else {
                                textArea.setText("请求失败，状态码: " + response.code());
                            }

                        } catch (Exception ex) {
                            logger.error("",ex);
                            translateText = ex.getMessage();
                            textArea.setText(translateText+"\r\n");
                        }
                    }
                }).start();

            }
        });



        // 将组件添加到 JFrame 中
        frame.add(label);
        frame.add(textField);
        frame.add(textArea);

        // 设置窗口关闭操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

    public void save2pg(String key,String value){
        JSONObject data = new JSONObject();
        data.put("key",key);
        data.put("value",value);
        logger.info("save2pg {}",data);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), JSONObject.toJSONString(data));

        // 创建请求对象
        Request request = null;
        try {
            request = new Request.Builder()

                    .url(new URL("http://192.168.1.103:16680/pg/translate/save"))
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            logger.info("repsonse {}",response.body().string());
        } catch (MalformedURLException e) {
            logger.error("",e);
        } catch (IOException e) {
            logger.error("",e);
        }


    }


}
