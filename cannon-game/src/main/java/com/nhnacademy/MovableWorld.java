package com.nhnacademy;

import java.util.List;
import java.util.ArrayList;

public class MovableWorld extends World {
    static final int DEFAULT_DT = 1050;

    private int moveCount;
    private int maxMoveCount;
    private int dt = DEFAULT_DT;

    public void reset() {
        this.moveCount = 0;
    }
    
    public void move() {
        List<GameObject> removeList = new ArrayList<>();
        if ((getMaxMoveCount() == 0) || getMaxMoveCount() > getMoveCount()) {

            moveCount++;
            repaint();
        }
        for (GameObject gameObject : removeList) {
            remove(gameObject);
        }
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            move();
            
            List<GameObject> gameObjects = new ArrayList<>();
            for (GameObject gameObject : getGameObjects()) {
                if (gameObject instanceof BounceableObject) {
                    gameObjects.add(gameObject);
                }
            }

            try {
                Thread.sleep(getDT());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getMovementCount() {
        return this.moveCount;
    }
    
    public int getMaxMoveCount() {
        return this.maxMoveCount;
    }

    public void setMaxMoveCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException();
        }
        this.maxMoveCount = count;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setDT(int dt) {
        if (dt < 0) {
            throw new IllegalArgumentException();
        }
        this.dt = dt;
    }

    public int getDT() {
        return dt;
    }
}
