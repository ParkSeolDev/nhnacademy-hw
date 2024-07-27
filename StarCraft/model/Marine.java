package model;

public class Marine extends TerranUnit implements Flightless, Upgradable {
    private static final String UNIT_NAME = "Marine";

    protected Marine(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public Integer attackPower(Flightless flightless) {
        return attackPower();
    }

    public static Marine create() {
        return new Marine(UNIT_NAME, 3, 10);
    }

    @Override
    public Integer range() {
        return range();
    }

    @Override
    public <T> Marine attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Flightless) {
            targetAttackPower = ((Flightless) targetUnit).attackPower(this);
        }
        return new Marine(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
