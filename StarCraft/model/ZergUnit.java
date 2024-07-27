package model;

public class ZergUnit extends Unit {

    protected ZergUnit(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public ZergUnit extinguish() {
        return null;
    }
}
