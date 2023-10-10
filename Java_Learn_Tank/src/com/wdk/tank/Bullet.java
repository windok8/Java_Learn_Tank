package com.wdk.tank;

import java.awt.*;

/**
 * @author : Windok
 * @date: 2023-09-26
 * @Description:
 * @version: 1.0
 */
public class Bullet {

    private static final int SPEED = 10;
    public static final int WIDTH = ResourceMgr.bulletU.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletU.getHeight();

    Rectangle rect = new Rectangle();

    private int x, y;
    private Dir dir;
    private Boolean live = true;
    private TankFrame tankFrame = null;
    private Group group = Group.BAD; // 默认为敌方子弹

    public Bullet(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g) {
        //  如果子弹死亡，从容器中移除
        if (!live) tankFrame.bullets.remove(this);
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }
        move();
    }

    private void move() {
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
        rect.x = this.x;
        rect.y = this.y;
        // 如果子弹飞出边界，死亡
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) live = false;

    }

    /**
     * @param tank 坦克
     * @Author: Windok
     * @Description: 子弹与坦克碰撞
     **/
    public void collideWith(Tank tank) {
        //  如果是同一方的子弹，不做处理
        if (this.group == tank.getGroup()) return;
        //  TODO: 用一个Rect来记录子弹的位置
        //  获取子弹和坦克的矩形
        //  Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        // 获取坦克的矩形
        //  Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), ((this.group == Group.BAD) ? Tank.BADTANK_WIDTH : Tank.GOODTANK_WIDTH), ((this.group == Group.BAD) ? Tank.BADTANK_HEIGHT : Tank.GOODTSNK_HEIGHT));
        //  判断两个矩形是否相交
        if (rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            fire_Explode(tank);
        }
    }

    private void fire_Explode(Tank tank) {
        int eX = 0;
        int eY = 0;
        switch (dir) {
            case LEFT:
            case RIGHT:
                eX = tank.getX() + ((this.group == Group.BAD) ? Tank.BADTANK_HEIGHT : Tank.GOODTSNK_HEIGHT) / 2 - Explode.WIDTH / 2;
                eY = tank.getY() + ((this.group == Group.BAD) ? Tank.BADTANK_WIDTH : Tank.GOODTANK_WIDTH) / 2 - Explode.HEIGHT / 2;
                break;
            case UP:
            case DOWN:
                eX = tank.getX() + ((this.group == Group.BAD) ? Tank.BADTANK_WIDTH : Tank.GOODTANK_WIDTH) / 2 - Explode.WIDTH / 2;
                eY = tank.getY() + ((this.group == Group.BAD) ? Tank.BADTANK_HEIGHT : Tank.GOODTSNK_HEIGHT) / 2 - Explode.HEIGHT / 2;
                break;
        }

        tankFrame.explodes.add(new Explode(eX, eY, tankFrame));
    }


    private void die() {
        this.live = false;

    }
}
