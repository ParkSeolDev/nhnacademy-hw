package model;

public class Hydralisk extends ZergUnit implements Flightless, Saliva, Upgradable {
    private static final String UNIT_NAME = "Hydralisk";

    protected Hydralisk(String unitName, Integer attackPower, Integer defense) {
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

    public static Hydralisk create() {
        return new Hydralisk(UNIT_NAME, 3, 7);
    }

    @Override
    public Integer range() {
        return range();
    }

    @Override
    public <T> Hydralisk attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Flightless) {
            targetAttackPower = ((Flightless) targetUnit).attackPower(this);
        }
        return new Hydralisk(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}