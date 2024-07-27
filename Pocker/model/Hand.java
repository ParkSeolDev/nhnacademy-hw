package model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cardsInHand = new ArrayList<>();

    public Hand() {}

    private Hand(List<Card> cards) {
        this.cardsInHand = cards;
    }

    public static Hand from(List<Card> cards) {
        return new Hand(cards);
    }

    public Hand receivedHand(List<Card> cards) {
        this.cardsInHand.addAll(cards);
        return new Hand(cardsInHand);
    }

    public Hand receivedHandOneCard(Card card) {
        this.cardsInHand.add(card);
        return new Hand(cardsInHand);
    }

    public Card findMaxCard() {
        Card maxCard = cardsInHand.get(0);

        for (int i = 1; i < cardsInHand.size(); i++) {
            Card currentCard = cardsInHand.get(i);
            if (currentCard.compare(maxCard) > 0) {
                maxCard = currentCard;
            }
        }

        return maxCard;
    }

    public List<Card> cards() {
        return cardsInHand;
    }
}
