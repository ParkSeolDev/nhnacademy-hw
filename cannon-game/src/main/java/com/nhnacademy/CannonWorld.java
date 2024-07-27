package com.nhnacademy;

import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.awt.event.ComponentEvent;

public class CannonWorld extends MovableWorld implements ComponentListener, KeyListener {
    static final int WALL_THICKNESS = 200;
    static final int BAR_WIDTH = 100;
    static final int BAR_THICKNESS = 20;
    static final int BAR_SPEED = 10;
    static final int MIN_WIDTH = WALL_THICKNESS * 2 + BAR_WIDTH;
    static final int MIN_HEIGHT = WALL_THICKNESS * 2 + BAR_THICKNESS;
    static final int OBSTACLE_WIDTH = 200;
    int blockHeight = 40;
    int blockWidth = 40;
    Vector gravity = new Vector(0, 1);
    Vector windSpeed = new Vector(0, 0);
    Vector angle = new Vector(20, -5);
    int angle1p = 20;
    Vector angle2 = new Vector(-27, -10);
    int angle2p = 150;
    final GameObject topWall;
    final GameObject bottomWall;
    final GameObject leftWall;
    final GameObject rightWall;
    final GameObject cannon;
    final GameObject cannon2;
    ExecutorService threadPool = Executors.newFixedThreadPool(5);
    final List<GameObject> boxList = new LinkedList<>();
    GameObject goal = null;

    public CannonWorld(int x, int y, int width, int height) {
        super();

        setBounds(x, y, width, height);

        GameObjectFactory gameObjectFactory = new GameObjectFactory();

        leftWall = (GameObject) gameObjectFactory.createWall(-WALL_THICKNESS, 0, WALL_THICKNESS, height);
        rightWall = (GameObject) gameObjectFactory.createWall(width, 0, WALL_THICKNESS, height);
        topWall = (GameObject) gameObjectFactory.createWall(0, -WALL_THICKNESS, width, WALL_THICKNESS);
        bottomWall = (GameObject) gameObjectFactory.createHell(0, height, width, WALL_THICKNESS);

        add(leftWall);
        add(rightWall);
        add(topWall);
        add(bottomWall);

        cannon = (GameObject) factory.createCannon(100, height - BAR_THICKNESS, BAR_WIDTH, BAR_THICKNESS);
        ((MovableObject)cannon).setPlayerId(1);
        add(cannon);

        cannon2 = (GameObject) factory.createCannon(900, height - BAR_THICKNESS, BAR_WIDTH, BAR_THICKNESS);
        ((MovableObject)cannon2).setPlayerId(2);
        add(cannon2);

        ((MovableObject)cannon).addMovedActionListener(() -> {
            if (cannon instanceof Movable) {
                for (int j = 0; j < getCount(); j++) {
                    GameObject other = getGameObject(j);
                    if (other instanceof Breakable && (cannon != other && cannon.isCollision(other))) {
                            cannon.hit(other);
                            if (other instanceof HitListener) {
                                ((HitListener) other).hit(cannon);
                            }
                        
                    }
                }
            }
        });

        cannon.setHitListener(other -> {
            if (other instanceof Breakable) {
                cannon.setLocation(other.getX() - BAR_WIDTH, cannon.getY());
            }
        });

        ((MovableObject)cannon2).addMovedActionListener(() -> {
            if (cannon2 instanceof Movable) {
                for (int j = 0; j < getCount(); j++) {
                    GameObject other = getGameObject(j);
                    if (other instanceof Breakable && (cannon2 != other && cannon2.isCollision(other))) {
                            cannon2.hit(other);
                            if (other instanceof HitListener) {
                                ((HitListener) other).hit(cannon2);
                            }
                        
                    }
                }
            }
        });

        cannon2.setHitListener(other -> {
            if (other instanceof Breakable) {
                cannon2.setLocation(other.getX() + other.getWidth(), cannon2.getY());
            }
        });

        setFocusable(true);
        addKeyListener(this);
        addComponentListener(this);
    }

