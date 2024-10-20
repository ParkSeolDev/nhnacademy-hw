package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardSymbol extends CardUnit {
    private static final Integer MIN_CARD_NUMBER = 3;
    private static final Integer MAX_CARD_NUMBER = 10;
    private static final Integer CARD_NUMBER_TWO = 2;
    private static final Integer CARD_NUMBER_TWO_POWER = 2;
    private static final Integer CARD_A = 14;
    private static final Integer CARD_A_POWER = 3;
    private static final Integer CARD_JACK = 11;
    private static final Integer CARD_QUEEN = 12;
    private static final Integer CARD_KING = 13;

    private static final List<CardSymbol> CARD_SYMBOL_CACHE = new ArrayList<>();
    
    static {
        for (int i = MIN_CARD_NUMBER; i <= MAX_CARD_NUMBER; i++) {
            CARD_SYMBOL_CACHE.add(new CardSymbol(i));
        }

        CARD_SYMBOL_CACHE.add(new AttackCardSymbol(CARD_NUMBER_TWO_POWER, CARD_NUMBER_TWO));
        CARD_SYMBOL_CACHE.add(new AttackCardSymbol(CARD_A_POWER, CARD_A));

        CARD_SYMBOL_CACHE.add(new FaceCardSymbol(CARD_JACK));
        CARD_SYMBOL_CACHE.add(new FaceCardSymbol(CARD_QUEEN));
        CARD_SYMBOL_CACHE.add(new FaceCardSymbol(CARD_KING));
    }
    
    protected CardSymbol(final Integer unitNumber) {
        super(unitNumber);
    }

    public static CardSymbol randomCardSymbol() {
        Collections.shuffle(CARD_SYMBOL_CACHE);

        return CARD_SYMBOL_CACHE.get(0);
    }
    
    public static CardSymbol from(final Integer unitNumber) {
        return CARD_SYMBOL_CACHE.get(unitNumber);
    }

    public boolean checkJack() {
        return unitNumber() == CARD_JACK;
    }

    public boolean checkKing() {
        return unitNumber() == CARD_KING;
    }

    public boolean checkQueen() {
        return unitNumber() == CARD_QUEEN;
    }

    @Override
    public String toString() {
        return unitNumber().toString();
    }
}
