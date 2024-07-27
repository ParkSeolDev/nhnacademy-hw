package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cards {
    private static final Integer CARDS_COUNT = 52; 
    private static final List<Card> cards = createCards();

    private static int cardsIndex = 0;

    private Cards() {
    }

    public static List<Card> getInstance() {
        return cards;
    }

    private static List<Card> createCards() {
        List<Card> cards = new ArrayList<>(CARDS_COUNT);
    
        for (int i = 0; i < CARDS_COUNT; i++) {
            cards.add(Card.createCard());
        }

        Collections.shuffle(cards);
        return Collections.unmodifiableList(cards);
    }
    
    public static Card getNextCard() {
        return cards.get(cardsIndex++);
    }
}
