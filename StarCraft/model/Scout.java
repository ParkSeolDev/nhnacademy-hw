package model;

public class Scout extends ProtosUnit implements Flyable {
    private static final String UNIT_NAME = "Scout";

    protected Scout(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public Integer attackPower(Flyable flyable) {
        return attackPower();
    }
    
    public static Scout create() {
        return new Scout(UNIT_NAME, 5, 10);
    }

    @Override
    public Integer attackPower(Flightless flightless) {
        return attackPower();
    }

    @Override
    public <T> Scout attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        }
        return new Scout(UNIT_NAME, attackPower(), this.defense() - targetAttackPower);
    }
}
