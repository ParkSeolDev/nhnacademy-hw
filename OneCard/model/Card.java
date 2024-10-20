package model;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private static final int CARD_DIVISION = 2;

    private List<CardUnit> card = new ArrayList<>(CARD_DIVISION);

    protected Card(List<CardUnit> cardUnits) {
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
    
    public boolean canPlayOn(CardUnit topCardSymbol, CardUnit topCardShape) {
        return this.card.get(0).equals(topCardSymbol)
                || this.card.get(1).equals(topCardShape)
                || this.card.get(1).unitNumber() == 0
                || topCardShape.unitNumber() == 0;
    }

    public CardUnit cardSymbol() {
        return card.get(0);
    }

    public CardUnit cardShape() {
        return card.get(1);
    }
    
    @Override
    public String toString() {
        return "[Symbol: " + card.get(0).toString() + " Shape: " + card.get(1).toString() + "]";
    }
}
