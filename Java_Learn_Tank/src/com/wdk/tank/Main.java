package com.wdk.tank;

import java.util.concurrent.TimeUnit;

/**
 * @author : Windok
 * @date: 2023-09-26
 * @Description:
 * @version: 1.0
 */
public class Main {

    /**
     * @param args
     * @Author: Windok
     * @Description: 主函数
     **/
    public static void main(String[] args) throws InterruptedException {

        TankFrame tankFrame = new TankFrame();
        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));

        //  初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            tankFrame.tanks.add(new Tank(50 + i * 80, 100, Dir.DOWN, Group.BAD, tankFrame));
        }

        new Thread(() -> new Audio("audio/war1.wav").loop()).start();

        while (true) {
            Thread.sleep(25);
            tankFrame.repaint();
        }

    }


}
