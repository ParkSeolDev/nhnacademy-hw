package com.nhnacademy;

import java.awt.Graphics;

public class EatableObject extends MovableObject implements Eatable {
    private GameObject gameObject;
    private Vector vector;
    public EatableObject(GameObject movableObject) {
        super(movableObject);
        gameObject = movableObject;
        setColor(((PaintableObect) movableObject).getColor());
        System.out.println("생성");
        vector = ((MovableObject) gameObject).getMotion();
    }

    @Override
    public int getY() {
        return gameObject.getY() + 30;
    }
    
    @Override
    public void move() {
        moveTo(gameObject.getX(), getY() + vector.getDY());
    }
    
    @Override
    public void moveTo(int x, int y) {
        gameObject.setX(x);
        gameObject.setY(y);
        region.setLocation(x, y);
    }
    
    @Override
    void setY(int y) {
        gameObject.setY(getY());
    }
    
    @Override
    public void paint(Graphics g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }
        int[] xPoints = { getX(), getX() + 50, getX() + 100, getX() + 25, getX() + 75, getX(), getX() - 75, getX() - 50, getX(), getX() - 25 };
        int[] yPoints = { getY(), getY() + 75, getY(), getY() + 50, getY() + 50, getY() + 100, getY() + 50, getY(),
                getY() + 75, getY() + 75 };
        
        int nPoints = 10;

        g.fillPolygon(xPoints, yPoints, nPoints);
        g.setColor(this.getColor());
    }
}
