package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Hell extends PaintableObject implements End {
    public Hell(int x, int y, int width, int height) {
        super(x, y, width, height);
        validate(x, y, width, height);

        this.region = new Rectangle(x, y, width, height);
        setColor(Color.RED);
        logger.trace("Hell created : {}, {}", x, y);
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