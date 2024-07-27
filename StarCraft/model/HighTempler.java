package model;

public class HighTempler extends ProtosUnit implements Flightless {
    private static final String UNIT_NAME = "HighTempler";

    protected HighTempler(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public Integer attackPower(Flightless flightless) {
        return attackPower();
    }
    
    public static HighTempler create() {
        return new HighTempler(UNIT_NAME, 10, 20);
    }

    @Override
    public <T> HighTempler attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Flightless) {
            targetAttackPower = ((Flightless) targetUnit).attackPower(this);
        }
        return new HighTempler(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
