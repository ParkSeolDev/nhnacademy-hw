package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CardSymbol extends CardUnit {
    private static final Integer MIN_CARD_NUMBER = 2;
	private static final Integer MAX_CARD_NUMBER = 14;

    private static final List<CardSymbol> CARD_SYMBOL_CACHE = new ArrayList<>();
    
    static {
        for (int i = MIN_CARD_NUMBER; i <= MAX_CARD_NUMBER; i++) {
            CARD_SYMBOL_CACHE.add(new CardSymbol(i));
        }
    }
    
    private CardSymbol(final Integer unitNumber) {
        super(unitNumber);
    }

    public static CardSymbol randomCardSymbol() {
        Collections.shuffle(CARD_SYMBOL_CACHE);

        return CARD_SYMBOL_CACHE.get(0);
    }
    
    public static CardSymbol from(final Integer unitNumber) {
        return CARD_SYMBOL_CACHE.get(unitNumber);
    }

    @Override
    public int compare(CardUnit c1, CardUnit c2) {
        if (c1.unitNumber() > c2.unitNumber())
            return 1;
        else if (c1.unitNumber() == c2.unitNumber())
            return 0;
        else
            return -1;
    }
}
