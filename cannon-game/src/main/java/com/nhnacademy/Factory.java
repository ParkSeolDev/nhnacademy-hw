package com.nhnacademy;

public abstract class Factory {
    abstract Upgradable createBrick(int x, int y, int width, int height);
    abstract Upgradable createBall(int x, int y, int radius);
    abstract Upgradable createWall(int x, int y, int width, int height);
    abstract Upgradable createCannon(int x, int y, int width, int height);
    abstract Upgradable createHell(int x, int y, int width, int height);
}
