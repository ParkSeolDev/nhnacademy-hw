package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ball extends PaintableObect {
    private static Map<Integer, Color> COLOR_MAP = new HashMap<>() {
        {
            put(0, Color.RED);
            put(1, Color.BLUE);
            put(2, Color.GREEN);
        }
    };
    private static final Random RANDOM = new Random();

    private GameObject gameObject;
    
    public Ball(int x, int y, int radius, int radius2) {
        super(x, y, radius, radius);
        validate(x, y, radius);

        this.region = new Rectangle(x - radius, y - radius, 2 * radius, 2 * radius);
        gameObject = new PaintableObect(x - radius, y - radius, 2 * radius, 2 * radius);
        setColor(COLOR_MAP.get(RANDOM.nextInt(0, 3)));
        logger.trace("Ball created : {}, {}", x, y);
    }

    @Override
    public void paint(Graphics g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }
        Color originalColor = g.getColor();
        g.fillOval((int) this.region.getX(), (int) this.region.getY(), gameObject.getRadius() * 2,
                gameObject.getRadius() * 2);
        g.setColor(this.getColor());
        g.setColor(originalColor);
        g.setColor(originalColor);
    }
}