    public void init() {
        Random random = new Random();
        int y = getHeight() - blockHeight / 2;
        for (int line = 0; line < 8; line++) {
            int x = 100 + BAR_WIDTH * 2;

            if (line == 0) {
                while (x + blockWidth <= (100 + OBSTACLE_WIDTH * 2) + blockWidth * 8) {
                    GameObject box = (GameObject) factory.createBrick(x, y, blockWidth, blockHeight);
                    boxList.add(box);
                    add(box);
                    box.setHitListener(other -> {
                        if (other instanceof Bounceable)
                            remove(box);});
                    x += blockWidth;

                }
            } else {
                int randomBlock = random.nextInt(1, 9);
                for (int i = 0; i < 8 - randomBlock; i++) {
                    x += blockWidth;
                }
                while (x + blockWidth <= (100 + OBSTACLE_WIDTH * 2) + blockWidth * randomBlock) {
                    GameObject box = (GameObject) factory.createBrick(x, y, blockWidth, blockHeight);
                    boxList.add(box);
                    add(box);
                    box.setHitListener(other -> remove(box));
                    x += blockWidth;
                }
            }

            y -= blockHeight;
        }

    }

    @Override
    public void add(GameObject object) {
        super.add(object);
        if (object instanceof Bounceable) {
            threadPool.execute((BounceableObject) object);
        }
    }

    public void start(GameObject cannon, int playerNumber) {
        BounceableObject ball = (BounceableObject) factory.createBall(cannon.getCenterX(),
                cannon.getCenterY() - BAR_THICKNESS / 2 - 10, 10);
        if (playerNumber == 1)
            ball.setVector(angle);
        if (playerNumber == 2)
            ball.setVector(angle2);

        ball.setShooterId(playerNumber);
        ball.setDT(getDT());
        ball.addStartedActionListener(() -> {

        });


        ball.setHitListener(other2 -> {
            if (other2 instanceof End) {
                ball.stop();
                remove(ball);
            }
            if (other2 instanceof Breakable)
                remove(other2);
            Vector motion = ((MovableObject) ball).getMotion();

            motion.multiply(0.5);
            ((MovableObject) ball).setVector(motion);
        });

        ball.addMovedActionListener(() -> {
            Vector newMotion = ball.getMotion();
            newMotion.add(gravity);
            newMotion.add(windSpeed);

            ball.setVector(newMotion);

            if (ball instanceof Bounceable) {
                for (int j = 0; j < getCount(); j++) {
                    GameObject other = getGameObject(j);

                    if (other instanceof Bounceable && ball != other && ball.isCollision(other)) {
                        ball.bounce(other);
                        double overlapX = ball.getRegion().getMaxX() - other.getRegion().getMinX();
                        double overlapY = ball.getRegion().getMaxY() - other.getRegion().getMinY();
                        if (overlapX < overlapY) {
                            if (ball.getX() < other.getX()) {
                                ball.setX(ball.getX() - (int)overlapX);
                                other.setX(other.getX() + (int)overlapX);
                            } else {
                                ball.setX(ball.getX() + (int)overlapX);
                                other.setX(other.getX() - (int)overlapX);
                            }
                        } else {
                            if (ball.getY() < other.getY()) {
                                ball.setY(ball.getY() - (int)overlapY);
                                other.setY(other.getY() + (int)overlapY);
                            } else {
                                ball.setY(ball.getY() + (int)overlapY);
                                other.setY(other.getY() - (int)overlapY);
                            }
                        }
                        ball.hit(other);
                        if (other instanceof HitListener) {
                            ((HitListener) other).hit(ball);
                        }

                    }

                    if (ball != other && ball.isCollision(other)) {
                        if (other instanceof Movable
                                && (((MovableObject) other).getPlayerId() != ball.getShooterId())) {
                            remove(other);
                            System.out.println(ball.getShooterId()+"P 승리");
                            System.exit(0);
                        }
                        ball.bounce(other);
                        ball.hit(other);
                        if (other instanceof HitListener) {
                            ((HitListener) other).hit(ball);
                        }
                    }
                }
            }
        });

        add(ball);
    }

