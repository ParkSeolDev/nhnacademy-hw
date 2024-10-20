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

    @Override
    public String toString() {
        return cardsInHand.stream()
                .map(Card::toString)
                .collect(Collectors.joining(", "));
    }
}
