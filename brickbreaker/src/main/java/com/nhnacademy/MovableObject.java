package com.nhnacademy;

import java.awt.Color;

public class MovableObject extends PaintableObect implements Movable {
    private Color color;
    private GameObject gameObject;
    protected Vector vector;
    public MovableObject(GameObject gameObject) {
        super(gameObject.getX(), gameObject.getY(), gameObject.getWidth(), gameObject.getHeight());
        this.gameObject = gameObject;
        setColor(((PaintableObect) gameObject).getColor());
        vector = new Vector();
    }

    public Vector getMotion() {
        return vector;
    }

    public int getDX() {
        return vector.getDX();
    }

    public int getDY() {
        return vector.getDY();
    }

    void setDX(int dx) {
        vector.setVector(new Vector(dx, vector.getDY()));
    }

    void setDY(int dy) {
        vector.setVector(new Vector(vector.getDX(), dy));
    }

    @Override
    public void move() {
       
        moveTo(getX() + vector.getDX(), getY() + vector.getDY());
        logger.trace("{} : {}, {}", getId(), getX(), getY());
        
    }
    
    public void moveTo(int x, int y) {
        gameObject.setX(x);
        gameObject.setY(y);
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException();
        }
        this.color = color;
    }
}
