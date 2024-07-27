package com.nhnacademy;

import java.awt.Color;

public class MovableObject extends PaintableObject implements Movable, Runnable {
    private int playerId;
    private Color color;
    private GameObject gameObject;
    protected Vector vector;
    boolean stopped = true;
    long dt = 0;
    StartedActionListener startedActionListener;
    MovedActionListener movedActionListener;
    Thread thread;

    public MovableObject(GameObject gameObject) {
        super(gameObject.getX(), gameObject.getY(), gameObject.getWidth(), gameObject.getHeight());
        this.gameObject = gameObject;
        setColor(((PaintableObject) gameObject).getColor());
        vector = new Vector();
    }

    public void setDT(long dt) {
        this.dt = dt;
    }

    public long getDT() {
        return dt;
    }

    public Vector getMotion() {
        return vector;
    }

    public void setVector(Vector newMotion) {
        this.vector.setVector(newMotion);
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
        move(vector);
        moveTo(getX() + vector.getDX(), getY() + vector.getDY());
    }
    
    public void move(Vector motion) {
        this.translate(motion);
        setLocation(this.getX(), this.getY());

        if (movedActionListener != null) {
            movedActionListener.action();
        }
    }
    
    public void moveTo(int x, int y) {
        setLocation(x, y);
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

    @Override
    public void run() {
        thread = Thread.currentThread();
        stopped = false;

        if (startedActionListener != null) {
            startedActionListener.action();
        }

        while (!stopped) {
            try {
                move();

                Thread.sleep(dt);
            } catch (InterruptedException ignore) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        stopped = true;
        if (thread != null) {
            thread.interrupt();
        }
    }

    public void addStartedActionListener(StartedActionListener listener) {
        this.startedActionListener = listener;
    }

    public void addMovedActionListener(MovedActionListener listener) {
        this.movedActionListener = listener;
    }

    public void setPlayerId(int id) {
        this.playerId = id;
    }

    public int getPlayerId() {
        return this.playerId;
    }
}
