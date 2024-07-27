package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;

public class Cannon extends PaintableObject {
    public Cannon(int x, int y, int width, int height) {
        super(x, y, width, height);
        validate(x, y, width, height);

        setColor(Color.MAGENTA);
        logger.trace("Cannon created : {}, {}", x, y);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g.setColor(this.getColor());
    } 
}
