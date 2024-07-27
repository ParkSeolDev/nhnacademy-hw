package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;

public interface Paintable extends Upgradable {
    public Color getColor();

    public void setColor(Color color);

    public void paint(Graphics g);
}
