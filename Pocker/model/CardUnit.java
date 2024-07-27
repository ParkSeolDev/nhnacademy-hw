package model;

import java.util.Comparator;

public abstract class CardUnit implements Comparator<CardUnit>{
    private Integer unitNumber;

    protected CardUnit(final Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public int compareTo(CardUnit other) {
        if (this.unitNumber > other.unitNumber)
            return 1;
        else if (this.unitNumber == other.unitNumber)
            return 0;
        else
            return -1;
    }

    public Integer unitNumber() {
        return unitNumber;
    }
}
