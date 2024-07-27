package model;

public class Carrier extends ProtosUnit implements Flyable {
    private static final String UNIT_NAME = "Carrier";

    protected Carrier(String unitName, Integer attackPower, Integer defense) {
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
    
    public static Carrier create() {
        return new Carrier(UNIT_NAME, 25, 40);
    }

    @Override
    public <T> Carrier attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        }
        return new Carrier(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
