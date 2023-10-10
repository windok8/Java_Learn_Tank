package com.wdk.tank;

import java.awt.*;

/**
 * @author : Windok
 * @date: 2023-09-28
 * @Description:
 * @version: 1.0
 */
public class Explode {

    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
    private int x, y;
    TankFrame tankFrame = null;
    private int step = 0;

    public Explode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;

        new Thread(() -> new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics g) {

        g.drawImage(ResourceMgr.explodes[step++], x, y, tankFrame);
        if (step >= ResourceMgr.explodes.length) {
            step = 0;
            tankFrame.explodes.remove(this);
        }
    }

}
