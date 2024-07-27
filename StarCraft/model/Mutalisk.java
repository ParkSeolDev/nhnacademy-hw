package model;

public class Mutalisk extends ZergUnit implements Flyable {
    private static final String UNIT_NAME = "Mutalisk";

    protected Mutalisk(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public Integer attackPower(Flyable flyable) {
        return attackPower();
    }
    
    public static Mutalisk create() {
        return new Mutalisk(UNIT_NAME, 2, 8);
    }

    @Override
    public Integer attackPower(Flightless flightless) {
        return attackPower();
    }

    @Override
    public <T> Mutalisk attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        }
        return new Mutalisk(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
