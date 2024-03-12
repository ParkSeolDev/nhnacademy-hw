package com.nhnacademy;

import java.awt.Rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GameObject implements Upgradable {
    private static int count = 0;
    private int id = ++count;

    protected Rectangle region;

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    GameObject(int x, int y, int width, int height) {
        validate(x, y, width, height);
        this.region = new Rectangle(x, y, width, height);
    }

    protected void validate(int x, int y, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("0보다 커야 합니다.");
        }

        if ((x + (long) (width / 2) > Integer.MAX_VALUE)
                || (x - (long) (width / 2) < Integer.MIN_VALUE)
                || (y + (long) (height / 2) > Integer.MAX_VALUE)
                || (y - (long) (height / 2) < Integer.MIN_VALUE)) {
            throw new IllegalArgumentException("박스가 정수 공간을 벗어납니다.");
        }
    }

    protected void validate(int x, int y, int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("반지름은 0보다 커야 합니다.");
        }

        if ((x + (long) radius > Integer.MAX_VALUE)
                || (x - (long) radius < Integer.MIN_VALUE)
                || (y + (long) radius > Integer.MAX_VALUE)
                || (y - (long) radius < Integer.MIN_VALUE)) {
            throw new IllegalArgumentException("볼이 정수 공간을 벗어납니다.");
        }
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return (int) region.getX();
    }

    public int getY() {
        return (int) region.getY();
    }

    void setX(int x) {
        region.setLocation(x, getY());
    }

    void setY(int y) {
        region.setLocation(getX(), y);
    }

    public Rectangle getRegion() {
        return region;
    }

    public synchronized void setRegion(int x, int y, int width, int height) {
        validate(x, y, width, height);
        region.setBounds(x - width / 2, y - height / 2, width, height);
    }

    public synchronized void setRegion(int x, int y, int radius) {
        validate(x, y, radius);
        region.setBounds(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", this.getX(), this.getY());
    }

    public boolean isCollision(Breakable other) {
        return getRegion().intersects(((GameObject)other).getRegion());
    }

    public boolean isCollision(GameObject other) {
        return getRegion().intersects(other.getRegion());
    }

    public int getWidth() {
        return (int) region.getWidth();
    }

    public int getHeight() {
        return (int) region.getHeight();
    }

    public int getRadius() {
        return (int) (region.getWidth() / 2);
    }
}