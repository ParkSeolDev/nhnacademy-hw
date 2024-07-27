package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardShape extends CardUnit {
    private static final Integer MIN_CARD_NUMBER = 0;
	private static final Integer MAX_CARD_NUMBER = 3;

    private static final List<CardShape> CARD_SHAPE_CACHE = new ArrayList<>();
    
    static {
        for (int i = MIN_CARD_NUMBER; i <= MAX_CARD_NUMBER; i++) {
            CARD_SHAPE_CACHE.add(new CardShape(i));
        }
    }
    
    private CardShape(final Integer unitNumber) {
        super(unitNumber);
    }

    public static CardShape randomCardShape() {
        Collections.shuffle(CARD_SHAPE_CACHE);

        return CARD_SHAPE_CACHE.get(0);
    }
    
    public static CardShape from(final Integer unitNumber) {
        return CARD_SHAPE_CACHE.get(unitNumber);
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
