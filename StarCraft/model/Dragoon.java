package model;

public class Dragoon extends ProtosUnit implements Flightless, Laser, Upgradable {
    private static final String UNIT_NAME = "Dragoon";

    protected Dragoon(String unitName, Integer attackPower, Integer defense) {
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
    
    public static Dragoon create() {
        return new Dragoon(UNIT_NAME, 3, 15);
    }

    @Override
    public Integer range() {
        return range();
    }

    @Override
    public <T> Dragoon attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Flightless) {
            targetAttackPower = ((Flightless) targetUnit).attackPower(this);
        }
        return new Dragoon(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
