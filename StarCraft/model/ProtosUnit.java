package model;

public class ProtosUnit extends Unit {

    protected ProtosUnit(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public ProtosUnit extinguish() {
        return null;
    }
}
