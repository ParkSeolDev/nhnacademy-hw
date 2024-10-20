package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardShape extends CardUnit {
    private static final Integer MIN_CARD_NUMBER = 1;
	private static final Integer MAX_CARD_NUMBER = 4;

    private static final List<CardShape> CARD_SHAPE_CACHE = new ArrayList<>();
    
    static {
        for (int i = MIN_CARD_NUMBER; i <= MAX_CARD_NUMBER; i++) {
            CARD_SHAPE_CACHE.add(new CardShape(i));
        }
    }
    
    protected CardShape(final Integer unitNumber) {
        super(unitNumber);
    }

    public static CardShape randomCardShape() {
        Collections.shuffle(CARD_SHAPE_CACHE);

        return CARD_SHAPE_CACHE.get(0);
    }
    
    public static CardShape from(final Integer unitNumber) {
        return CARD_SHAPE_CACHE.get(unitNumber);
    }
}
