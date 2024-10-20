package model;

public class AttackCardSymbol extends CardSymbol implements Attackable {

    private int attackPower;

    public AttackCardSymbol(int attackPower, Integer unitNumber) {
        super(unitNumber);
        this.attackPower = attackPower;
    }

    @Override
    public void attack(Player player) {
        player.hand().receivedHand(Cards.getPenalties(attackPower));
    }
}
