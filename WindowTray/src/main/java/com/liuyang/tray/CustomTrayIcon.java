package com.liuyang.tray;

import com.liuyang.tray.rsshub.bilibili.BiliServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * 自定义托盘图标类，继承自TrayIcon，实现了鼠标监听器以显示弹出菜单。
 *
 * @author liuyang
 * @version 1.0
 * @since 2025/06/10 23:34
 */
public class CustomTrayIcon extends TrayIcon {

    private static final Logger logger = LoggerFactory.getLogger(CustomTrayIcon.class);

    private JPopupMenu popupMenu;
    private final SystemTray systemTray;
    private final TrayIcon trayIcon;

    public static void main(String[] args) {
        try {
            if (!SystemTray.isSupported()) {
                logger.info("SystemTray is not supported");
                return;
            }
            Image trayImage = Toolkit.getDefaultToolkit().getImage(CustomTrayIcon.class.getResource("/favicon-16x16.png"));
            CustomTrayIcon trayIcon = new CustomTrayIcon(trayImage, "My Tray");
            SystemTray.getSystemTray().add(trayIcon);
            logger.info("tray start");
        } catch (Exception e) {
            logger.error("",e);
        }
    }

    public CustomTrayIcon(Image image, String tooltip) {
        super(image, tooltip);
        this.systemTray = SystemTray.getSystemTray();
        this.trayIcon = this;

        createPopupMenu();
        addMouseListener(new TrayMouseListener());
    }

    private void createPopupMenu() {
        popupMenu = new JPopupMenu();

        JMenuItem whisperItem = new JMenuItem("Whisper",new ImageIcon(CustomTrayIcon.class.getResource("/whisper-16x16.png")));
        whisperItem.setFont(new Font("华文宋体",Font.PLAIN,20));
        whisperItem.addActionListener(e -> {
            new BiliServices().start();

        });


        JMenuItem openItem = new JMenuItem("打开",new ImageIcon(CustomTrayIcon.class.getResource("/favicon-16x16.png")));
        openItem.setFont(new Font("华文宋体",Font.PLAIN,20));
        openItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "打开应用程序");
        });

        JMenuItem exitItem = new JMenuItem("退出",new ImageIcon(CustomTrayIcon.class.getResource("/exit-16x16.png")));
        exitItem.setFont(new Font("退出",Font.PLAIN,20));
        exitItem.addActionListener(e -> {
            systemTray.remove(trayIcon);
            System.exit(0);
        });

        popupMenu.add(whisperItem);
        popupMenu.add(openItem);
        popupMenu.addSeparator();
        popupMenu.add(exitItem);
    }

    private class TrayMouseListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                showPopupMenu(e);
            }
        }

        private void showPopupMenu(MouseEvent e) {
            // 为了显示JPopupMenu，我们需要一个可见组件作为父组件
            // 这里我们创建一个不可见的JFrame作为父组件
            JFrame frame = new JFrame();
            frame.setIconImage(new ImageIcon(CustomTrayIcon.class.getResource("/favicon-16x16.png")).getImage());
            frame.setUndecorated(true);
            frame.setVisible(true);
            frame.setLocation(e.getXOnScreen()+10, e.getYOnScreen()-popupMenu.getPreferredSize().width-10);

            // 显示弹出菜单
            popupMenu.show(frame, 0, 0);

            // 菜单关闭后销毁临时frame
            popupMenu.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                }

                @Override
                public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
                    frame.dispose();
                }

                @Override
                public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {
                    frame.dispose();
                }
            });
        }
    }
}