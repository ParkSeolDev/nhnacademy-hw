package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cards {
    private static final Integer CARDS_COUNT = 52;
    private static final Integer JOKER_COUNT = 2;
    private static final Integer COLOR_JOKER_POWER = 7;
    private static final Integer MONO_JOKER_POWER = 5;
    private static final Integer COLOR_JOKER_CARD_SYMBOL = 16;
    private static final Integer MONO_JOKER_CARD_SYMBOL = 15;

    private static final List<Card> cards = createCards();

    private static int cardsIndex = 0;

    private Cards() {
    }

    public static List<Card> getInstance() {
        return cards;
    }

    private static List<Card> createCards() {
        List<Card> cards = new ArrayList<>(CARDS_COUNT + JOKER_COUNT);
    
        for (int i = 0; i < CARDS_COUNT; i++) {
            cards.add(Card.createCard());
        }

        cards.add(new Card(List.of((CardUnit) new Joker(COLOR_JOKER_POWER, COLOR_JOKER_CARD_SYMBOL).attackable(), new CardShape(0))));
        cards.add(new Card(List.of((CardUnit) new Joker(MONO_JOKER_POWER, MONO_JOKER_CARD_SYMBOL).attackable(), new CardShape(0))));

        Collections.shuffle(cards);
        return Collections.unmodifiableList(cards);
    }
    
    public static Card getNextCard() {
        if (cardsIndex + 1 < cards.size()) {
            return cards.get(++cardsIndex);
        } else {
            System.out.println("No more cards left in the deck!");
            return null;
        }
    }

    public static List<Card> getPenalties(int penaltiesCnt) {
        List<Card> penaltyCards = new ArrayList<>();
        
        for (int i = 0; i < penaltiesCnt; i++) {
            if (cardsIndex < cards.size()) {
                penaltyCards.add(cards.get(cardsIndex++));
            } else {
                System.out.println("No more cards left in the deck!");
                break;
            }
        }
        
        return penaltyCards;
    }
}
