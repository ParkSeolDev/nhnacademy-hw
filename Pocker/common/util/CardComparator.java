package common.util;

import java.util.Comparator;

import model.Card;
import model.CardUnit;

public class CardComparator implements Comparator<Card> {

    @Override
    public int compare(Card c1, Card c2) {
        if (compareTo(c1.card().get(0), c2.card().get(0)) == 1) {
            return 1;
        }

        if (compareTo(c1.card().get(0), c2.card().get(0)) == 0) {
            if (compareTo(c1.card().get(1), c2.card().get(1)) == 1) {
                return 1;
            } else
                return -1;
        }

        return -1;
    }

    private int compareTo(CardUnit x1, CardUnit x2) {
        if (x1.unitNumber() > x2.unitNumber())
            return 1;
        else if (x1.unitNumber() == x2.unitNumber())
            return 0;
        else
            return -1;
    }
    
}
