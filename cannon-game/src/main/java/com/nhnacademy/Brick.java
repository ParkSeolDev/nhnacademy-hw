package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Brick extends PaintableObject implements Breakable {

    public Brick(int x, int y, int width, int height) {
        super(x, y, width, height);
        validate(x, y, width, height);

        this.region = new Rectangle(x, y, width, height);
        setColor(Color.GREEN);
        logger.trace("Brick created : {}, {}", x, y);
    }

    @Override
    public void paint(Graphics g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }
        g.fillRect((int) this.region.getX(), (int) this.region.getY(), (int) this.region.getWidth(),
                (int) this.region.getHeight());
        g.setColor(this.getColor());
    }
}