    public void clear() {
        List<GameObject> removeList = new LinkedList<>();

        for (int i = 0; i < getCount(); i++) {
            if (getGameObject(i) instanceof Bounceable) {
                removeList.add(getGameObject(i));
            }
        }

        for (GameObject gameObject : removeList) {
            ((BounceableObject) gameObject).stop();
            remove(gameObject);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public void setWindSpeed(int value) {
        windSpeed.setVector(new Vector(value, 0));
    }

    public void setSpeed(int value) {
        setDT((DEFAULT_DT - value) / 10);

    }

    public void setAngle(int angle, int playerNumber) {
        if (playerNumber == 1) {
            int balance = angle / 5 * 5;
            int x = 20;
            int y = -15;

            while (balance != 45) {
                if (balance < 45) {
                    balance += 5;
                    x += 2;
                    y += 2;
                } else if (balance > 45) {
                    balance -= 5;
                    x -= 2;
                    y -= 2;
                } else {
                    break;
                }
            }
            this.angle.set(x, y);
        } else {
            int balance = angle / 5 * 5;
            int x = 19;
            int y = -15;

            while (balance != 135) {
                if (balance < 135) {
                    balance += 5;
                    x -= 2;
                    y -= 2;
                } else if (balance > 135) {
                    balance -= 5;
                    x += 2;
                    y += 2;
                } else {
                    break;
                }
            }
            
            this.angle2.set(-x, y);
        }
    }

    public void setGravity(int value) {
        gravity.setVector(new Vector(0, value));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_D) {
            ((MovableObject) cannon).move(new Vector(-BAR_SPEED, 0));
            if (((MovableObject) cannon).getMinX() < 0) {
                ((MovableObject) cannon).setLocation(((MovableObject) cannon).getX(), ((MovableObject) cannon).getY());
            }
        } else if (event.getKeyCode() == KeyEvent.VK_G) {
            ((MovableObject) cannon).move(new Vector(BAR_SPEED, 0));
            if (((MovableObject) cannon).getMaxX() > getWidth()) {
                ((MovableObject) cannon).setLocation(getWidth() - ((MovableObject) cannon).getWidth() / 2,
                        ((MovableObject) cannon).getY());
            }
        } else if (event.getKeyCode() == KeyEvent.VK_R) {
            angle1p += 5;
            if (angle1p >= 90) {
                angle1p = 90;
            }
            setAngle(angle1p, 1);
        } else if (event.getKeyCode() == KeyEvent.VK_F) {
            angle1p -= 5;
            if (angle1p <= 0) {
                angle1p = 0;
            }
            setAngle(angle1p, 1);
        } else if (event.getKeyCode() == KeyEvent.VK_A && (!hasBall())) {
            start(cannon, 1);
        }

        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            ((MovableObject) cannon2).move(new Vector(-BAR_SPEED, 0));
            if (((MovableObject) cannon2).getMinX() < 0) {
                ((MovableObject) cannon2).setLocation(((MovableObject) cannon2).getX(),
                        ((MovableObject) cannon).getY());
            }
        } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            ((MovableObject) cannon2).move(new Vector(BAR_SPEED, 0));
            if (((MovableObject) cannon2).getMaxX() > getWidth()) {
                ((MovableObject) cannon2).setLocation(getWidth() - ((MovableObject) cannon2).getWidth() / 2,
                        ((MovableObject) cannon).getY());
            }
        } else if (event.getKeyCode() == KeyEvent.VK_UP) {
            angle2p -= 5;
            if (angle2p <= 90) {
                angle2p = 90;
            }
            setAngle(angle2p, 2);
        } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            angle2p += 5;
            if (angle2p >= 170) {
                angle2p = 170;
            }
            setAngle(angle2p, 2);
        } else if (event.getKeyCode() == KeyEvent.VK_L && (!hasBall())) {
            start(cannon2, 2);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
