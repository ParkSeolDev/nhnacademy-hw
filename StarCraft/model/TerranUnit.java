package model;

public class TerranUnit extends Unit {

    protected TerranUnit(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }
    
    @Override
    public TerranUnit extinguish() {
        return null;
    }
}
