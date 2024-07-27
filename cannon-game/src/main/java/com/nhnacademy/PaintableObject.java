package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;

public class PaintableObject extends GameObject implements Paintable {
    private Color color;

    public PaintableObject(int x, int y, int width, int height) {
        super(x, y, width, height);
        color = Color.BLACK;
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
    public void paint(Graphics g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g.setColor(this.getColor());
    }
}
