package com.nhnacademy;

import java.awt.Color;
import java.awt.Rectangle;

public class Wall extends PaintableObect {
    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        validate(x, y, width, height);

        this.region = new Rectangle(x, y, width, height);
        setColor(Color.ORANGE);
        logger.trace("Wall created : {}, {}", x, y);
    }
}
