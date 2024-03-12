package com.nhnacademy;

import java.awt.Graphics;
import java.awt.Rectangle;

public class BounceableObject extends MovableObject implements Bounceable {
    private Vector vector;
    private GameObject gameObject;
    public BounceableObject(GameObject movableObject) {
        super(movableObject);
        this.region = movableObject.getRegion();
        gameObject = movableObject;
        setColor(((PaintableObect) movableObject).getColor());
        vector = ((MovableObject) gameObject).getMotion();
    }

    @Override
    public void bounce(Breakable other) {
        Rectangle intersection = getRegion().intersection(((GameObject)other).getRegion());
        if (gameObject.getRegion().getHeight() != intersection.getHeight()) {
            vector.setDY(-vector.getDY());
            this.setDY(-this.getDY());
        }
        if (gameObject.getRegion().getWidth() != intersection.getWidth()) {
            vector.setDX(-vector.getDX());
            this.setDX(-this.getDX());
        }
    }

    @Override
    public void bounce(GameObject other) {
        Rectangle intersection = getRegion().intersection(other.getRegion());
        if (gameObject.getRegion().getHeight() != intersection.getHeight()) {
            vector.setDY(-vector.getDY());
            this.setDY(-this.getDY());
        }
        if (gameObject.getRegion().getWidth() != intersection.getWidth()) {
            vector.setDX(-vector.getDX());
            this.setDX(-this.getDX());
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
}
