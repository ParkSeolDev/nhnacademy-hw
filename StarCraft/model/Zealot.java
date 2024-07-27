package model;

public class Zealot extends ProtosUnit implements Flightless {
    private static final String UNIT_NAME = "Zealot";

    protected Zealot(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public Integer attackPower(Flightless flightless) {
        return attackPower();
    }
    
    public static Zealot create() {
        return new Zealot(UNIT_NAME, 5, 20);
    }

    @Override
    public <T> Zealot attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Flightless) {
            targetAttackPower = ((Flightless) targetUnit).attackPower(this);
        }
        return new Zealot(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
