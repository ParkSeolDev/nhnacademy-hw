package com.nhnacademy;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class World extends JPanel implements MouseMotionListener {
    private List<GameObject> gameObjects;
    private Integer score = 0;
    private boolean itemOn = false;
    GameObjectFactory factory = new GameObjectFactory();
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    
    public World() {
        gameObjects = new ArrayList<>();
        
        Upgradable controlBar = factory.createControlBar(400, 650, 100, 50);
        gameObjects.add((GameObject) controlBar);
        addMouseMotionListener(this);
    }

    public void add(GameObject object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }

        for (GameObject existObject : gameObjects) {
            if (object instanceof Bounceable || existObject instanceof Bounceable && (object.getRegion().intersects(existObject.getRegion()))) {
                    throw new IllegalArgumentException();
            }
        }
        gameObjects.add(object);
    }

    public void addAll(List<GameObject> objects) {
        gameObjects.addAll(objects);
    }

    public void remove(GameObject object) {
        gameObjects.remove(object);
        this.gameObjects = new ArrayList<>(getGameObjects());
    }

    @Override
    public void remove(int index) {
        gameObjects.remove(index);
    }

    public int getCount() {
        return gameObjects.size();
    }

    public void setScore() {
        this.score++;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setItemOn() {
        itemOn = true;
    }

    public boolean getItemOn() {
        return itemOn;
    }

    public void createItem() {
        if (!itemOn) {
            setItemOn();
            GameObject item = (GameObject) factory.createItem(200, 200, 100, 50);
            gameObjects.add(item);
        }
    }

    public GameObject getGameObject(int index) {
        return gameObjects.get(index);
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (GameObject object : getGameObjects()) {
            if (object instanceof Paintable) {
                ((Paintable) object).paint(g);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (GameObject object : gameObjects) {
            if (object instanceof Movable && !(object instanceof Bounceable) && !(object instanceof Eatable)) {
                ((MovableObject) object).setX(e.getX());
                repaint();
            }
        }
    }
}

