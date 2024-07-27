package model;

public class Zergling extends ZergUnit implements Flightless {
    private static final String UNIT_NAME = "Zergling";

    protected Zergling(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public Integer attackPower(Flightless flightless) {
        return attackPower();
    }
    
    public static Zergling create() {
        return new Zergling(UNIT_NAME, 2, 2);
    }

    @Override
    public <T> Zergling attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Flightless) {
            targetAttackPower = ((Flightless) targetUnit).attackPower(this);
        }
        return new Zergling(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
