package model;

public class Joker {
    private Attackable attackable;

    public Joker(int attackPower, Integer unitNumber) {
        attackable = new AttackCardSymbol(attackPower, unitNumber);
    }

    protected Attackable attackable() {
        return attackable;
    }
}
