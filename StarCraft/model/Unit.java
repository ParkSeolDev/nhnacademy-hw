package model;

public abstract class Unit extends DefenseObserver {
    private String unitName;
    private Integer attackPower;
    private Integer defense;

    public <T extends Unit> Unit(T attackedUnit) {
        this(attackedUnit.unitName(), attackedUnit.attackPower(), attackedUnit.defense());
    }

    protected <T extends Unit> Unit(String unitName, Integer attackPower, Integer defense) {
        this.unitName = unitName;
        this.attackPower = attackPower;
        this.defense = defense;
        if (isDefenseZero(defense)) {
            this.defense = 0;
        }
    }

    private boolean isDefenseZero(Integer defense) {
        if (defense <= 0) {
            return true;
        }
        return false;
    }

    public <T> Unit attackedUnit(T targetUnit)
    {
        return null;
    }

    public Integer attackPower() {
        return this.attackPower;
    }

    public Integer defense() {
        return this.defense;
    }

    public String unitName() {
        return this.unitName;
    }

    @Override
    public String toString() {
        return unitName + " (현재 방어력: " + defense + ")";
    }
}
