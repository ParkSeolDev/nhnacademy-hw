package model;

public class Guardian extends ZergUnit implements Flyable {
    private static final String UNIT_NAME = "Guardian";

    protected Guardian(String unitName, Integer attackPower, Integer defense) {
        super(unitName, attackPower, defense);
    }

    @Override
    public Integer attackPower(Flyable flyable) {
        return attackPower();
    }
    
    public static Guardian creaet() {
        return new Guardian(UNIT_NAME, 3, 6);
    }

    @Override
    public Integer attackPower(Flightless flightless) {
        return attackPower();
    }

    @Override
    public <T> Guardian attackedUnit(T targetUnit) {
        int targetAttackPower = 0;
        if (targetUnit instanceof Flyable) {
            targetAttackPower = ((Flyable) targetUnit).attackPower(this);
        } else if (targetUnit instanceof Equipped) {
            targetAttackPower = ((Equipped) targetUnit).attackPower(this);
        }
        return new Guardian(UNIT_NAME, this.attackPower(), this.defense() - targetAttackPower);
    }
}
