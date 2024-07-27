package model;

public class Goliath extends TerranUnit implements Flightless, Missile, Upgradable {
    private static final String UNIT_NAME = "Goliath";

    protected Goliath(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public Integer attackPower(Flyable flyable) {
        return attackPower();
    }

    @Override
    public Integer attackPower(Flightless flightless) {
        return attackPower();
    }
    
    public static Goliath create() {
        return new Goliath(UNIT_NAME, 5, 15);
    }

    @Override
    public Integer range() {
        return range();
    }

    @Override
    public <T> Goliath attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Flightless) {
            targetAttackPower = ((Flightless) targetUnit).attackPower(this);
        }
        return new Goliath(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
