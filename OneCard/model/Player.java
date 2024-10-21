package model;

public class Player {
    private Integer playerNum;
    private Hand hand;

    private Player(Integer playerNum, Hand hand) {
        this.playerNum = playerNum;
        this.hand = hand;
    }

    public static Player of(Integer playerNum, Hand hand) {
        return new Player(playerNum, hand);
    }

    public Hand hand() {
        return this.hand;
    }

    public Integer playerNum() {
        return playerNum;
    }

    @Override
    public String toString() {
        return playerNum + " : " + hand.toString();
    }
}
