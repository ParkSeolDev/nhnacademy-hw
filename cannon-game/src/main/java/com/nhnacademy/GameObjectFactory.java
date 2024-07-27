package com.nhnacademy;

public class GameObjectFactory extends Factory {
    @Override
    Upgradable createBrick(int x, int y, int width, int height) {
        return new Brick(x, y, width, height);
    }

    @Override
    Upgradable createBall(int x, int y, int radius) {
        return new BounceableObject(new MovableObject(new Ball(x, y, radius, radius)));
    }

    @Override
    Upgradable createWall(int x, int y, int width, int height) {
        return new Wall(x, y, width, height);
    }
    @Override
    Upgradable createCannon(int x, int y, int width, int height) {
        return new MovableObject(new Cannon(x, y, width, height));
    }
    
    @Override
    Upgradable createHell(int x, int y, int width, int height) {
        return new Hell(x, y, width, height);
    }
}
