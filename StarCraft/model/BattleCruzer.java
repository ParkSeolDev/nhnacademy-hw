package model;

public class BattleCruzer extends TerranUnit implements Flyable {

    private static final String UNIT_NAME = "BattleCruzer";

    protected BattleCruzer(String unitName, Integer attackPower, Integer defense) {
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
    
    public static BattleCruzer create() {
        return new BattleCruzer(UNIT_NAME, 20, 30);
    }

    @Override
    public <T> BattleCruzer attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        }
        return new BattleCruzer(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
