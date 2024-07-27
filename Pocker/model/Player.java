package model;

public class Player {
    private Integer playerNum;
    private Money money;
    private Hand hand;

    private Player(Integer playerNum, Money money, Hand hand) {
        this.playerNum = playerNum;
        this.money = money;
        this.hand = hand;
    }

    public static Player of(Integer playerNum, Money money, Hand hand) {
        return new Player(playerNum, money, hand);
    }

    public Hand hand() {
        return this.hand;
    }

    public Integer playerNum() {
        return playerNum;
    }

    public Money money() {
        return money;
    }
}
