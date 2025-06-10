package com.liuyang.tray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomTrayIcon extends TrayIcon {
    private JPopupMenu popupMenu;
    private final SystemTray systemTray;
    private final TrayIcon trayIcon;

    public CustomTrayIcon(Image image, String tooltip) {
        super(image, tooltip);
        this.systemTray = SystemTray.getSystemTray();
        this.trayIcon = this;

        // 初始化弹出菜单
        createPopupMenu();

        // 添加鼠标监听器
        addMouseListener(new TrayMouseListener());
    }

    private void createPopupMenu() {
        popupMenu = new JPopupMenu();

        // 添加菜单项
        JMenuItem openItem = new JMenuItem("打开",new ImageIcon(CustomTrayIcon.class.getResource("/favicon-16x16.png")));
        openItem.setFont(new Font("华文宋体",Font.PLAIN,20));
        openItem.addActionListener(e -> {
            // 这里可以添加打开应用程序的逻辑
            JOptionPane.showMessageDialog(null, "打开应用程序");
        });

        // 推出先篡改
        JMenuItem exitItem = new JMenuItem("退出",new ImageIcon(CustomTrayIcon.class.getResource("/favicon-16x16.png")));
        exitItem.setFont(new Font("退出",Font.PLAIN,20));
        exitItem.addActionListener(e -> {
            systemTray.remove(trayIcon);
            System.exit(0);
        });

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

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger()) {
//                showPopupMenu(e);
            }
        }

        private void showPopupMenu(MouseEvent e) {
            // 为了显示JPopupMenu，我们需要一个可见组件作为父组件
            // 这里我们创建一个不可见的JFrame作为父组件
            JFrame frame = new JFrame();
            frame.setIconImage(new ImageIcon(CustomTrayIcon.class.getResource("/favicon-16x16.png")).getImage());
            frame.setUndecorated(true);
            frame.setVisible(true);
            frame.setLocation(e.getX(), e.getY());

            // 显示弹出菜单
            popupMenu.show(e.getComponent(), e.getX(), e.getY());

            // 菜单关闭后销毁临时frame
            popupMenu.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {}

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

    // 测试代码
    public static void main(String[] args) {
        try {
            // 检查系统是否支持系统托盘
            if (!SystemTray.isSupported()) {
                System.out.println("SystemTray is not supported");
                return;
            }

            // 创建系统托盘图标
            Image trayImage = Toolkit.getDefaultToolkit().getImage(
                    CustomTrayIcon.class.getResource("/favicon-16x16.png"));
            CustomTrayIcon trayIcon = new CustomTrayIcon(trayImage, "My Tray");

            // 将托盘图标添加到系统托盘
            SystemTray.getSystemTray().add(trayIcon);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}