package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Card implements Comparator<Card> {
    private static final int CARD_DIVISION = 2;

    private List<CardUnit> card = new ArrayList<>(CARD_DIVISION);

    private Card(List<CardUnit> cardUnits) {
        this.card = cardUnits;
    }

    public static Card createCard() {
        List<CardUnit> newCards = new ArrayList<>();
        newCards.add(CardSymbol.randomCardSymbol());
        newCards.add(CardShape.randomCardShape());

        return new Card(newCards);
    }
    
    public static Card of(CardSymbol cardSymbol, CardShape cardShape) {
        List<CardUnit> newCards = new ArrayList<>();
        newCards.add(cardSymbol);
        newCards.add(cardShape);

        return new Card(newCards);
    }

    public List<CardUnit> card() {
        return card;
    }

    public int compare(Card maxCard) {
        if (card.get(0).compareTo(maxCard.card().get(0)) == 1) {
            return 1;
        }

        if (card.get(0).compareTo(maxCard.card().get(0)) == 0) {
            if (card.get(1).compareTo(maxCard.card().get(1)) == 1) {
                return 1;
            } else
                return -1;
        }

        return -1;
    }
    
    public int compare(Card c1, Card c2) {
        return c1.compare(c2);
    }
}
