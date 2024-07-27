package model;

public class Tank extends TerranUnit implements Flightless, Upgradable {
    private static final String UNIT_NAME = "Tank";

    protected Tank(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public Integer attackPower(Flightless flightless) {
        return attackPower();
    }
    
    public static Tank create() {
        return new Tank(UNIT_NAME, 7, 15);
    }

    @Override
    public Integer range() {
        return attackPower();
    }

    @Override
    public <T> Tank attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Flightless) {
            targetAttackPower = ((Flightless) targetUnit).attackPower(this);
        }
        return new Tank(UNIT_NAME, attackPower(), this.defense() - targetAttackPower);
    }
}
