package com.nhnacademy;

import java.awt.Color;
import java.awt.Rectangle;

public class Wall extends PaintableObject {

    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        validate(x, y, width, height);

        this.region = new Rectangle(x, y, width, height);
        setColor(Color.ORANGE);
        logger.trace("Wall created : {}, {}", x, y);
    }

    @Override
    public void hit(GameObject other) {
        if (hitListener != null) {
            hitListener.hit(other);
        }
    }
}
