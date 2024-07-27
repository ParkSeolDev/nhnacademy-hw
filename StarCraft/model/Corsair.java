package model;

public class Corsair extends ProtosUnit implements Flyable {
    private static final String UNIT_NAME = "Corsair";

    protected Corsair(String unitName, Integer attackPower, Integer defense) {
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
    
    public static Corsair create() {
        return new Corsair(UNIT_NAME, 4, 12);
    }

    @Override
    public <T> Corsair attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        }
        return new Corsair(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
