package model;

public abstract class CardUnit {
    private Integer unitNumber;

    protected CardUnit(final Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Integer unitNumber() {
        return unitNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        CardUnit cardUnit = (CardUnit) other;
        return unitNumber != null && unitNumber.equals(cardUnit.unitNumber);
    }

    @Override
    public int hashCode() {
        return unitNumber != null ? unitNumber.hashCode() : 0;
    }
}
