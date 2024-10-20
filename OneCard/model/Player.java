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

    public void removeCard(Card card) {
        hand.cards().remove(card);
    }

    public Card playCard(CardUnit topCardSymbol, CardUnit topCardShape) {
        for (Card card : hand.cards()) {
            if (card.canPlayOn(topCardSymbol, topCardShape)) {
                removeCard(card);
                return card;
            }
        }
        return null;
    }

    public boolean hasWon() {
        return hand.cards().isEmpty();
    }

    @Override
    public String toString() {
        return playerNum + " : " + hand.toString();
    }
}
