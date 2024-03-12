package com.nhnacademy;

import java.awt.Color;

public class Item extends PaintableObect {

    public Item(int x, int y, int width, int height) {
        super(x, y, width, height);
        validate(x, y, width, height);
        setColor(Color.YELLOW);
        logger.trace("Item created : {}, {}", x, y);
    }
}
