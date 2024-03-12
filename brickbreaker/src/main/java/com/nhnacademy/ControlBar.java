package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;

public class ControlBar extends PaintableObect {
    public ControlBar(int x, int y, int width, int height) {
        super(x, y, width, height);
        validate(x, y, width, height);

        setColor(Color.MAGENTA);
        logger.trace("ControlBar created : {}, {}", x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g.setColor(this.getColor());
    } 
}
