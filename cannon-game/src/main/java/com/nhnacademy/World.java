package com.nhnacademy;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class World extends JPanel {
    private List<GameObject> gameObjects;
    GameObjectFactory factory = new GameObjectFactory();
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    
    public World() {
        gameObjects = new ArrayList<>();
    }

    public void add(GameObject object) {
        if (object == null) {
            throw new IllegalArgumentException();
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
                ((PaintableObject) object).paint(g);
            }
        }
    }

    public boolean hasBall() {
        List<GameObject> list = new ArrayList<>();
        for (GameObject gameObject : getGameObjects()) {
            if (gameObject instanceof Bounceable) {
                list.add(gameObject);
            }
        }
        
        return !(list.size() <= 4);
    }
}

