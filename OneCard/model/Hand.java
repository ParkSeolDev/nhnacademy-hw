package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Card> cards() {
        return cardsInHand;
    }

    public void removeCard(Card card) {
        cards().remove(card);
    }

    public Card playCard(CardUnit topCardSymbol, CardUnit topCardShape) {
        for (Card card : cards()) {
            if (card.canPlayOn(topCardSymbol, topCardShape)) {
                removeCard(card);
                return card;
            }
        }
        return null;
    }

    public boolean hasWon() {
        return cards().isEmpty();
    }

    @Override
    public String toString() {
        return cardsInHand.stream()
                .map(Card::toString)
                .collect(Collectors.joining(", "));
    }
}
