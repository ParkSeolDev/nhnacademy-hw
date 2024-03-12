package com.nhnacademy;

public interface Bounceable extends Upgradable {
    public void bounce(Breakable other);
    public void bounce(GameObject other);
}
