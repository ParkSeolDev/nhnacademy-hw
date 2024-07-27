package model;

public class Valkyrie extends TerranUnit implements Flyable {
    private static final String UNIT_NAME = "Valkyrie";

    protected Valkyrie(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public Integer attackPower(Flyable flyable) {
        return attackPower();
    }
    
    public static Valkyrie create() {
        return new Valkyrie(UNIT_NAME, 4, 12);
    }

    @Override
    public Integer attackPower(Flightless flightless) {
        return attackPower();
    }

    @Override
    public <T> Valkyrie attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        }
        return new Valkyrie(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
