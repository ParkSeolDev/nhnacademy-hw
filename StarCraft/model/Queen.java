package model;

public class Queen extends ZergUnit implements Flyable {
    private static final String UNIT_NAME = "Queen";

    protected Queen(String unitName, Integer attackPower, Integer defense) {
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
    
    public static Queen create() {
        return new Queen(UNIT_NAME, 15, 25);
    }

    @Override
    public <T> Queen attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        }
        return new Queen(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
