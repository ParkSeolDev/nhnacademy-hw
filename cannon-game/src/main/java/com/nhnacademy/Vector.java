package com.nhnacademy;


public class Vector {
    private int dx;
    private int dy;

    public Vector() {
        dx = 0;
        dy = 0;
    }

    public Vector(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Vector(Vector other) {
        dx = other.getDX();
        dy = other.getDY();
    }

    public Vector createPosition(int dx, int dy) {
        return new Vector(dx, dy);
    }

    public int getDX() {
        return this.dx;
    }

    public int getDY() {
        return this.dy;
    }

    public void setDX(int dx) {
        this.dx = dx;
    }

    public void setDY(int dy) {
        this.dy = dy;
    }

    public void set(int dx, int dy) {
        setDX(dx);
        setDY(dy);
    }

    public void setVector(Vector other) {
        setDX(other.getDX());
        setDY(other.getDY());
    }

    public void turnDX() {
        setVector(new Vector(-getDX(), getDY()));
    }

    public void turnDY() {
        setVector(new Vector(getDX(),-getDY()));
    }

    public void add(Vector other) {
        this.dx = this.dx + other.getDX();
        this.dy = this.dy + other.getDY();
    }

    public void sub(Vector other) {
        this.dx = this.dx - other.getDX();
        this.dy = this.dy - other.getDY();
    }

    public void multiply(double scale) {
        set((int) (getDX() * scale), (int) (getDY() * scale));
    }
}
