package model;

public class Wraith extends TerranUnit implements Flyable {
    private static final String UNIT_NAME = "Wraith";

    protected Wraith(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public Integer attackPower(Flyable flyable) {
        return attackPower();
    }
    
    public static Wraith create() {
        return new Wraith(UNIT_NAME, 3, 10);
    }

    @Override
    public Integer attackPower(Flightless flightless) {
        return attackPower();
    }

    @Override
    public <T> Wraith attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        }
        return new Wraith(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
