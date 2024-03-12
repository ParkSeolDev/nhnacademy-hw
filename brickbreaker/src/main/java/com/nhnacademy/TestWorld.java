package com.nhnacademy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class TestWorld {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 800;
    private static final int WALL_WIDTH = 700;
    private static final int WALL_HEIGHT = 100;
    private static final int MIN_RADIUS = 10;
    private static final int MIN_DELTA = 10;

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MovableWorld world = new MovableWorld();

        GameObjectFactory gameObjectFactory = new GameObjectFactory();

        GameObject topWall = (GameObject)gameObjectFactory.createWall(0, 0, WALL_WIDTH, WALL_HEIGHT-50);
        GameObject bottomHell = (GameObject)gameObjectFactory.createHell(0, FRAME_HEIGHT - WALL_HEIGHT, WALL_WIDTH, WALL_HEIGHT);
        GameObject leftWall = (GameObject)gameObjectFactory.createWall(0, WALL_HEIGHT-50, WALL_HEIGHT, WALL_WIDTH - WALL_HEIGHT+50);
        GameObject rightWall = (GameObject) gameObjectFactory.createWall(FRAME_WIDTH - WALL_HEIGHT, 0, WALL_HEIGHT,
                WALL_WIDTH + 100);
        
        world.add(topWall);
        world.add(bottomHell);
        world.add(leftWall);
        world.add(rightWall);

        frame.add(world);

        List<GameObject> gameObjects = new ArrayList<>();
        
        try {
            for (int i = 100; i < 650; i+= 100) {
                for (int j = 100; j < 600; j+= 100) {
                    GameObject brick = (GameObject) gameObjectFactory.createBrick(i, j, 80, 50);
                    gameObjects.add(brick);
                }
            }
                
        } catch (IllegalArgumentException ignore) {
        }
        
       try {
            GameObject ball = (GameObject) gameObjectFactory.createBall(RANDOM.nextInt(100, 700), RANDOM.nextInt(600, 620),
                    MIN_RADIUS);
            
            int dx = MIN_DELTA;
            int dy = MIN_DELTA;
            ((MovableObject) ball).setDX(dx);
            ((MovableObject) ball).setDY(-dy);
            gameObjects.add(ball);
            
        } catch (IllegalArgumentException ignore) {
        }
        
        world.addAll(gameObjects);

        frame.setEnabled(true);
        frame.setVisible(true);
        world.setMaxMoveCount(3000);
        world.run();
    }
}
