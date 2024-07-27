package com.nhnacademy;

import java.awt.Graphics;
import java.awt.Rectangle;

public class BounceableObject extends MovableObject implements Bounceable {
    private GameObject gameObject;
    private int shooterId;
    public BounceableObject(GameObject movableObject) {
        super(movableObject);
        this.region = movableObject.getRegion();
        gameObject = movableObject;
        setColor(((PaintableObject) movableObject).getColor());
        vector = ((MovableObject) gameObject).getMotion();
    }
    
    @Override
    public void bounce(GameObject other) {
        Rectangle intersection = getRegion().intersection(other.getRegion());
        if (getRegion().getHeight() != intersection.getHeight()) {
            vector.setDY(-vector.getDY());
        }
        if (getRegion().getWidth() != intersection.getWidth()) {
            vector.setDX(-vector.getDX());
        }
    }

    @Override
    public void paint(Graphics g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }
        g.fillOval((int) this.region.getX(), (int) this.region.getY(), gameObject.getRadius() * 2,
                gameObject.getRadius() * 2);
        g.setColor(this.getColor());
    }

    public void setShooterId(int id) {
        this.shooterId = id;
    }

    public int getShooterId() {
        return shooterId;
    }
}
