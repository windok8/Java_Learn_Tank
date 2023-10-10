package com.wdk.tank;

import java.awt.*;
import java.util.Random;

import static com.wdk.tank.ResourceMgr.goodTankL;
import static java.nio.file.Files.move;

/**
 * @author : Windok
 * @date: 2023-09-26
 * @Description:
 * @version: 1.0
 */
public class Tank {

    private int x, y;
    private Group group = Group.BAD;
    private static final int SPEED = 2;

    public static final int GOODTSNK_HEIGHT = ResourceMgr.goodTankU.getHeight();
    public static final int GOODTANK_WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int BADTANK_HEIGHT = ResourceMgr.badTankU.getHeight();
    public static final int BADTANK_WIDTH = ResourceMgr.badTankU.getWidth();
    private Dir dir = Dir.DOWN;
    private boolean moving = true;
    private TankFrame tankFrame = null;
    private Boolean living = true;
    private Random random = new Random();
    Rectangle rect = new Rectangle();

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Tank(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = (group == Group.BAD) ? ResourceMgr.badTankL.getWidth() : ResourceMgr.goodTankL.getWidth();
        rect.height = (group == Group.BAD) ? ResourceMgr.badTankL.getHeight() : ResourceMgr.goodTankL.getHeight();
        ;
    }


    public void paint(Graphics g) {
        //  判断坦克是否存活
        if (!living) {
            tankFrame.tanks.remove(this);
        }
        //  根据方向绘制坦克
        switch (dir) {
            case LEFT:
                g.drawImage((this.group == Group.BAD) ? ResourceMgr.badTankL : ResourceMgr.goodTankL, x, y, null);
                break;
            case RIGHT:
                g.drawImage((this.group == Group.BAD) ? ResourceMgr.badTankR : ResourceMgr.goodTankR, x, y, null);
                break;
            case UP:
                g.drawImage((this.group == Group.BAD) ? ResourceMgr.badTankU : ResourceMgr.goodTankU, x, y, null);
                break;
            case DOWN:
                g.drawImage((this.group == Group.BAD) ? ResourceMgr.badTankD : ResourceMgr.goodTankD, x, y, null);
                break;
        }
        move();
    }


    private void move() {
        if (!living) return;
        // 判断坦克是否移动
        if (!moving) return;

        // 根据方向移动坦克 : 根据 dir 的值判断坦克的移动方向
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }

        //  随机开火
        if (this.group == Group.BAD && random.nextInt(100) > 95) this.fire();
        //  随机改变方向
        randomDir();
        boundsCheck();
        rect.x = this.x;
        rect.y = this.y;
    }

    private void boundsCheck() {
        int tankH = (this.group == Group.BAD) ? Tank.BADTANK_HEIGHT : Tank.GOODTSNK_HEIGHT;
        int tankW = (this.group == Group.BAD) ? Tank.BADTANK_WIDTH : Tank.GOODTANK_WIDTH;
        if (this.x < 10) x = 10;
        if (this.y < 40) y = 40;
        if (this.x > TankFrame.GAME_WIDTH - tankW - 10)
            x = TankFrame.GAME_WIDTH - tankW - 10;
        if (this.y > TankFrame.GAME_HEIGHT - tankH - 10) y = TankFrame.GAME_HEIGHT - tankH - 10;
    }

    private void randomDir() {
        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            this.dir = Dir.values()[random.nextInt(4)];
        }
    }


    //  坦克开火
    public void fire() {
        int bullerX = 0, bullerY = 0;
        switch (dir) {
            case DOWN:
                bullerX = x + ((this.group == Group.BAD) ? Tank.BADTANK_WIDTH : Tank.GOODTANK_WIDTH) / 2 - Bullet.WIDTH / 2;
                bullerY = y + ((this.group == Group.BAD) ? Tank.BADTANK_HEIGHT : Tank.GOODTSNK_HEIGHT);
                break;
            case UP:
                bullerX = x + ((this.group == Group.BAD) ? Tank.BADTANK_WIDTH : Tank.GOODTANK_WIDTH) / 2 - Bullet.WIDTH / 2;
                bullerY = y;
                break;
            case LEFT:
                bullerX = x;
                bullerY = y + ((this.group == Group.BAD) ? Tank.BADTANK_WIDTH : Tank.GOODTANK_WIDTH) / 2 - Bullet.WIDTH / 2;
                break;
            case RIGHT:
                bullerX = x + ((this.group == Group.BAD) ? Tank.BADTANK_HEIGHT : Tank.GOODTSNK_HEIGHT);
                bullerY = y + ((this.group == Group.BAD) ? Tank.BADTANK_WIDTH : Tank.GOODTANK_WIDTH) / 2 - Bullet.WIDTH / 2;
                break;
        }
        tankFrame.bullets.add(new Bullet(bullerX, bullerY, this.dir, this.group, this.tankFrame));

    }

    public void die() {
        this.living = false;
    }
}
