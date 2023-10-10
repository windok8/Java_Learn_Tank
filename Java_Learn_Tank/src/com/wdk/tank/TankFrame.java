package com.wdk.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Windok
 * @date: 2023-09-26
 * @Description: 窗口类 继承 Frame 类 : 重写 paint 方法 --> 用于绘制窗口
 * 重写 update 方法 --> 用于解决窗口闪烁问题
 * 重写 windowClosing 方法 --> 用于监听窗口关闭事件
 * @version: 1.0
 */
public class TankFrame extends Frame {
    static final int GAME_WIDTH = 1080, GAME_HEIGHT = 560;
    Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Tank> tanks = new ArrayList<Tank>();
    List<Explode> explodes = new ArrayList<>();


    /**
     * @param
     * @Author: Windok
     * @Description: 构造函数 初始化窗口
     **/
    public TankFrame() {
        // 设置窗口大小 800 * 600
        setSize(GAME_WIDTH, GAME_HEIGHT);
        // 设置窗口可见
        setLocation(400, 100);
        // 设置窗口大小不可变
        setResizable(false);
        // 设置窗口标题
        setTitle("Tank War");
        // 设置窗口可见
        setVisible(true);
        // 设置窗口关闭事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 点击关闭按钮，退出程序
                System.exit(0);
            }
        });

        addKeyListener(new MyKeyListener());
    }


    Image offScreenImage = null;

    /**
     * @param g
     * @Author: Windok
     * @Description: 解决窗口闪烁问题
     **/
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }


    /**
     * @param
     * @Author: Windok
     * @Description: 绘制窗口
     **/
    @Override
    public void paint(Graphics g) {

        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量 : " + bullets.size(), 10, 60);
        g.drawString("坦克的数量 : " + tanks.size(), 10, 80);
        g.drawString("爆炸的数量 : " + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);
        for (int i = 0; i < bullets.size(); i++)
            bullets.get(i).paint(g);
        for (int i = 0; i < tanks.size(); i++)
            tanks.get(i).paint(g);
        for (int i = 0; i < explodes.size(); i++)
            explodes.get(i).paint(g);
        //  子弹与坦克碰撞检测
        for (int i = 00; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++)
                bullets.get(i).collideWith(tanks.get(j));
        }


    }

    /**
     * @param
     * @Author: Windok
     * @Description: 键盘监听类 继承 KeyAdapter 类 :
     * 重写 keyPressed 和 keyReleased 方法 --> 用于监听键盘事件
     **/
    class MyKeyListener extends KeyAdapter {

        //  按键持续按下 : 用于判断按下的是哪个键
        boolean bL = false;
        boolean bR = false;
        boolean bU = false;
        boolean bD = false;

        //  按键按下
        @Override
        public void keyPressed(KeyEvent e) {
            //  获取按键的 keyCode 值 : 用于判断按下的是哪个键
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
            }
            setMainTankDir();
        }


        // 按键抬起

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;

            }
        }

        //  根据按键状态设置坦克方向
        private void setMainTankDir() {
            if (!bL && !bR && !bU && !bD) myTank.setMoving(false);
            else {
                myTank.setMoving(true);
                if (bL) myTank.setDir(Dir.LEFT);
                if (bR) myTank.setDir(Dir.RIGHT);
                if (bU) myTank.setDir(Dir.UP);
                if (bD) myTank.setDir(Dir.DOWN);
            }
        }

    }
}
