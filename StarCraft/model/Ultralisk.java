package model;

public class Ultralisk extends ZergUnit implements Flightless {
    private static final String UNIT_NAME = "Ultralisk";

    protected Ultralisk(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public Integer attackPower(Flightless flightless) {
        return attackPower();
    }
    
    public static Ultralisk create() {
        return new Ultralisk(UNIT_NAME, 5, 15);
    }

    @Override
    public <T> Ultralisk attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Flightless) {
            targetAttackPower = ((Flightless) targetUnit).attackPower(this);
        }
        return new Ultralisk(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
