package com.nhnacademy;


public class MovableWorld extends World {
    private static final int DEFAULT_DT = 50;
    private static final int MAX_SCORE = 10;

    private int moveCount;
    private int maxMoveCount;
    private int dt = DEFAULT_DT;

    private boolean isEaten = false;

    public void reset() {
        this.moveCount = 0;
    }
    
    public void move() {
        if ((getMaxMoveCount() == 0) || getMaxMoveCount() > getMoveCount()) {
            for (GameObject object : getGameObjects()) {
                if (object instanceof Movable) {
                    ((Movable) object).move();
                    if (object instanceof Movable && !(object instanceof Bounceable) && !(object instanceof Eatable)) {
                        for (int i = 0; i < getGameObjects().size(); i++) {
                            GameObject other = getGameObject(i);
                            if (other instanceof Eatable && ((other != object) && object.isCollision(other))) {
                                remove(other);
                                setScore();
                                System.out.println("먹음");
                                break;
                            }
                        }
                    }

                    if (object instanceof Bounceable) {
                        for (int i = 0; i < getGameObjects().size(); i++) {
                            GameObject other = getGameObject(i);
                            if (other instanceof Eatable && ((other != object) && object.isCollision(other))) {
                                remove(other);
                                setScore();
                                
                            }
                            if (other instanceof Breakable) {
                                if ((other != object) && object.isCollision(other)) {
                                    ((Bounceable) object).bounce((Breakable) other);
                                    remove(other);
                                    setScore();
                                    logger.trace("ball({})와 ball({})이 충돌하였습니다.", object.getId(),
                                            other.getId());
                                }
                            } else if (other instanceof End) {
                                if ((other != object) && object.isCollision(other)) {
                                    ((Bounceable) object).bounce(other);
                                    remove(object);
                                    System.out.println("패배!");
                                    System.exit(0);
                                    logger.trace("ball({})와 ball({})이 충돌하였습니다.", object.getId(),
                                            other.getId());
                                }
                            } else {
                                if ((other != object) &&  object.isCollision(other)) {
                                    ((Bounceable) object).bounce(other);
                                    logger.trace("ball({})와 ball({})이 충돌하였습니다.", object.getId(),
                                            other.getId());
                                }
                            }
                        }
                    }
                }
            }
            moveCount++;
            repaint();
        }
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            move();
            if (getScore() >= MAX_SCORE) {
                System.out.println("승리!");
                System.exit(0);
            }

            if (getScore() == 3 && !getItemOn()) {
                createItem();
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
